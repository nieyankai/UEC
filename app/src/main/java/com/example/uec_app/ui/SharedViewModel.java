package com.example.uec_app.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.model.UserInfo;
import com.example.uec_app.repository.DssRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;


@ActivityRetainedScoped
public class SharedViewModel extends ViewModel {

    private DssRepository dssRepository;
    private MutableLiveData<DssConfig> currentDss;
    private MutableLiveData<Boolean> login = new MutableLiveData<>();
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<Map<String,Object>> resTypeCount;

    @Inject
    public SharedViewModel(DssRepository dssRepository){
        this.dssRepository = dssRepository;
        this.login.setValue(false);
        this.userInfo.setValue(new UserInfo("admin","admin"));
        currentDss = new MutableLiveData<>();
        resTypeCount = new MutableLiveData<>();
        resTypeCount.setValue(new HashMap<>());
    }


    public MutableLiveData<DssConfig> getCurrentDss() {
        return currentDss;
    }

    public void setCurrentDss(DssConfig currentDss) {
        this.currentDss.setValue(currentDss);
    }

    public MutableLiveData<Map<String, Object>> getResTypeCount() {
        return resTypeCount;
    }
    public void setResTypeCount(Map<String, Object> resTypeCount) {
        this.resTypeCount.setValue(resTypeCount);
    }

    public void refreshResTypeCount(){
        List<ResType> types = dssRepository.getResTypeList();
        Map<String,Object> map = new HashMap<>();
        for (ResType type:types) {
            long n = dssRepository.getTypeConfigCount(type);
            if (n==0)
                continue;
            map.put(type.getName(),n);
        }
        resTypeCount.setValue(map);
    }

    public boolean checkUser(UserInfo userInfo) {
        if (userInfo.getName()==this.userInfo.getValue().getName() && this.userInfo.getValue().getPassword() == userInfo.getPassword())
            return true;
        return false;
    }

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public LiveData<Boolean> getLogin() {
        return (LiveData<Boolean>)login;
    }

    public void login(UserInfo userInfo) {
        this.userInfo.setValue(userInfo);
        this.login.setValue(true);
    }
}
