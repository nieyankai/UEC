package com.example.uec_app.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssData;
import com.example.uec_app.model.ResType;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class HomeViewModel extends ViewModel {

    private MutableLiveData<Map<String,List<DssData>>> dataListMap;

    private DssRepository dssRepository;

    @Inject
    public HomeViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        dataListMap = new MutableLiveData<>();
        dataListMap.setValue(new HashMap<>());
    }

    public MutableLiveData<Map<String, List<DssData>>> getDataListMap() {
        return dataListMap;
    }

    public void setDataListMap(Map<String, List<DssData>> dataListMap) {
        this.dataListMap.setValue(dataListMap);
    }

    public void refresh(){
        Map<String, List<DssData>> map = new HashMap<>();
        List<ResType> resTypeList = dssRepository.getResTypeList();
        for (ResType resType: resTypeList) {
            List<DssData> dataList = dssRepository.getTypeActivetData(resType);
            if (dataList.isEmpty())
                continue;
            map.put(resType.getName(),dataList);
        }
        dataListMap.setValue(map);
    }

    public void clearDB() {
        dssRepository.clearDB();
    }
}