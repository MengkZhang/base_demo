package com.zhang.androidbase.httpurlconn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("AppCompatCustomView")
public class MySmartImageView extends ImageView {

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 3://请求失败 显示一个默认图片
                    int resource =  (Integer) msg.obj;
                    MySmartImageView.this.setBackgroundResource(resource);
                    break;


                case 4://获取显示bitmap
                    Bitmap bitmap = (Bitmap) msg.obj;
                    MySmartImageView.this.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    private Activity mActivity;

    // 一个参数的构造方法 在new 代码初始化的时候调用
    public MySmartImageView(Context context) {
        super(context);
        mActivity = (Activity) context;
    }

    // 这个类 在布局文件中使用的时候 调用2个参数构造方法
    public MySmartImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
    }

    public MySmartImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = (Activity) context;
    }

    public void setImageUrl(final String path, final int source) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "https://upload-images.jianshu.io/upload_images/5914881-8e0f627c5963bd0d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp";
                File file = new File(mActivity.getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));

                //使用缓存数据
                if (file.exists() && file.length() >0) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Message message = mHandler.obtainMessage();
                    message.obj = bitmap;
                    message.what = 4;
                    mHandler.sendMessage(message);
                    return;
                }
                //请求网络数据
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            fos.write(buffer,0,len);
                        }
                        fos.close();
                        inputStream.close();

                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                        Message message = mHandler.obtainMessage();
                        message.obj = bitmap;
                        message.what = 4;
                        mHandler.sendMessage(message);

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mImageView.setImageBitmap(bitmap);
//                            }
//                        });

                    } else {
                        sendErrorPic(source);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    sendErrorPic(source);
                } catch (IOException e) {
                    e.printStackTrace();
                    sendErrorPic(source);
                }
            }
        }).start();
    }

    private void sendErrorPic(int source) {
        Message message = mHandler.obtainMessage();
        message.obj = source;
        message.what = 3;
        mHandler.sendMessage(message);
    }

}
