package com.adam.test;


import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpPostTest {

    public static void main(String[] args) throws IOException {

        HttpPostTest test = new HttpPostTest();
        test.post("http://localhost:8080/limiter.html");
    }

    private void post(String url) throws IOException {
        HttpPost httpRequest = new HttpPost(url);

        List<BasicNameValuePair> nvalues = new ArrayList<>();
        // 请求的参数
//        nvalues.add(new BasicNameValuePair("key1", "v1"));
//        nvalues.add(new BasicNameValuePair("key2", "v2"));
//        nvalues.add(new BasicNameValuePair("key3", "v3"));

        httpRequest.setEntity(new UrlEncodedFormEntity(nvalues));

        HttpResponse response = new DefaultHttpClient().execute(httpRequest);

        if (response.getStatusLine().getStatusCode() == 200) {// 获取调用api返回数据

                //doing something
            InputStream content = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(content);
            BufferedReader bufr = new BufferedReader(reader);//缓冲
            String str;
            while((str = bufr.readLine()) != null){
                System.out.println(str);
            }

        }

    }
}
