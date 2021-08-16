package iss.workshops.telemedicinemobile;

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
import retrofit2.http.Query;

public interface API
{

    @POST("userRest/authenticate")
    Call<User> login(@Query("uname") String username, @Query("pwd") String password);

    //KAT - not hardcoded ; username = patientId
    @GET("api/list")
    Call<List<Appointment>> getAppointments(@Query("patientId") String username);


    //KAT (14/8/2021) - not hardcoded - for retrieving patient details
    @GET("api/patient")
    Call<Patient> getPatients(@Query("patientId") String patientId);


}
