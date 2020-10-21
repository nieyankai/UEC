package com.example.uec_app;

import android.app.Application;

import com.example.uec_app.db.TDao;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {

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
                dssRepository.fetchDataFromNet(config);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        timer.scheduleAtFixedRate(dataTask,1000,5000);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        timer.cancel();
    }
}
