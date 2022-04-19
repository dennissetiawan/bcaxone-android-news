package retrofit;


import java.util.Map;

import model.NewsAPIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsAPIService {
    public static final String BASE_URL = "https://newsapi.org/";

    @GET("/v2/everything")
    Call<NewsAPIResponse> getEverything(@QueryMap Map<String,String> query);

    @GET("/v2/top-headlines")
    Call<NewsAPIResponse> getTopHeadlines(@QueryMap Map<String,String> query);
}
