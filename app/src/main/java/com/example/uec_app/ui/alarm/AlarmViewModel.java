package com.example.uec_app.ui.alarm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.Alarm;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class AlarmViewModel extends ViewModel {

    private MutableLiveData<List<Alarm>> alarmList;
    private MutableLiveData<Long> alarmAll;
    private MutableLiveData<Long> alarmWait;
    private MutableLiveData<Long> alarmResolved;
    private DssRepository dssRepository;

    @Inject
    public AlarmViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        alarmList = new MutableLiveData<>();
        alarmList.setValue(new ArrayList<>());
        alarmAll = new MutableLiveData<>();
        alarmAll.setValue(0L);
        alarmWait = new MutableLiveData<>();
        alarmWait.setValue(0L);
        alarmResolved = new MutableLiveData<>();
        alarmResolved.setValue(0L);
    }


    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList.setValue(alarmList);
    }


    public void setAlarmResolved(Long alarmResolved) {
        this.alarmResolved.setValue(alarmResolved);
    }

    public void setAlarmAll(Long alarmAll) {
        this.alarmAll.setValue(alarmAll);
    }

    public void setAlarmWait(Long alarmWait) {
        this.alarmWait.setValue(alarmWait);
    }

    public MutableLiveData<List<Alarm>> getAlarmList() {
        return alarmList;
    }

    public MutableLiveData<Long> getAlarmAll() {
        return alarmAll;
    }

    public MutableLiveData<Long> getAlarmWait() {
        return alarmWait;
    }

    public MutableLiveData<Long> getAlarmResolved() {
        return alarmResolved;
    }

    public boolean resolveAlarm(int id){
        if(dssRepository.resolveAlarm(alarmList.getValue().get(id))){
            alarmList.getValue().get(id).setResolved("已解除");
            setAlarmList(alarmList.getValue());
            return true;
        }
        return false;
    }

    public void refresh(){
        setAlarmList(dssRepository.getAlarmList(10L));
        setAlarmAll(dssRepository.countAlarmAll());
        setAlarmWait(dssRepository.countAlarmWait());
        setAlarmResolved(dssRepository.countAlarmResolved());
    }


}