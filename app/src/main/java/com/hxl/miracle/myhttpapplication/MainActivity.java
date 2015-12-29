package com.hxl.miracle.myhttpapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_okhttp, btn_asyncHttp;

    private EditText tv_okhttp, tv_asyncHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btn_okhttp = (Button) findViewById(R.id.btn_okhttp);
        btn_asyncHttp = (Button) findViewById(R.id.btn_asynchttp);
        tv_okhttp = (EditText) findViewById(R.id.tv_okhttp);
        tv_asyncHttp = (EditText) findViewById(R.id.tv_asynchttp);

        btn_okhttp.setOnClickListener(this);
        btn_asyncHttp.setOnClickListener(this);
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
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_okhttp:
                TextRequest textRequest = new TextRequest(MainActivity.this);
                final long time = System.currentTimeMillis();
                Log.d("onRequestSuccess", "time" + time);
                textRequest.startTextRequest("2", new BLOkRequestCallback() {
                    @Override
                    public void onRequestSuccess(Request request, Response response) {
                        try {
                            ResponseBody responseBody = response.body();
                            final String str = responseBody.string();
                            long timeSuccess = System.currentTimeMillis() - time;
                            final String time = (double) timeSuccess / 1000 + "秒";
                            Log.d("onRequestSuccess", " timeSuccess" + timeSuccess + " request" + str);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_okhttp.setText(str);
                                    Toast.makeText(MainActivity.this, "消耗时间：" + time + "内容长度：" + str.length(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (IOException e) {

                        }

                    }

                    @Override
                    public void onRequestFailure(Request request, Exception e) {
                        Log.d("onRequestFailure", "request" + request.body().toString());
                    }
                });
                break;
            case R.id.btn_asynchttp:

                TextAsyncRequest textAsyncRequest = new TextAsyncRequest(MainActivity.this);
                final long asyncTime = System.currentTimeMillis();
                Log.d("onRequestSuccess", "asyncTime" + asyncTime);
                textAsyncRequest.startTextRequest("2", new BLRequestCallback() {
                    @Override
                    public void onSuccess(Basebean bean, String data) {
                        final String asyncData = bean.getData();
                        long timeSuccess = System.currentTimeMillis() - asyncTime;
                        final String time = (double) timeSuccess / 1000 + "秒";
                        Log.d("onRequestSuccess", " time" + time + " data" + data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_asyncHttp.setText(asyncData);
                                Toast.makeText(MainActivity.this, "消耗时间：" + time + "内容长度：" + asyncData.length(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onFailure(String errorCode, String errorMsg, Basebean bean) {
                        Log.d("onRequestSuccess", "onFailure" + errorMsg);
                    }
                });
                break;

        }
    }
}
