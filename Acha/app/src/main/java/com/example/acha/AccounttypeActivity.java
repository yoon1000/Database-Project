package com.example.acha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccounttypeActivity extends AppCompatActivity {

  private RetrofitCommunication service;
  private String user;

  final String limited = "The limited plan allows one to view up to 2 movie at-a-time and at most 2 movies per month.";
  final String unlimited = "The unlimited plan allow one to view up to 2 movies at-a-time and place no limit on how many movies you can view per month.";

  private TextView L;
  private TextView U;

  private LinearLayout unlimitbtn;
  private LinearLayout limitbtn;

  String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounttype);

        L = (TextView)findViewById(R.id.limited);
        U = (TextView)findViewById(R.id.unlimited);
        limitbtn = (LinearLayout) findViewById(R.id.Limited);
        unlimitbtn = (LinearLayout) findViewById(R.id.Unlimited);
        L.setText(limited);
        U.setText(unlimited);

        user = SharedPreference.getAttribute(AccounttypeActivity.this, "id");
        service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

        Date = getToday();

        unlimitbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AccounttypeActivity.this);
            builder.setTitle("You choose a 'unlimited plan'.").setMessage("Are you sure?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                JsonObject AccountUpdate = new JsonObject();
                AccountUpdate.addProperty("account", "unlimited");
                AccountUpdate.addProperty("id", user);
                AccountUpdate.addProperty("date", Date);
                Call<JsonObject> planChoose = service.planChoose(AccountUpdate);

                planChoose.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("received", response.body().toString());
                  }

                  @Override
                  public void onFailure(Call<JsonObject> call, Throwable t) {

                  }
                });


                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
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
        });

      limitbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          AlertDialog.Builder builder = new AlertDialog.Builder(AccounttypeActivity.this);
          builder = new AlertDialog.Builder(AccounttypeActivity.this);
          builder.setTitle("You choose a 'limited plan'.").setMessage("Are you sure?");
          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              JsonObject AccountUpdate = new JsonObject();
              AccountUpdate.addProperty("account", "limited");
              AccountUpdate.addProperty("id", user);
              AccountUpdate.addProperty("date", Date);
              Call<JsonObject> planChoose = service.planChoose(AccountUpdate);

              planChoose.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                  Log.d("received", response.body().toString());
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
              });


              Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
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
      });
    }

    public static String getToday(){
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.format(new Date());
    }
}
