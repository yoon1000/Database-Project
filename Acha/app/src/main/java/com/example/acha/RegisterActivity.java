package com.example.acha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button Register;

    private EditText Id;
    private EditText Fname;
    private EditText Lname;
    private EditText Email;
    private EditText pw;

  private RetrofitCommunication service;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_register);

          Register = (Button)findViewById(R.id.Register);

          Id = (EditText)findViewById(R.id.IdRegister);
          Fname = (EditText)findViewById(R.id.FnameRegister);
          Lname = (EditText)findViewById(R.id.LnameRegister);
          Email = (EditText)findViewById(R.id.emailRegister);
          pw = (EditText)findViewById(R.id.pwRegister);

          service = RetrofitConnection.getClient().create(RetrofitCommunication.class);

          Register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              attemptJoin();
          }
      });
    }

  private void attemptJoin() {
      JsonObject Joindata = new JsonObject();
      Joindata.addProperty("id", Id.getText().toString());
      Joindata.addProperty("Fname", Fname.getText().toString());
      Joindata.addProperty("Lname", Lname.getText().toString());
      Joindata.addProperty("email", Email.getText().toString());
      Joindata.addProperty("password", pw.getText().toString());
      Call<JsonObject> register = service.userRegister(Joindata);

      register.enqueue(new Callback<JsonObject>() {
          @Override
          public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
              JsonObject result = response.body();
              if (response.body().get("code").getAsInt() == 200) {
                  Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT)
                  .show();
                  finish();
              } else {
                  Log.d("Error", result.get("code").toString());
              }
          }

          @Override
          public void onFailure(Call<JsonObject> call, Throwable t) {
            Toast.makeText(RegisterActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
              Log.e("로그인 에러 발생", t.getMessage());
          }
      });
  }

}
