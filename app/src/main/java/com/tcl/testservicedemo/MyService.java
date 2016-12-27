package com.tcl.testservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {

    private  String data = "默认数据";
    private boolean running = false;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new IMyAidlManager();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service Started");
        new Thread(){
            @Override
            public void run() {
                super.run();
                running = true;
                while (running){
                    System.out.println(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service Destroy");
        running = false;
    }

    private class IMyAidlManager extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setData(String data) throws RemoteException {
            MyService.this.data = data;
        }
    }
}
