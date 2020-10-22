package com.example.uec_app.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@DatabaseTable(tableName = "tb_dss_config")//创建表名
public class DssConfig {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;

    @DatabaseField(columnName = "region",foreign = true,canBeNull = true,foreignAutoRefresh = true)
    private Region region;

    @DatabaseField(columnName = "ip",dataType = DataType.STRING)
    private String ip;

    @DatabaseField(columnName = "group",dataType = DataType.STRING)
    private String group;

    @DatabaseField(columnName = "dss",dataType = DataType.STRING)
    private String dss;

    @DatabaseField(columnName = "res_type",foreign = true,canBeNull = true,foreignAutoRefresh = true)
    private ResType resType;

    @DatabaseField(columnName = "upper",dataType = DataType.STRING,canBeNull = true)
    private String upper;
    @DatabaseField(columnName = "lower",dataType = DataType.STRING,canBeNull = true)
    private String lower;


    @DatabaseField(columnName = "time",dataType = DataType.LONG)
    private long time;

    public DssConfig() {
    }

    public DssConfig(long id, String name, Region region, String ip, String group, String dss, ResType resType, long time) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.ip = ip;
        this.group = group;
        this.dss = dss;
        this.resType = resType;
        this.time = time;
    }

    public DssConfig(String name, Region region, String ip, String group, String dss, ResType resType, long time) {
        this.name = name;
        this.region = region;
        this.ip = ip;
        this.group = group;
        this.dss = dss;
        this.resType = resType;
        this.time = time;
    }

    public DssConfig(String name, Region region, String ip, String group, String dss, ResType resType) {
        this.name = name;
        this.region = region;
        this.ip = ip;
        this.group = group;
        this.dss = dss;
        this.resType = resType;
        this.time = System.currentTimeMillis();
    }

    public DssConfig(String name, Region region, String ip, String group, String dss, ResType resType, String upper, String lower) {
        this.name = name;
        this.region = region;
        this.ip = ip;
        this.group = group;
        this.dss = dss;
        this.resType = resType;
        this.upper = upper;
        this.lower = lower;
        this.time = System.currentTimeMillis();
    }

    public DssConfig(String name, Region region, String ip, String group, String dss, ResType resType, long time, String upper, String lower) {
        this.name = name;
        this.region = region;
        this.ip = ip;
        this.group = group;
        this.dss = dss;
        this.resType = resType;
        this.upper = upper;
        this.lower = lower;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDss() {
        return dss;
    }

    public void setDss(String dss) {
        this.dss = dss;
    }

    public ResType getResType() {
        return resType;
    }

    public void setResType(ResType resType) {
        this.resType = resType;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DssConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region=" + region +
                ", ip='" + ip + '\'' +
                ", group='" + group + '\'' +
                ", dss='" + dss + '\'' +
                ", resType=" + resType +
                ", upper='" + upper + '\'' +
                ", lower='" + lower + '\'' +
                ", time=" + time +
                '}';
    }


    public String getTimeString(){
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date(time));
    }
}
