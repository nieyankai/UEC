package com.example.uec_app.http;


import com.example.uec_app.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserService {

    String baseUrl = "";

    @POST("user/new")
    Call<UserInfo> createUser(@Body UserInfo user);

    @POST("user/login")
    Call<Boolean> loginUser(@Body UserInfo user);

}
