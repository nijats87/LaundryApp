package com.start.laundryapp.retrofit;

;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {
    public final static String BASE_URL = "http://10.50.93.26:8017";

    private static Retrofit retrofit;


    public static ApiService getService() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    String accessToken = SharedPrefs.getToken() == null ? "" : SharedPrefs.getToken();

                    Request request = original.newBuilder()
                            .header("User-Agent", "Laundry/1.0 Android")
                            .header("Accept", "application/json")
                            .header("Authorization", accessToken)
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


//    interface MyCallback<T> {
//
//        void onResponse(T response);
//        void onFailure(Call<ApiResponse<T>> call, Throwable t);
//    }
//
//
//    static <T> void request(Call<ApiResponse<T>> call, final MyCallback<T> callback) {
//        call.enqueue(new Callback<ApiResponse<T>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<T>> call, retrofit2.Response<ApiResponse<T>> response) {
//                if (response.isSuccessful() && response.body().success) {
//                    callback.onResponse(response.body().result);
//                } else {
//                    callback.onFailure(call, null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<T>> call, Throwable t) {
//                callback.onFailure(call, t);
//            }
//        });
//    }

}
