package ir.ac.sbu.crisismanaging.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService
{
    private static final String EMULATOR_URL = "http://10.0.2.2:8000/";
    private static final String DEVICE_URL = "https://safe-tor-71958.herokuapp.com/";
    private static ApiClient apiClient;

    public static ApiClient getWebService()
    {
        if (apiClient == null)
        {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


            httpClient.addInterceptor(chain ->
            {
                Request original = chain.request();
                String originalPath = original.url().url().getPath();
                //no header when get token is send
                //build request
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DEVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();

            apiClient = retrofit.create(ApiClient.class);
        }
        return apiClient;
    }
}
