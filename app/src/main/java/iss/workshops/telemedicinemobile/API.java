package iss.workshops.telemedicinemobile;

import java.util.List;

import iss.workshops.telemedicinemobile.domain.Doctor;
import iss.workshops.telemedicinemobile.domain.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API
{

    @POST("userRest/authenticate")
    Call<User> login(@Query("uname") String username, @Query("pwd") String password);


    //doctors
    @GET("/api/doctor/list")
    Call<List<Doctor>> listDoctors();

    @GET("/api/searchDoctor/{keyword}")
    Call<List<Doctor>> searchDoctors(@Path("keyword") String keyword);


}