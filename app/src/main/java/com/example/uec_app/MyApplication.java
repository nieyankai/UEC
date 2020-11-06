package com.example.uec_app;

import android.app.Application;
import android.content.Intent;

import com.example.uec_app.db.TDao;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.service.DataService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, DataService.class));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
