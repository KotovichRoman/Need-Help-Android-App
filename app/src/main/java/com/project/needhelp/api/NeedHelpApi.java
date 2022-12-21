package com.project.needhelp.api;

import com.project.needhelp.api.model.UserDTO;
import com.project.needhelp.classes.User;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NeedHelpApi {
    @GET("users")
    Single<ArrayList<UserDTO>> getUsers();

    @POST("auth")
    Single<User> getCurrentUser(@Header("Authorization") String token);

    @POST("auth/login")
    Single<UserDTO> getToken(@Body User loginInformation);
}
