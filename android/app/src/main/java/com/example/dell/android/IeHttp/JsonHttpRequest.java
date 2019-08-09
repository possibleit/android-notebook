package com.example.dell.android.IeHttp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class JsonHttpRequest implements LeHttpRequest{

    private String url;
    private Map<String,String> data;
    private Map<String, File> file_map;
    private CallbackListener callbackListener;

    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识 随机生成
    private static final String CONTENT_TYPE = "multipart/form-data";     //内容类型
    private static final String LINE_END = "\r\n";
    private static final String PREFIX = "--";
    private static final String CHARSET = "utf-8";

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(Map<String,String> data) {
        this.data=data;
    }

    @Override
    public void setFile(Map<String, File> file_map) {
        this.file_map = file_map;
    }

    @Override
    public void setListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }


    private HttpURLConnection urlConnection;
    @Override
    public void execute() {
        URL url;

        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            urlConnection.connect();

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            if (data != null) {
                dos.writeBytes(getStrParams(data).toString());
                dos.flush();
            }

            StringBuilder fileSb = new StringBuilder();
            if(file_map != null) {
                for (Map.Entry<String, File> fileEntry : file_map.entrySet()) {
                    fileSb.append(PREFIX)
                            .append(BOUNDARY)
                            .append(LINE_END)
                            /**
                             * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
                             * filename是文件的名字，包含后缀名的 比如:abc.png
                             */
                            .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                    + fileEntry.getKey() + "\"" + LINE_END)
                            .append("Content-Type: image/jpg" + LINE_END) //此处的ContentType不同于 请求头 中Content-Type
                            .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                            .append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
                    dos.writeBytes(fileSb.toString());
                    dos.flush();
                    InputStream is = new FileInputStream(fileEntry.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        dos.write(buffer, 0, len);
                    }
                    is.close();
                    dos.writeBytes(LINE_END);
                }

                dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
                dos.flush();
                dos.close();
            }

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                callbackListener.onSuccess(in);
            }
            else {
                throw new RuntimeException("请求失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("请求失败");

        }finally {
            urlConnection.disconnect();
        }
    }

    private static StringBuilder getStrParams(Map<String,String> strParams){
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet() ){
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }
}
