package com.example.acha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button registerbtn;
    private Button loginbtn;

    private TextView textView;
    private EditText Id;
    private EditText Password;

    private RetrofitCommunication service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn = (Button)findViewById(R.id.btnLogin);
        registerbtn = (Button)findViewById(R.id.btnRegister);
        textView = (TextView)findViewById(R.id.textView);
        Id = (EditText)findViewById(R.id.IdLogin);
        Password = (EditText)findViewById(R.id.pwLogin);

        service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SharedPreference.setAttribute(v.getContext(), "id", Id.getText().toString());
              attemptLogin();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
      Id.setError(null);
      Password.setError(null);

      String id = Id.getText().toString();
      String password = Password.getText().toString();

      boolean cancel = false;
      View focusView = null;


      if (password.isEmpty()) {
        Id.setError("비밀번호를 입력해주세요.");
        focusView = Id;
        cancel = true;
      } else if (!isPasswordValid(password)) {
        Password.setError("6자 이상의 비밀번호를 입력해주세요.");
        focusView = Password;
        cancel = true;
      }

      if (cancel) {
        focusView.requestFocus();
      } else {


        JsonObject logindata = new JsonObject();
        logindata.addProperty("id", id);
        logindata.addProperty("password", password);
        Call<JsonObject> login = service.userLogin(logindata);


        login.enqueue(new Callback<JsonObject>() {
          @Override
          public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            JsonObject result = response.body();
            if (response.body().get("code").getAsInt() == 200) {
              Log.d("LoginSuccess", result.get("code").toString());
              Intent intent = new Intent(MainActivity.this, HomeActivity.class);
              startActivity(intent);
            } else {
              Log.d("LoginError", result.get("code").toString());
            }
          }

          @Override
          public void onFailure(Call<JsonObject> call, Throwable t) {
            Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
            Log.e("로그인 에러 발생", t.getMessage());
          }
        });
      }


    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

}
