package com.example.uec_app.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<DssData>> voltageDataList;
    private DssRepository dssRepository;

    @Inject
    public HomeViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        voltageDataList = new MutableLiveData<>();
        voltageDataList.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<DssData>> getVoltageDataList() {
        return voltageDataList;
    }

    public void setVoltageDataList(List<DssData> voltageDataList) {
        this.voltageDataList.setValue(voltageDataList);
    }

    public void refreshVoltageData(){
        voltageDataList.setValue(dssRepository.getTypeActivetData("电压"));
    }

    public void clearDB() {
        dssRepository.clearDB();
    }
}