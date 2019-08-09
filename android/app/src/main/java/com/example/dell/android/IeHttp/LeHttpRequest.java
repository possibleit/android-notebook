package com.example.dell.android.IeHttp;

import java.io.File;
import java.util.Map;

public interface LeHttpRequest {

    void setUrl(String url);

    void setData(Map<String, String> data);

    void setFile(Map<String, File> file_map);

    void setListener(CallbackListener callbackListener);

    void execute();

}
