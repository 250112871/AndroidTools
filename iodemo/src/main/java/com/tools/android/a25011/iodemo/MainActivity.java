package com.tools.android.a25011.iodemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tools.android.a25011.iodemo.nio.NioClient;
import com.tools.android.a25011.iodemo.nio.NioServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NioServer nioServer = new NioServer(8001);
                    nioServer.listion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NioClient nioClient = new NioClient(8001);
                    nioClient.listion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
