package com.example.uec_app.coap;

import android.util.Log;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.config.NetworkConfig;

import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;

/**
 * Created by yyq on 18-1-9.
 */

public class COAPClient {

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    //从服务器端返回的数据
    String result = "";


    public boolean run(String url, String data, String action) {

        CoapResponse response = null;
        boolean flag;
        NetworkConfig.getStandard().setLong("ACK_TIMEOUT",2000);
        CoapClient client = new CoapClient(url);

        //put 请求
        if (action.equals("put")) {
            response = client.put(data,TEXT_PLAIN);
 //           response = client.put(data,1);
        }else if (action.equals("get")) {
            response = client.get();
        }else if(action.equals("post")){
            response = client.post(data, MediaTypeRegistry.APPLICATION_JSON);
        }else if(action.equals("delete")){
            response = client.delete();
        }

        if (response != null) {
            flag = true;
            result = new String(response.getPayload(), 0, response.getPayload().length);
        } else {
            flag = false;
            Log.e("88888", "No response received.");
        }
        return flag;
    }

}