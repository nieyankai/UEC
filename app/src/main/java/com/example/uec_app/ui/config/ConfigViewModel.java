package com.example.uec_app.ui.config;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class ConfigViewModel extends ViewModel {

    private MutableLiveData<List<Map<String,Object>>> configItems;
    private DssRepository dssRepository;

    @Inject
    public ConfigViewModel(DssRepository dssRepository){
        this.dssRepository = dssRepository;
        configItems = new MutableLiveData<>();
        configItems.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Map<String, Object>>> getConfigItems() {
        return configItems;
    }

    public void setConfigItems(List<Map<String, Object>> configItems) {
        this.configItems.setValue(configItems);
    }

    public boolean refreshData(){
        List<Map<String,Object>> configItems = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("name","区域配置");
        map.put("button","立即配置");
        configItems.add(map);

        map = new HashMap<>();
        map.put("name","资源配置");
        map.put("button","立即配置");
        configItems.add(map);

        map = new HashMap<>();
        map.put("name","数据源配置");
        map.put("button","立即配置");
        configItems.add(map);

        map = new HashMap<>();
        map.put("name","用户信息");
        map.put("button","立即配置");
        configItems.add(map);

        map = new HashMap<>();
        map.put("name","日志");
        map.put("button","立即配置");
        configItems.add(map);

        setConfigItems(configItems);
        return true;

    }
}
