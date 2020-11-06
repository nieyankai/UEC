package com.example.uec_app.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.uec_app.coap.COAPClient;
import com.example.uec_app.db.TDao;
import com.example.uec_app.model.Alarm;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.model.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class DssRepository {


    private TDao<DssData> dssDataTDao;
    private TDao<DssConfig> dssConfigTDao;
    private TDao<UserInfo> userInfoTDao;
    private TDao<Alarm> alarmTDao;
    private TDao<ResType> resTypeTDao;
    private TDao<Region> regionTDao;
    private Map<Long,Object> dataCache;


    @Inject
    public DssRepository(@ApplicationContext Context ctx) {

        this.dssDataTDao = new TDao<DssData>(ctx,DssData.class);
        this.dssConfigTDao = new TDao<DssConfig>(ctx,DssConfig.class);
        this.userInfoTDao = new TDao<UserInfo>(ctx,UserInfo.class);
        this.alarmTDao = new TDao<>(ctx,Alarm.class);
        this.resTypeTDao = new TDao<>(ctx,ResType.class);
        this.regionTDao = new TDao<>(ctx,Region.class);
        this.dataCache = new HashMap<>();
    }

    private String sendCoapRequest(String url, String payload, String action) {
        try {
            COAPClient coapClient = new COAPClient();
            boolean flag = coapClient.run(url,payload==null?"":payload,action);
            if(flag){
                return coapClient.getResult();
            }else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String getCoapUrl(DssConfig dssConfig){
        if (dssConfig == null)
            return null;
        String ip = dssConfig.getIp();
        String group = dssConfig.getGroup();
        String dss = dssConfig.getDss();
        String url = "coap://" + ip + ":"+ 5683 + "/dss/" + group + "/" + dss;
        return url;
    }

    private String getUnit(DssConfig dssConfig){
        if (dssConfig == null)
            return null;
        String unit = dssConfig.getResType().getUnit();
        return unit;
    }

    private void checkData(DssData dssData, DssConfig dssConfig){
        if (dssConfig.getUpper() == null ||dssConfig.getLower() == null || dssConfig.getUpper().isEmpty() || dssConfig.getLower().isEmpty())
            return;
        float upper = Float.parseFloat(dssConfig.getUpper());
        float lower = Float.parseFloat(dssConfig.getLower());
        float value = Float.parseFloat(dssData.getValue());
        if (value<=upper && value>=lower)
            return;
        if (value>upper){
            Alarm alarm = new Alarm(dssData.getValue(),"超上限预警",dssConfig.getRegion().getName(),dssConfig.getName());
            alarmTDao.insert(alarm);
            return;
        }
        if (value<lower){
            Alarm alarm = new Alarm(dssData.getValue(),"超下限预警",dssConfig.getRegion().getName(),dssConfig.getName());
            alarmTDao.insert(alarm);
        }
    }

    public void clearDB(){
        dssDataTDao.deleteAll();
        userInfoTDao.deleteAll();
        alarmTDao.deleteAll();
    }


    //Dss Data
    public boolean removeDssData(DssData dssData){return dssDataTDao.delete(dssData)>0;}
    public DssData getLatestData(DssConfig dssConfig){
        if (dataCache.containsKey(dssConfig.getId()))
            return (DssData) dataCache.get(dssConfig.getId());

        DssData dataDB = dssDataTDao.queryLatest("dss_config",dssConfig);
        if (dataDB != null){
            return dataDB;
        }
        String url = getCoapUrl(dssConfig);
        if (url == null)
            return null;
        String value = sendCoapRequest(url,null,"get");
        if (value == null || value.isEmpty())
            return null;
        DssData dataNet = new DssData(dssConfig,value,System.currentTimeMillis());
        dataCache.put(dssConfig.getId(),dataNet);
        dssDataTDao.insert(dataNet);
        return dataNet;
    }

    public String setDssData(DssData data){
        String url = getCoapUrl(data.getDssConfig());
        String value = data.getValue();
        return sendCoapRequest(url,value,"change");
    }

    public List<DssData> getDataList(DssConfig dssConfig, Long N){
        List<DssData> list = dssDataTDao.queryForN("dss_config",dssConfig,N);
        if (list==null || list.isEmpty())
            return new ArrayList<>();
        list.sort(new Comparator<DssData>() {
            @Override
            public int compare(DssData o1, DssData o2) {
                return (int) (o1.getTime()-o2.getTime());
            }
        });
        return list;
    }

    public List<DssData> getTypeLatestData(String type){
        List<DssConfig> configs = dssConfigTDao.queryByColumn("type",type);
        if (configs==null || configs.isEmpty())
            return new ArrayList<>();
        List<DssData> res = new ArrayList<>();
        for (DssConfig config: configs) {
            DssData dssData = dssDataTDao.queryLatest("dss_config",config);
            if (dssData == null)
                continue;
            res.add(dssData);
        }
        return res;
    }

    public List<DssData> getTypeActivetData(ResType type){
        List<DssConfig> configs = dssConfigTDao.queryByColumn("res_type",type);
        if (configs==null || configs.isEmpty())
            return new ArrayList<>();
        List<DssData> res = new ArrayList<>();
        for (DssConfig config: configs) {
            DssData dssData = dssDataTDao.queryActive("dss_config",config);
            if (dssData == null)
                continue;
            res.add(dssData);
        }
        return res;
    }


    public List<DssData> getAllLatestData(){
        List<DssConfig> configs = dssConfigTDao.queryAll();
        if (configs==null || configs.isEmpty())
            return new ArrayList<>();
        List<DssData> res = new ArrayList<>();
        for (DssConfig config: configs) {
            DssData dssData = dssDataTDao.queryLatest("dss_config",config);
            if (dssData == null)
                continue;
            res.add(dssData);
        }
        return res;
    }

    public List<DssData> getAllActiveData(){
        List<DssConfig> configs = dssConfigTDao.queryAll();
        if (configs==null || configs.isEmpty())
            return new ArrayList<>();
        List<DssData> res = new ArrayList<>();
        for (DssConfig config: configs) {
            DssData dssData = dssDataTDao.queryActive("dss_config",config);
            if (dssData == null)
                continue;
            res.add(dssData);
        }
        return res;
    }

    public boolean fetchDataFromNet(DssConfig dssConfig){
        String url = getCoapUrl(dssConfig);
        String value = sendCoapRequest(url,null,"get");
        if (value == null || value.isEmpty())
            return false;
        DssData data = new DssData(dssConfig, value, System.currentTimeMillis());
        if(dssDataTDao.insert(data)<=0)
            return false;
        dataCache.put(dssConfig.getId(),data);
        checkData(data,dssConfig);
        return true;
    }

    //DssConfig
    public DssConfig getDssConfig(long config_id){
        return dssConfigTDao.queryById(config_id);
    }

    public long getTypeConfigCount(ResType type){
        return dssConfigTDao.countByCollumn("res_type",type);
    }

    public boolean removeDssConfig(DssConfig dssConfig){
        return dssConfigTDao.delete(dssConfig)>0;
    }

    public boolean addDssConfig(DssConfig dssConfig){
        return dssConfigTDao.insert(dssConfig)>0;
    }
    public boolean updateDssConfig(DssConfig dssConfig){
        return dssConfigTDao.update(dssConfig)>0;
    }

    public boolean setDssConfig(DssConfig config){
        return dssConfigTDao.insertOrUpdate(config)>0;
    }

    public List<DssConfig> getDssConfigList(){
        List<DssConfig> configs = dssConfigTDao.queryAll();
        if (configs==null)
            return new ArrayList<>();
        return configs;
    }

    //UserInfo
    public UserInfo getUserInfo(long user_id){
        return userInfoTDao.queryById(user_id);
    }

    public boolean setUserInfo(UserInfo userInfo){
        return userInfoTDao.insertOrUpdate(userInfo)>0;
    }

    public boolean checkUser(UserInfo userInfo) {
        Map<String ,Object> map = new HashMap<>();
        map.put("name",userInfo.getName());
        map.put("password",userInfo.getPassword());
        return userInfoTDao.queryForFieldValues(map).size()>0;
    }

    //Alarm
    public List<Alarm> getAlarmList(long N){
        return alarmTDao.queryForN(N);
    }

    public boolean addAlarm(Alarm alarm){
        return alarmTDao.insert(alarm)>0;
    }

    public boolean removeAlarm(Alarm alarm){
        return alarmTDao.delete(alarm)>0;
    }

    public boolean resolveAlarm(Alarm alarm){
        alarm.setResolved("已解除");
        return alarmTDao.update(alarm)>0;
    }

    public long countAlarmAll(){
        return alarmTDao.count();
    }
    public long countAlarmWait(){
        return alarmTDao.countByCollumn("resolved","解除");
    }
    public long countAlarmResolved(){
        return alarmTDao.countByCollumn("resolved","已解除");
    }
    //ResType
    public List<ResType> getResTypeList(){
        return resTypeTDao.queryAll();
    }
    public boolean addResType(ResType resType){
        return resTypeTDao.insert(resType)>0;
    }
    public boolean updateResType(ResType resType){
        return resTypeTDao.update(resType)>0;
    }
    public boolean removeResType(ResType resType){return resTypeTDao.delete(resType)>0;}


    //Region
    public List<Region> getRegionList(){
        return regionTDao.queryAll();
    }
    public boolean addRegion(Region region){
        return regionTDao.insert(region)>0;
    }
    public boolean updateRegion(Region region){
        return regionTDao.update(region)>0;
    }
    public boolean removeRegion(Region region){
        return regionTDao.delete(region)>0;
    }


    //control
    public boolean control(DssConfig dssConfig, String type, String value, int time) {
        String url = getCoapUrl(dssConfig);
        String data = value == "开" ? "1":"0";
        if (type == "立即控制"){
            String res = sendCoapRequest(url,data,"put");
            Log.d("result", "control() returned: " + res);
        }
        return data.equals(getLatestData(dssConfig).getValue());
    }

    public int getRetryTimes() {
        return 3;
    }
}
