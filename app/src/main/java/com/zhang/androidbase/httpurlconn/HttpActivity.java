package com.zhang.androidbase.httpurlconn;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhang.androidbase.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://成功
                    String s = (String) msg.obj;
                    Toast.makeText(HttpActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                    break;
                case 2://失败
                    Toast.makeText(HttpActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3://异常
                    Toast.makeText(HttpActivity.this, "异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void reqHttpWeb(View view) {

        new  Thread(new Runnable() {
            @Override
            public void run() {
//                String path = "https://www.baidu.com/";
                String path = "https://api.douban.com/v2/movie/in_theaters";
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    //状态吗200表示请求资源成功
                    if (responseCode == 200) {
                        //获取服务器返回的数据
                        InputStream inputStream = httpURLConnection.getInputStream();
                        String response = InputStreamToStringTool.inputStreamToString(inputStream);
                        System.out.println("=========res start");
                        System.out.println("----" + response);
                        System.out.println("=========res end");

                        //注意Toast也是一个View 更新UI在主线程操作
                        Message message = mHandler.obtainMessage();
                        message.obj = response;
                        message.what = 1;
                        mHandler.sendMessage(message);


                    } else {
                        Message message = mHandler.obtainMessage();
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Message message = mHandler.obtainMessage();
                    message.what = 3;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = mHandler.obtainMessage();
                    message.what = 3;
                    mHandler.sendMessage(message);
                }

            }
        }).start();

    }
}
