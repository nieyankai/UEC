package com.example.uec_app.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<DssData>> latestDataList;
    private DssRepository dssRepository;

    @Inject
    public DashboardViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        latestDataList = new MutableLiveData<>();
        latestDataList.setValue(new ArrayList<>());
    }

    public LiveData<List<DssData>> getLatestDataList() {
        return (LiveData<List<DssData>>)latestDataList;
    }

    public void setLatestDataList(List<DssData> dssDataList) {
        this.latestDataList.setValue(dssDataList);
    }

    public boolean removeDssData(int id){
        if(dssRepository.removeDssData(latestDataList.getValue().get(id))){
            latestDataList.getValue().remove(id);
            setLatestDataList(latestDataList.getValue());
            return true;
        }
        return false;
    }

    public void refresh(){
        setLatestDataList(dssRepository.getAllLatestData());
    }



}