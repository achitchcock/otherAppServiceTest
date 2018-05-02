package uwb.aaron.com.servicestarterlib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uwb.aaron.com.servicestarterlib.dataReceiver;

/**
 * Created by Aaron on 4/22/2018.
 */

public class serviceConnection implements dataReceiver.Receiver{

    private Context parent;
    public dataReceiver mReceiver;

    private Map<String, String> data;

    private byte[] tex;
    private int buff_size;

    public serviceConnection(Context con){
        parent = con;
        data = new HashMap<String, String>();
    }

    public void startDroneService(){
        mReceiver = new dataReceiver(new Handler());
        mReceiver.setReceiver(this);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "uwb.aaron.com.servicetest",
                "uwb.aaron.com.servicetest.backgroundService"));
        intent.putExtra("dataR", mReceiver);
        intent.putExtra("nameTag","serviceConnectionReceiver");
        intent.setAction("START_SERVICE");
        parent.startService(intent);
    }


    public void stopDroneService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "uwb.aaron.com.servicetest",
                "uwb.aaron.com.servicetest.backgroundService"));

        parent.stopService(intent);
        data.clear();
    }

    public void sendCommand(String command){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "uwb.aaron.com.servicetest",
                "uwb.aaron.com.servicetest.backgroundService"));
        intent.setAction(command);
        parent.startService(intent);
    }
    @Override
    public void onReceiveResult(int  resultCode, Bundle resultData) {
        // TODO Auto-generated method stub
        String tag = resultData.getString("ServiceTag");
        Log.d("UNITY DRONE","received result from Service="+tag);
        switch (resultCode){
            case 0:{
                Set<String> keys = resultData.keySet();
                for (String key : keys
                        ) {
                    data.put(key, resultData.getString(key));
                }
            }
            case 1:{
                tex = resultData.getByteArray("VIDEO_BUFF");
                buff_size = resultData.getInt("BUFF_SIZE");
            }
        }
    }

    public String get(String key){
        if(data.containsKey(key)){
            return data.get(key);
        }
        else{
            return "Unknown";
        }

    }

    public byte[] getTex() {
        return tex;
    }
    public int getBuffSize(){return buff_size;}
}
