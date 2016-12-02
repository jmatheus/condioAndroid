package com.juliovaz.condio.network;

import com.juliovaz.condio.util.ConstantsCondio;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


/**
 * Created by julio on 07/05/16.
 */
public abstract class ApiMethodsManager {

    public static ApiService getMethodGetService(){

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(ConstantsCondio.ACCEPT, ConstantsCondio.APPLICATION_JSON);
                request.addHeader(ConstantsCondio.CONTENT_TYPE, ConstantsCondio.APPLICATION_JSON_CHARSET_UTF_8);
            }
        };

        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint("http://condio-web.herokuapp.com")
                .setRequestInterceptor(requestInterceptor)
                .build();

        return retrofit.create(ApiService.class);
    }


}
