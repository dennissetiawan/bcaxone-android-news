package retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private NewsAPIService newsAPIService;
    private LoginAPIService loginAPIService;
    public RetrofitInstance(String url){
        //TODO: for what?
        Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        newsAPIService = retrofit.create(NewsAPIService.class);
        loginAPIService = retrofit.create(LoginAPIService.class);
    }

    public NewsAPIService getNewsAPIService() {
        return newsAPIService;
    }
    public LoginAPIService getLoginAPIService() {
        return loginAPIService;
    }

}
