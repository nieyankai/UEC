package com.example.uec_app.http;

import retrofit2.Retrofit;

public class UserService {

    Retrofit retrofit;
    IUserService userService;

    public UserService(){
        retrofit = new Retrofit.Builder().baseUrl(IUserService.baseUrl).build();
        userService = retrofit.create(IUserService.class);
    }
}
