package com.tcl.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.tcl.testservicedemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private EditText editText;
    private Button btnStart, btnStop, btnBind, btnUnBind, btnSync;
    private Intent serviceIntent;
    private IMyAidlInterface ibindler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.edittext);
        btnStart = (Button) findViewById(R.id.start);
        btnStop = (Button) findViewById(R.id.stop);
        btnBind = (Button) findViewById(R.id.bind);
        btnUnBind = (Button) findViewById(R.id.unbind);
        btnSync = (Button) findViewById(R.id.sync);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);
        btnSync.setOnClickListener(this);

        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.tcl.testservicedemo", "com.tcl.testservicedemo.MyService"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startService(serviceIntent);
                break;
            case R.id.stop:
                stopService(serviceIntent);
                break;
            case R.id.bind:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(this);
                ibindler = null;
                break;
            case R.id.sync:
                if (ibindler!=null){
                    try {
                        ibindler.setData(editText.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Bind Service");
        System.out.println(service);
        ibindler = IMyAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
