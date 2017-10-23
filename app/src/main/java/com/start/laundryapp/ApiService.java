package com.start.laundryapp;

import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderModel;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by nijats87 on 10/23/2017.
 */

interface ApiService {
    @GET("/api/services/app/order/my")
    Call<ApiResponse<ItemsHolder<OrderModel>>> orders();
}