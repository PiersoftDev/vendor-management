package com.piersoft.vm;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class ApiClientUtil {

    @Value("${surepass.token}")
    private String token;

    @Value("${surepass.era_domain}")
    private String eraDomain;

    public String makePostCall(String url , RequestBody requestBody){
        OkHttpClient client = new OkHttpClient.Builder().build();


        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization",token)
                .url(eraDomain+url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);


        try {
            Long start = System.currentTimeMillis();
            Response response = call.execute();
            Long end = System.currentTimeMillis();
            System.out.println("Time taken to get gst call execution details:"+(end-start));
            ResponseBody responseBody = response.body();
            return  new String(responseBody.bytes(), Charset.forName("ISO-8859-1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
