package com.example.uec_app.ui.config.dss;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.Region;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class DssConfigViewModel extends ViewModel {

    private DssRepository dssRepository;

    private MutableLiveData<List<DssConfig>> dssList;

    @Inject
    public DssConfigViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        dssList = new MutableLiveData<>();
        dssList.setValue(new ArrayList<>());
    }

    public LiveData<List<DssConfig>> getDssConfigList() {
        return (LiveData<List<DssConfig>>)dssList;
    }

    public void setDssConfigList(List<DssConfig> dssList) {
        this.dssList.setValue(dssList);
    }

    public boolean removeDssConfig(int id){
        if(dssRepository.removeDssConfig(dssList.getValue().get(id))){
            dssList.getValue().remove(id);
            setDssConfigList(dssList.getValue());
            return true;
        }
        return false;
    }

    public void refresh(){
        setDssConfigList(dssRepository.getDssConfigList());
    }
}
