package com.example.acha;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitCommunication {

  @GET("/")
  Call<JsonObject> getTest();

  @POST("/user/join")
  Call<JsonObject> userRegister(@Body JsonObject userData);

  @POST("/user/login")
  Call<JsonObject> userLogin(@Body JsonObject data);

  @POST("/user/accountType")
  Call<JsonObject> accountType(@Body JsonObject id);

  @GET("/movie/ascending")
  Call<JsonObject> getMovie();

  @GET("/movie/descending")
  Call<JsonObject> getMovieDesc();

  @GET("/movie/genre/Action")
  Call<JsonObject> getMovieAction();

  @GET("/movie/genre/Drama")
  Call<JsonObject> getMovieDrama();

  @GET("/movie/genre/Comedy")
  Call<JsonObject> getMovieComedy();

  @GET("/movie/genre/Romance")
  Call<JsonObject> getMovieRomance();

  @POST("/user/accountType/plan")
  Call<JsonObject> planChoose(@Body JsonObject account);

  @POST("/user/account")
  Call<JsonObject> account(@Body JsonObject id);

  @POST("/user/movieQueue")
  Call<JsonObject> movieQueue(@Body JsonObject id);

  @POST("/user/addMovie")
  Call<JsonObject> addMovie(@Body JsonObject id);

  //1.큐에 있는 첫 영화 꺼내서 2.오더에 넣은 후 3.큐에서 지우가
  @POST("/user/FirstMovie")
  Call<JsonObject> firstMovie(@Body JsonObject id);

  @POST("/user/addOrder")
  Call<JsonObject> addOrder(@Body JsonObject id);

  @POST("/user/deleteQueue")
  Call<JsonObject> deleteQueue(@Body JsonObject id);

  @POST("/user/OrderFull")
  Call<JsonObject> orderFull(@Body JsonObject id);

  @POST("/user/myOrder")
  Call<JsonObject> myOrder(@Body JsonObject id);

  @POST("/user/movieKeyword")
  Call<JsonObject> movieKeyword(@Body JsonObject Keyword);

  @GET("/movie/les")
  Call<JsonObject> getLes();
}
