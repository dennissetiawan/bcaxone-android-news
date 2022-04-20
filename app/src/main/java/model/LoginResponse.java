package model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("data")
	private LoginUserData loginUserData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("token")
	private String token;

	public LoginUserData getLoginUserData(){
		return loginUserData;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public String getToken(){
		return token;
	}
}