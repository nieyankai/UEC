package com.example.uec_app.work;

import com.example.uec_app.model.DssConfig;
import com.example.uec_app.repository.DssRepository;

import java.util.List;

public class dataThread extends Thread {


    DssRepository dssRepository;
    boolean isRunning;

    public dataThread(DssRepository dssRepository) {
        this.dssRepository = dssRepository;
        isRunning = true;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }
    @Override
    public void run() {
        long lastSendTime = 0;
        while (isRunning){
            long now = System.currentTimeMillis();
            try{
                if((now - lastSendTime) > 5000)
                {
                    List<DssConfig> configs = dssRepository.getDssConfigList();
                    if (configs == null || configs.size()==0){
                        lastSendTime = now;
                        continue;
                    }
                    for (DssConfig config : configs) {
                        dssRepository.fetchDataFromNet(config);
                    }
                    lastSendTime = now;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
