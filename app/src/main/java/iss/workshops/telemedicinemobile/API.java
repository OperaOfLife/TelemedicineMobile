package iss.workshops.telemedicinemobile;

import java.util.List;


import iss.workshops.telemedicinemobile.activities.ourChatBot.ChatResponse;
import iss.workshops.telemedicinemobile.domain.Doctor;

import iss.workshops.telemedicinemobile.domain.Appointment;
import iss.workshops.telemedicinemobile.domain.Patient;

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

    @FormUrlEncoded
    @POST("/chat")
    Call<ChatResponse> chatWithTheBit(@Field("chatInput") String chatText);


    //KAT - not hardcoded ; username = patientId
    @GET("api/list")
    Call<List<Appointment>> getAppointments(@Query("patientId") String username);


    //KAT (14/8/2021) - not hardcoded - for retrieving patient details
    @GET("api/patient")
    Call<Patient> getPatients(@Query("patientId") String patientId);

}

