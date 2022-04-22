package model;

import com.google.gson.annotations.SerializedName;

public class LoginUserData{

	@SerializedName("remember_exp")
	private Object rememberExp;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("last_login")
	private String lastLogin;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("oauth_uid")
	private Object oauthUid;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("ip_address")
	private String ipAddress;

	@SerializedName("forgot_exp")
	private Object forgotExp;

	@SerializedName("remember_time")
	private Object rememberTime;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("last_activity")
	private Object lastActivity;

	@SerializedName("top_secret")
	private Object topSecret;

	@SerializedName("oauth_provider")
	private Object oauthProvider;

	@SerializedName("id")
	private String id;

	@SerializedName("banned")
	private String banned;

	@SerializedName("verification_code")
	private Object verificationCode;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public Object getRememberExp(){
		return rememberExp;
	}

	public String getCompanyId(){
		return companyId;
	}

	public String getLastLogin(){
		return lastLogin;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public Object getOauthUid(){
		return oauthUid;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getIpAddress(){
		return ipAddress;
	}

	public Object getForgotExp(){
		return forgotExp;
	}

	public Object getRememberTime(){
		return rememberTime;
	}

	public String getFullName(){
		return fullName;
	}

	public Object getLastActivity(){
		return lastActivity;
	}

	public Object getTopSecret(){
		return topSecret;
	}

	public Object getOauthProvider(){
		return oauthProvider;
	}

	public String getId(){
		return id;
	}

	public String getBanned(){
		return banned;
	}

	public Object getVerificationCode(){
		return verificationCode;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}
}