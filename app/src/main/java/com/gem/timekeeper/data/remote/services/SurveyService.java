package com.gem.timekeeper.data.remote.services;

import com.gem.timekeeper.data.dto.CompleteSurveyListDTO;
import com.gem.timekeeper.data.dto.SurveyListDTO;
import com.gem.timekeeper.data.remote.callback.CommandResponse;
import com.gem.timekeeper.data.remote.callback.SimpleQueryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by BaVV on 03/21/2018.
 * SurveyService
 */

public interface SurveyService {

  String SERVICE_NAME = "";

  @GET("queries/Merit.Components.Surveys.Queries/SurveyQueries/GetBestSurveyTemplates/results")
  Call<SimpleQueryResponse<SurveyListDTO>> getSurveyList(@Query("title") String filter);

  @POST("commands/Merit.Components.Surveys.Commands/CreateBestSurveyOnTabletCommand/publish")
  Call<CommandResponse> submitSurvey(@Body CompleteSurveyListDTO completeSurveyList);
}