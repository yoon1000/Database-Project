package com.example.acha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviequeueActivity extends AppCompatActivity {
  private ListView listView;
  private RetrofitCommunication service;
  private MovieQueueAdapter retroAdapter;
  private String user;
  JSONObject obj;
  ArrayList<MovieList> modelListViewArrayList;
  private Button addOrder;

  String movieName;
  String Date;
  String accountType;
  int Isfull;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviequeue);

      user = SharedPreference.getAttribute(MoviequeueActivity.this, "id");
      accountType = SharedPreference.getAttribute(MoviequeueActivity.this, "accountType");
      Log.d("account TYpe", accountType);
      listView = findViewById(R.id.lv2);
      addOrder = findViewById(R.id.AddOrder);
      Date = getToday();
      getJSONResponse();


      service = RetrofitConnection.getClient().create(RetrofitCommunication.class);
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("You choose a 'unlimited plan'.");

      addOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          if (accountType == "unlimited") {
            builder.setTitle("Do you want to watch first movie in your queue?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                JsonObject logindata = new JsonObject();
                logindata.addProperty("id", user);
                Call<JsonObject> FirstMovie = service.firstMovie(logindata);
                FirstMovie.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject result = response.body();
                    JsonArray jsonArray = result.getAsJsonArray("result");
                    JsonElement jsonElement = jsonArray.get(0);
                    movieName = jsonElement.getAsJsonObject().get("MovieName").getAsString();
                    SharedPreference.setAttribute(getApplicationContext(), "moviename", movieName);
                    Log.d("setting", movieName);
                  }

                  @Override
                  public void onFailure(Call<JsonObject> call, Throwable t) {

                  }
                });
                String result = SharedPreference.getAttribute(getApplicationContext(), "moviename");
                Log.d("Shared result", result);
                logindata.addProperty("movieName", result);
                logindata.addProperty("rentalDate", Date);
                Call<JsonObject> AddOrder = service.addOrder(logindata);
                AddOrder.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("result", response.body().get("result").toString());
                  }

                  @Override
                  public void onFailure(Call<JsonObject> call, Throwable t) {

                  }
                });

                Call<JsonObject> DeleteQueue = service.deleteQueue(logindata);
                DeleteQueue.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                  }

                  @Override
                  public void onFailure(Call<JsonObject> call, Throwable t) {

                  }
                });
                Intent intent = new Intent(MoviequeueActivity.this, MoviequeueActivity.class);
                startActivity(intent);
                finish();
              }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
              }
            });
            builder.show();

          }
          else {
            JsonObject logindata = new JsonObject();
            logindata.addProperty("id", user);
            Call<JsonObject> OrderFull = service.orderFull(logindata);
            OrderFull.enqueue(new Callback<JsonObject>() {
              @Override
              public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject result = response.body();
                Log.d("limited", result.get("result").toString());
                JsonArray jsonArray = result.getAsJsonArray("result");
                JsonElement jsonElement = jsonArray.get(0);
                Isfull = jsonElement.getAsJsonObject().get("COUNT(MovieName)").getAsInt();
                Log.d("Isfull", String.valueOf(Isfull));

                if(Isfull > 2){
                  Toast.makeText(MoviequeueActivity.this, "you are not allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                  builder.setTitle("Do you want to watch first movie in your queue?");
                  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      JsonObject logindata = new JsonObject();
                      logindata.addProperty("id", user);
                      Call<JsonObject> FirstMovie = service.firstMovie(logindata);
                      FirstMovie.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                          JsonObject result = response.body();
                          JsonArray jsonArray = result.getAsJsonArray("result");
                          JsonElement jsonElement = jsonArray.get(0);
                          movieName = jsonElement.getAsJsonObject().get("MovieName").getAsString();
                          SharedPreference.setAttribute(getApplicationContext(), "moviename", movieName);
                          Log.d("setting", movieName);
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                      });
                      String result = SharedPreference.getAttribute(getApplicationContext(), "moviename");
                      Log.d("Shared result", result);
                      logindata.addProperty("movieName", result);
                      logindata.addProperty("rentalDate", Date);
                      Call<JsonObject> AddOrder = service.addOrder(logindata);
                      AddOrder.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                          Log.d("result", response.body().get("result").toString());
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                      });

                      Call<JsonObject> DeleteQueue = service.deleteQueue(logindata);
                      DeleteQueue.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                      });
                      Intent intent = new Intent(MoviequeueActivity.this, MoviequeueActivity.class);
                      startActivity(intent);
                      finish();
                    }
                  });
                  builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                  });
                  builder.show();




                }
              }

              @Override
              public void onFailure(Call<JsonObject> call, Throwable t) {

              }
            });

          }
        }
      });

    }

  public static String getToday(){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return simpleDateFormat.format(new Date());
  }

  private void getJSONResponse() {
    service = RetrofitConnection.getClient().create(RetrofitCommunication.class);
    JsonObject logindata = new JsonObject();
    logindata.addProperty("id", user);
      Call<JsonObject> call = service.movieQueue(logindata);
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
