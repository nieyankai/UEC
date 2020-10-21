package com.example.uec_app.ui.notifications;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class NotificationsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<DssData>> mList;
    private DssRepository dssRepository;

    @Inject
    public NotificationsViewModel(Application application) {
        super(application);
        dssRepository = new DssRepository(application.getApplicationContext());
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
        mList = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<DssData>> getList(){
/*      List<DssData> dssDataList = dssRepository.getAllLatestData("电压",Long.valueOf(10));
        if (dssDataList == null)
            return mList;
        mList.setValue(dssDataList);*/
        return mList;
    }
}