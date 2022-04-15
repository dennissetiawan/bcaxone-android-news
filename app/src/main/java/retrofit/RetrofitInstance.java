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

    public RetrofitInstance(){
        //TODO: for what?
        Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        newsAPIService = retrofit.create(NewsAPIService.class);

    }

    public NewsAPIService getNewsAPIService() {
        return newsAPIService;
    }
}
