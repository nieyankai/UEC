package com.example.uec_app.ui.config.region;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.Region;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class RegionConfigViewModel extends ViewModel {

    private DssRepository dssRepository;

    private MutableLiveData<List<Region>> regionList;

    @Inject
    public RegionConfigViewModel(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        regionList = new MutableLiveData<>();
        regionList.setValue(new ArrayList<>());
    }

    public LiveData<List<Region>> getRegionList() {
        return (LiveData<List<Region>>)regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList.setValue(regionList);
    }

    public boolean removeRegion(int id){
        if(dssRepository.removeRegion(regionList.getValue().get(id))){
            regionList.getValue().remove(id);
            setRegionList(regionList.getValue());
            return true;
        }
        return false;
    }

    public void refresh(){
        setRegionList(dssRepository.getRegionList());
    }
}
