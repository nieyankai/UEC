package com.example.uec_app.ui.config.resource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class ResourceConfigViewModel extends ViewModel {

    private DssRepository dssRepository;

    private MutableLiveData<List<ResType>> resourceList;

    @Inject
    public ResourceConfigViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        resourceList = new MutableLiveData<>();
        resourceList.setValue(new ArrayList<>());
    }

    public LiveData<List<ResType>> getResTypeList() {
        return (LiveData<List<ResType>>)resourceList;
    }

    public void setResTypeList(List<ResType> resourceList) {
        this.resourceList.setValue(resourceList);
    }

    public boolean removeResType(int id){
        if(dssRepository.removeResType(resourceList.getValue().get(id))){
            resourceList.getValue().remove(id);
            setResTypeList(resourceList.getValue());
            return true;
        }
        return false;
    }

    public void refresh(){
        setResTypeList(dssRepository.getResTypeList());
    }
}
