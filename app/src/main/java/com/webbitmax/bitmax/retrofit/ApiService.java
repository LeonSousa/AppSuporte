package com.webbitmax.bitmax.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leonardo on 23/08/17.
 */

public class ApiService {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://186.233.7.29/webmin/apichamados/";

    //private static final String ROOT_URL = "https://api.github.com/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static RequestInterface getApiService() {
        return getRetrofitInstance().create(RequestInterface.class);
    }
}
