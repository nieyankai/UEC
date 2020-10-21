package com.example.uec_app.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.ResType;
import com.example.uec_app.repository.DssRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class SharedViewModel extends ViewModel {

    private DssRepository dssRepository;
    private MutableLiveData<String> currentDataName;
    private MutableLiveData<Map<String,Object>> resTypeCount;

    @Inject
    public SharedViewModel(DssRepository dssRepository){
        this.dssRepository = dssRepository;
        currentDataName = new MutableLiveData<>();
        resTypeCount = new MutableLiveData<>();
        resTypeCount.setValue(new HashMap<>());
    }

    public MutableLiveData<String> getCurrentDataName() {
        return currentDataName;
    }

    public void setCurrentDataName(String name){
        currentDataName.setValue(name);
    }

    public MutableLiveData<Map<String, Object>> getResTypeCount() {
        return resTypeCount;
    }
    public void setResTypeCount(Map<String, Object> resTypeCount) {
        this.resTypeCount.setValue(resTypeCount);
    }

    public void refreshResTypeCount(){
        List<ResType> types = dssRepository.getResTypeList();
        Map<String,Object> map = new HashMap<>();
        for (ResType type:types) {
            long n = dssRepository.getTypeConfigCount(type);
            if (n==0)
                continue;
            map.put(type.getName(),n);
        }
        resTypeCount.setValue(map);
    }

}
