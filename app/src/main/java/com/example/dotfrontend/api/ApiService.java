package com.example.dotfrontend.api;

import com.example.dotfrontend.model.Batch;
import com.example.dotfrontend.model.Parcel;
import com.example.dotfrontend.response.ChangeBatchProperties;
import com.example.dotfrontend.response.ChangeDueAmountResponse;
import com.example.dotfrontend.response.ChangeParcelPropertiesResponse;
import com.example.dotfrontend.model.ParcelLog;
import com.example.dotfrontend.response.RegisterVehicleResponse;
import com.example.dotfrontend.response.SendParcelResponse;
import com.example.dotfrontend.model.User;
import com.example.dotfrontend.request.AssignRiderRequest;
import com.example.dotfrontend.response.SignUpResponse;

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
    Call<ResponseBody> signUp(@Body SignUpResponse user);

    @POST("api/parcel/addParcel")
    Call<ResponseBody> addParcel(@Body SendParcelResponse r);

    @POST("api/parcelLog/changeStatus")
    Call<ResponseBody> changeStatus(@Body ChangeParcelPropertiesResponse r);

    @POST("api/parcelLog/changeLocation")
    Call<ResponseBody> changeLocation(@Body ChangeParcelPropertiesResponse r);

    @POST("api/parcelLog/changeCurrentRider")
    Call<ResponseBody> changeCurrentRider(@Body ChangeParcelPropertiesResponse r);

    @POST("api/rider/registerVehicle")
    Call<ResponseBody> registerVehicle(@Body RegisterVehicleResponse r);

    @POST("api/user/login")
    Call<Boolean> login(@Body Map<String, String> credentials);

    @GET("/api/user/getuserfromemail")
    Call<User> getUserFromEmail(@Query("email") String email);

    @GET("/api/parcelLog")
    Call<ParcelLog> getParcelLog(@Query("parcelID") Long parcelID);

    @GET("/api/Batch/byLocation")
    Call<List<Batch>> getBatchesByLocation(@Query("city") String city,
                                           @Query("country") String country);

    @POST("/api/Batch/assignRider")
    Call<Batch> assignRiderToBatch(@Body AssignRiderRequest req);

    @POST("/api/user/changeDueAmount")
    Call<Void> changeDueAmount(@Body ChangeDueAmountResponse body);

    @POST("/api/Batch/dropBatch")
    Call<ResponseBody> dropBatch(@Query("batchId") long batchId);

    // Change location using @RequestBody
    @POST("/api/Batch/changeLocation")
    Call<ResponseBody> changeLocation(@Body ChangeBatchProperties changeRequest);

    @POST("/api/parcelLog/changeDeliveredDate")
    Call<Void> changeDeliveredDate(@Body ChangeParcelPropertiesResponse body);

    @GET("/api/Batch/getriderbatches")
    Call<List<Batch>> getRiderBatches(@Query("riderId") long riderId);

    @GET("/api/parcel/getparcelsofbatch")
    Call<List<Parcel>> getParcelsOfBatch(@Query("batchId") long batchId);

    @GET("/api/parcel/getparcelsofcustomer")
    Call<List<Parcel>> getParcelsOfCustomer(@Query("customerId") Long customerId);

}

