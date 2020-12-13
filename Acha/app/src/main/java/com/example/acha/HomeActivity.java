package com.example.acha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
  private String user;

  private TextView message;
  private Button homebtn, queuebtn, myorder;

  private RetrofitCommunication service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        message = (TextView) findViewById(R.id.Message);
        homebtn = (Button) findViewById(R.id.Homebtn);
        queuebtn = (Button) findViewById(R.id.MovieQueue);
        myorder = (Button)findViewById(R.id.MyOrder) ;
        user = SharedPreference.getAttribute(HomeActivity.this, "id");
        service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

      JsonObject logindata = new JsonObject();
      logindata.addProperty("id", user);
      message.setText(user);
      Call<JsonObject> login = service.accountType(logindata);


      login.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          JsonObject result = response.body();
          //Log.d("received", result.get("account").toString());
          if (response.body().get("code").getAsInt() == 200) {
            SharedPreference.setAttribute(HomeActivity.this, "accountType", result.get("accountType").toString());
            message.setText(response.body().get("message").toString());
            homebtn.setText("Go to Movie List");

          } else {
            message.setText(response.body().get("message").toString());
            homebtn.setText("Please set up your account");
          }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
          Toast.makeText(HomeActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
          Log.e("로그인 에러 발생", t.getMessage());
        }
      });


      homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(homebtn.getText().toString() == "Go to Movie List"){
            Intent intent = new Intent(HomeActivity.this, MovielistActivity.class);
            intent.putExtra("menuList", 1);
            startActivity(intent);
          }
          else{
            Intent intent = new Intent(HomeActivity.this, AccounttypeActivity.class);
            startActivity(intent);
            finish();;
          }
        }
      });

      queuebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(HomeActivity.this, MoviequeueActivity.class);
          startActivity(intent);
        }
      });

      myorder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(HomeActivity.this, MyorderActivity.class);
          startActivity(intent);
        }
      });
    }
}
