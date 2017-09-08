package com.start.laundryapp;

import retrofit2.Call;
import retrofit2.http.GET;

interface APIInterface {

    @GET("/services/app/orderType/all")
    Call<MultipleResources> doGetListResources();
}
