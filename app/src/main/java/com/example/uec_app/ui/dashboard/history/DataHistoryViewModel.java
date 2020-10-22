package com.example.uec_app.ui.dashboard.history;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class DataHistoryViewModel extends ViewModel {
    private DssRepository dssRepository;
    private MutableLiveData<List<DssData>> dssDataList;

    @Inject
    public DataHistoryViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        dssDataList = new MutableLiveData<>();
        dssDataList.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<DssData>> getDssDataList() {
        return dssDataList;
    }

    public void setDssDataList(List<DssData> dssDataList) {
        this.dssDataList.setValue(dssDataList);
    }

    public void refresh(DssConfig dssConfig){
        setDssDataList(dssRepository.getDataList(dssConfig, 12L));
    }

}
