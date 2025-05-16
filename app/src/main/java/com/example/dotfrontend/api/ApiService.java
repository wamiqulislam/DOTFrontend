package com.example.dotfrontend.api;

import com.example.dotfrontend.model.Batch;
import com.example.dotfrontend.model.ChangeDueAmountResponse;
import com.example.dotfrontend.model.ChangeParcelPropertiesResponse;
import com.example.dotfrontend.model.ParcelLog;
import com.example.dotfrontend.model.RegisterVehicleResponse;
import com.example.dotfrontend.model.SendParcelResponse;
import com.example.dotfrontend.model.User;
import com.example.dotfrontend.request.AssignRiderRequest;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/user/addUser")
    Call<User> addUser(@Body User user);

    @POST("api/user/signUp")
    Call<ResponseBody> signUp(@Body User user);

    @POST("api/parcel/addParcel")
    Call<ResponseBody> addParcel(@Body SendParcelResponse r);

    @POST("api/parcelLog/changeStatus")
    Call<ResponseBody> changeStatus(@Body ChangeParcelPropertiesResponse r);

    @POST("api/parcelLog/changeLocation")
    Call<ResponseBody> changeLocation(@Body ChangeParcelPropertiesResponse r);

    @POST("api/parcelLog/changeCurrentRider")
    Call<ResponseBody> changeCurrentRider(@Body ChangeParcelPropertiesResponse r);

    @POST("api/parcelLog/changeDeliveredDate")
    Call<ResponseBody> changeDeliveredDate(@Body ChangeParcelPropertiesResponse r);

    @POST("api/rider/registerVehicle")
    Call<ResponseBody> registerVehicle(@Body RegisterVehicleResponse r);

    @POST("api/user/login")
    Call<Boolean> login(@Body Map<String, String> credentials);

    @GET("/api/user/getuserfromemail")
    Call<User> getUserFromEmail(@Query("email") String email);

    @GET("/api/parcelLog")
    Call<ParcelLog> getParcelLog(@Query("parcelID") Long parcelID);

    @GET("/api/Batch")
    Call<List<Batch>> getBatchesByLocation(@Query("city") String city,
                                           @Query("country") String country);

    @POST("/api/Batch/assignrider")
    Call<Batch> assignRiderToBatch(@Body AssignRiderRequest req);

    @POST("/api/rider/changeDueAmount")
    Call<Void> changeDueAmount(@Body ChangeDueAmountResponse body);

}

