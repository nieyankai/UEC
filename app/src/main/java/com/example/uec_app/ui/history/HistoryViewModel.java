package com.example.uec_app.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class HistoryViewModel extends ViewModel {

    private MutableLiveData<List<DssData>> dssDataList;
    @Inject
    DssRepository dssRepository;


    @Inject
    public HistoryViewModel() {
        dssDataList = new MutableLiveData<>();
        dssDataList.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<DssData>> getDssDataList() {
        return dssDataList;
    }

    public boolean refreshData(DssConfig dssConfig, Long N){
        List<DssData> dataList = dssRepository.getDataList(dssConfig,N);
        if (dataList==null||dataList.size()==0)
            return false;
        dataList.sort(new Comparator<DssData>() {
            @Override
            public int compare(DssData o1, DssData o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });
        dssDataList.setValue(dataList);
        return true;
    }

}
