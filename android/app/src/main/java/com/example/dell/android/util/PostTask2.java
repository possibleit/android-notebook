package com.example.dell.android.util;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class PostTask2 {//post发送数据给服务器，参数1 服务器url 参数二 字符串数据 参数三 二进制文件流数据
    //返回服务器response
    public static String submitData(String url, Map<String,String > params,
                                    Map<String, File> files){
        //byte[] data = getRequestData(params,encode).toString().getBytes();
        StringBuilder sb2 = new StringBuilder();
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--" , LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        try{
            URL url1 = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

//            httpURLConnection.setRequestProperty("Content-Type","application/x-www-from-urlencoded");
//            httpURLConnection.setRequestProperty("Content-Length",String.valueOf(data.length));

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET+LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }

//            OutputStream outputStream = httpURLConnection.getOutputStream();
            DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outStream.write(sb.toString().getBytes());

//            int response = httpURLConnection.getResponseCode();
//            if(response == HttpURLConnection.HTTP_OK){
//                InputStream inptStream = httpURLConnection.getInputStream();
//                return dealResponseResult(inptStream);
//            }

            if(files!=null){
                //int i = 0;
                for (Map.Entry<String, File> file: files.entrySet()) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    //sb1.append("Content-Disposition: form-data; name=\"file"+(i++)+"\"; filename=\""+file.getKey()+"\""+LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"userupfile\"; filename=\""+file.getKey()+"\""+LINEND);
                    sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());

                    InputStream is = new FileInputStream(file.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }

                    is.close();
                    outStream.write(LINEND.getBytes());
                }

                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
                outStream.write(end_data);
                outStream.flush();

                int res = httpURLConnection.getResponseCode();
                InputStream in = null;
                if (res == 200) {
                    in = httpURLConnection.getInputStream();
                    int ch;

                    while ((ch = in.read()) != -1) {
                        sb2.append((char) ch);
                    }
                    Log.i("CAMERA", sb2.toString());
                }

                return in == null ? null : sb2.toString();

            }

        }catch (IOException e) {
            //e.printStackTrace();
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }

//    public static StringBuffer getRequestData(Map<String ,String> params, String encode){
//        StringBuffer stringBuffer = new StringBuffer();
//        try{
//            for(Map.Entry<String, String> entry : params.entrySet()){
//                stringBuffer.append(entry.getKey())
//                        .append("=")
//                        .append(URLEncoder.encode(entry.getValue(),encode))
//                        .append("&");
//            }
//            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return stringBuffer;
//    }
//
//    public static String dealResponseResult(InputStream inputStream) {
//        String resultData = null;      //存储处理结果
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] data = new byte[1024];
//        int len = 0;
//        try {
//            while((len = inputStream.read(data)) != -1) {
//                byteArrayOutputStream.write(data, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        resultData = new String(byteArrayOutputStream.toByteArray());
//        return resultData;
//    }
}
