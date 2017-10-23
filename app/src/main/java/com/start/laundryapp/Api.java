package com.start.laundryapp;

;import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nijats87 on 10/23/2017.
 */

public class Api {
    public final static String BASE_URL = "http://138.201.157.254:8017";

    private static Retrofit retrofit;


    public static ApiService getService() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("User-Agent", "LaundryApp/1.0 Android")
                            .header("Accept", "application/json")
                            .header("Authorization", SharedPrefs.getToken())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit.create(ApiService.class);
    }
}
