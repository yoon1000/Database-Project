package com.example.acha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovielistActivity extends AppCompatActivity {

    private int menu;
    private RetrofitCommunication service;
    private EditText keyword;
    private Button KeywordSearch;

    private ListView listView;
    private MovieAdapter retroAdapter;
    JSONObject obj;
    ArrayList<MovieList> modelListViewArrayList;
    String keyword1;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_movielist);

      listView = findViewById(R.id.lv);
      keyword = findViewById(R.id.Keyword);
      KeywordSearch = findViewById(R.id.KeywordSearch);
      String iddddd = SharedPreference.getAttribute(getApplicationContext(), "id");
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle("");


      service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

      menu =getIntent().getIntExtra("menuList", 0);
      Log.d("menu", String.valueOf(menu));

      getJSONResponse(menu);


      KeywordSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          Log.d("Search", "serch");
              //SharedPreference.setAttribute(MovielistActivity.this, "Keyword", keyword.getText().toString());
              Intent intent = new Intent(MovielistActivity.this, MovielistActivity.class);
              intent.putExtra("menuList", 7);
              startActivity(intent);
              finish();
        }
      });


      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          String I = modelListViewArrayList.get(position).getTitle();
          //int I = parent.getPositionForView(view);
          Log.d("position", I);
          builder.setTitle("Do you want to add " + I + " to your queue?");
          builder.setMessage(I);
          builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {

                JsonObject logindata = new JsonObject();
                logindata.addProperty("id", iddddd);
                logindata.addProperty("movieName", I);
                Call<JsonObject> login = service.addMovie(logindata);
                login.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("add movie", response.body().get("message").toString());
                  }

                  @Override
                  public void onFailure(Call<JsonObject> call, Throwable t) {

                  }
                });
                //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
              }
            });
          builder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
              }
            });
          builder.show();
          //Toast.makeText(getApplicationContext(), "item click",Toast.LENGTH_LONG).show();
        }
      });

    }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.ASCENDING:
        Intent intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 1);
        startActivity(intent);
        finish();
        return true;
      case R.id.DESCENDING:
        intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 2);
        startActivity(intent);
        finish();
        return true;
      case R.id.Action:
        intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 3);
        startActivity(intent);
        finish();
        return true;
      case R.id.Drama:
        intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 4);
        startActivity(intent);
        finish();
        return true;
      case R.id.Comedy:
        intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 5);
        startActivity(intent);
        finish();
        return true;
      case R.id.Romance:
        intent = new Intent(MovielistActivity.this, MovielistActivity.class);
        intent.putExtra("menuList", 6);
        startActivity(intent);
        finish();
        return true;
      case R.id.option:
        intent = new Intent(MovielistActivity.this, SettingActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void getJSONResponse(int i) {
    service = RetrofitConnection.getClient().create(RetrofitCommunication.class);
    if(i == 1) {
      Call<JsonObject> call = service.getMovie();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());

              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }
        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
        }
      });
    }
    else if(i == 2){
      Call<JsonObject> call = service.getMovieDesc();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());

              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
    else if(i == 3){
      Call<JsonObject> call = service.getMovieAction();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());
              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
    else if(i == 4){
      Call<JsonObject> call = service.getMovieDrama();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());
              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
    else if(i == 5){
      Call<JsonObject> call = service.getMovieComedy();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());
              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
    else if(i == 6){
      Call<JsonObject> call = service.getMovieRomance();
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());
              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
    else if(i == 7){
      
      JsonObject keyword = new JsonObject();
      keyword.addProperty("keyword", SharedPreference.getAttribute(MovielistActivity.this, "Keyword"));
      Call<JsonObject> call = service.movieKeyword(keyword);
      call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Log.i("onSuccess", response.body().toString());
              String jsonresponse = response.body().toString();
              writeListView(jsonresponse);
            } else {
              Log.i("onEmptyResponse", "Returned empty response");
            }
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });
    }
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

        retroAdapter = new MovieAdapter(this, modelListViewArrayList);
        listView.setAdapter(retroAdapter);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
