package ir.ac.sbu.crisismanaging.Service;

import ir.ac.sbu.crisismanaging.Pojos.FormDescriptorApiResponse;
import ir.ac.sbu.crisismanaging.Pojos.Form;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient
{
    @GET("form_descriptors")
    Call<FormDescriptorApiResponse> getFormDescriptors();

    @GET("form_descriptors/{id}")
    Call<FormDescriptorApiResponse> getFormDescriptors(@Path("id") String id);

    @POST("forms")
    Call<FormDescriptorApiResponse> sendForm(@Body Form form);
}
