package com.example.acha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyorderActivity extends AppCompatActivity {

  private RetrofitCommunication service;

  private ListView listView;
  private MovieQueueAdapter retroAdapter;
  JSONObject obj;
  ArrayList<MovieList> modelListViewArrayList;
  private String user;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        user = SharedPreference.getAttribute(MyorderActivity.this, "id");

        listView = findViewById(R.id.lv3);
        getJSONResponse();

    }

  private void getJSONResponse() {
    service = RetrofitConnection.getClient().create(RetrofitCommunication.class);
    JsonObject logindata = new JsonObject();
    logindata.addProperty("id", user);
    Call<JsonObject> call = service.myOrder(logindata);
    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        JsonObject result =response.body();
        if (result.get("code").getAsInt() == 200) {
          if (response.body() != null) {
            Log.i("onSuccess", response.body().toString());

            String jsonresponse = response.body().toString();
            writeListView(jsonresponse);
          } else {
            Log.i("onEmptyResponse", result.get("message").toString());
          }
        }
      }
      @Override
      public void onFailure(Call<JsonObject> call, Throwable t) {
      }
    });
  }
  private void writeListView(String response) {
    try {
      obj = new JSONObject(response);

      modelListViewArrayList = new ArrayList<>();
      JSONArray dataArray  = obj.getJSONArray("result");

      for (int i = 0; i < dataArray.length(); i++) {
        MovieList modelListView = new MovieList();
        JSONObject dataobj = dataArray.getJSONObject(i);

        modelListView.setTitle(dataobj.getString("MovieName"));
        modelListView.setGenre(dataobj.getString("MovieType"));
        //modelListView.setRating(dataobj.getString("city"));

        modelListViewArrayList.add(modelListView);

      }

      retroAdapter = new MovieQueueAdapter(this, modelListViewArrayList);
      listView.setAdapter(retroAdapter);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
