package com.example.uec_app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.repository.DssRepository;

import java.security.Provider;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class DataService extends Service {

    @Inject
    DssRepository dssRepository;
    Timer timer = new Timer();

    TimerTask dataTask = new TimerTask() {
        @Override
        public void run() {
            List<DssConfig> configs = dssRepository.getDssConfigList();
            if (configs == null || configs.size()==0)
                return;
            for (DssConfig config : configs) {

                for (int i = dssRepository.getRetryTimes();i>0;i--){
                    if(dssRepository.fetchDataFromNet(config))
                        break;
                }
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        timer.scheduleAtFixedRate(dataTask,1000,5000);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
