package com.piersoft.vm;

import com.google.gson.Gson;
import com.piersoft.vm.dto.response.GSTByPanResponseDTO;
import com.piersoft.vm.dto.response.GSTResponseDTO;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SurepassUtil {

    @Value("${surepass.token}")
    private String token;

    @Value("${surepass.era_domain}")
    private String eraDomain;

    @Autowired
    private ApiClientUtil apiClientUtil;

    public GSTResponseDTO fetchGSTDetails(String gstNumber){
        RequestBody requestBody = new FormBody.Builder()
                .add("id_number",gstNumber)
                .add("filing_status_get","false")
                .build();
        String response =  apiClientUtil.makePostCall(eraDomain+"/api/v1/corporate/gstin",token,  requestBody );
        return  new Gson().fromJson(response, GSTResponseDTO.class);
    }

    public GSTByPanResponseDTO fetchPANDetails(String panNo){
        RequestBody requestBody = new FormBody.Builder()
                .add("id_number",panNo)
                .build();
        String response = apiClientUtil.makePostCall(eraDomain+"/api/v1/corporate/gstin-by-pan",token, requestBody );
        return  new Gson().fromJson(response, GSTByPanResponseDTO.class);
    }



}
