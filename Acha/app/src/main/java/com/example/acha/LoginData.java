package com.example.acha;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
      @SerializedName("userId")
      @Expose
      String userId;

      @SerializedName("userPw")
      @Expose
      String userPw;

      public String getUserId(){
        return userId;
      }
      public String getUserPw(){
        return userPw;
      }

      public LoginData(String userId, String userPw) {
          this.userId = userId;
          this.userPw = userPw;
      }
}
