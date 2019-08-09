package com.example.dell.android.IeHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtils {

        private static boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }

        public static byte[] builderUrlParams(Map<String, String> params){
            Set<String> keySet = params.keySet();
            List<String> keyList = new ArrayList<>(keySet);
            Collections.sort(keyList);
            StringBuilder sb = new StringBuilder();
            for (String key : keyList) {
                String value = params.get(key);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                sb.append(key);
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(params.get(key),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString().getBytes();
        }
}
