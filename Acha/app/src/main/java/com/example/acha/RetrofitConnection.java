package com.example.acha;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConnection {

  private final static String BASE_URL = "http://10.0.2.2:3000";
  private static Retrofit retrofit = null;

  private RetrofitConnection() {
  }

  public static Retrofit getClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }

    return retrofit;
  }
}
