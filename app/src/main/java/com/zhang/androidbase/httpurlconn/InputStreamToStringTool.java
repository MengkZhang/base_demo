package com.zhang.androidbase.httpurlconn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamToStringTool {
    /**
     * 把流转换成String字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int length = -1;
        byte[] buffer = new byte[1024];//1kb
        while((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer,0,length);
        }
        //关闭输入流  注意 内存输出流可以不用关闭  官方文档说关闭无效
        inputStream.close();
        String content = new String(baos.toByteArray());

        return content;
    }

}
