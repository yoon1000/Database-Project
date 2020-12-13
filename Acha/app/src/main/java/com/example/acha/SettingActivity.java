package com.example.acha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    private String id;
    private String accountType;
    private TextView Fname, Lname, Id, Email, Accounttype;
    private RetrofitCommunication service;

    private Button change, unsubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        Fname = findViewById(R.id.settingFname);
        Lname = findViewById(R.id.settingLname);
        Id = findViewById(R.id.settingId);
        Email = findViewById(R.id.settingEmail);
        Accounttype = findViewById(R.id.settingPlan);
        change = findViewById(R.id.ChangePlan);
        unsubscribe = findViewById(R.id.Unsubscribe);
        service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

      id = SharedPreference.getAttribute(SettingActivity.this, "id");
      //accountType = SharedPreference.getAttribute(SettingActivity.this, "accountType");
      Log.d("SharedPreference",id);

      JsonObject logindata = new JsonObject();
      logindata.addProperty("id", id);
      Call<JsonObject> setting = service.account(logindata);
      setting.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          JsonObject result = response.body();
          JsonArray jsonArray = result.getAsJsonArray("result");
          JsonElement jsonElement = jsonArray.get(0);
          Fname.setText(jsonElement.getAsJsonObject().get("Fname").getAsString());
          Lname.setText(jsonElement.getAsJsonObject().get("Lname").getAsString());
          Id.setText(jsonElement.getAsJsonObject().get("CustomerId").getAsString());
          Email.setText(jsonElement.getAsJsonObject().get("Email").getAsString());
          Accounttype.setText(jsonElement.getAsJsonObject().get("AccountType").getAsString());
          Log.d("setting", result.get("result").toString());
          //Fname.setText(result.get("Fname").toString());

        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
      });

      change.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(Accounttype.getText().toString() == "unlimited"){

          }
          else{

          }
        }
      });
      unsubscribe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
      });

    }
}
