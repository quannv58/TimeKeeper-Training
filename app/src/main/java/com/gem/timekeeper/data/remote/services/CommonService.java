package com.gem.timekeeper.data.remote.services;


import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.data.remote.callback.SimpleQueryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by BaVV on 11/17/17.
 * CommonService
 */

public interface CommonService {

  @POST("token")
  @FormUrlEncoded
  Call<UserDTO> login(@Field("username") String address,
                      @Field("password") String password,
                      @Field("grant_type") String grantType,
                      @Field("kiosk_app") boolean isKioskApp);

  @POST("token")
  @FormUrlEncoded
  Call<UserDTO> refreshToken(@Field("username") String address,
                             @Field("password") String password,
                             @Field("grant_type") String grantType,
                             @Field("refresh_token") String refreshToken);

  @GET("queries/Merit.Components.Surveys.Queries/SurveyQueries/RegisterDeviceNotificationForKios/results")
  Call<SimpleQueryResponse<Boolean>> registerDeviceToken(@Query("deviceToken") String deviceToken,
                                                            @Query("deviceType") int deviceType);
}
