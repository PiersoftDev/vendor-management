package com.piersoft.vm;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WhatsappNotificationUtil {

    @Value("${waba.token}")
    private String token;

    @Value("${waba.url}")
    private String url;

    @Autowired
    private ApiClientUtil apiClientUtil;

    public void sendWelcomeMessage(){

        String body = "{ \"messaging_product\": \"whatsapp\", \"to\": \"919945014010\", \"type\": \"template\", \"template\": { \"name\": \"welcome_message_template\", \"language\": { \"code\": \"en_US\" } } }";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body );
        String response =  apiClientUtil.makePostCall(url, token, requestBody );
        System.out.println(response);
    }
}
