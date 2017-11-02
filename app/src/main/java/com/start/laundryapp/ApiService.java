package com.start.laundryapp;

import com.start.laundryapp.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("/api/services/app/order/my")
    Call<ApiResponse<ItemsHolder<OrderModel>>> orders();

    @GET("/api/services/app/terminalPoint/all")
    Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> terminalPoints();

    @GET("/api/services/app/orderType/all")
    Call<ApiResponse<ItemsHolder<OrderTypeModel>>> orderTypes();

    @GET("api/services/app/orderExecutionType/all")
    Call<ApiResponse<ItemsHolder<ExecutionTypeModel>>> executionTypes();

    @GET("/api/services/app/clothesType/all")
    Call<ApiResponse<ItemsHolder<ClothesTypeModel>>> clothesTypes();

    @POST("/api/services/app/order/create")
    Call<ApiResponse<Void>> makeOrder(@Body MakeOrderModel body);

    @POST("/api/Account/Register")
    Call<ApiResponse<RegisterResultModel>> register(@Body RegisterModel registerModel);

    @POST("/api/Account/Authenticate")
    Call<ApiResponse<LoginResultModel>> login(@Body LoginModel loginModel);
}