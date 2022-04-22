package retrofit;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.Map;

import model.LoginResponse;
import model.NewsAPIResponse;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface LoginAPIService {
    public static final String URL = "https://talentpool.oneindonesia.id/";

    @Multipart
    @POST("/api/user/login")
    Call<LoginResponse> loginPostRequest(@Header ("X-API-KEY") String xAPIKey,  @Part("username") RequestBody username, @Part("password") RequestBody password);

}
