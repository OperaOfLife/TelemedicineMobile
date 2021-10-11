package iss.workshops.telemedicinemobile;

import java.util.ArrayList;
import java.util.List;


import iss.workshops.telemedicinemobile.activities.ourChatBot.ChatResponse;
import iss.workshops.telemedicinemobile.domain.Clinic;
import iss.workshops.telemedicinemobile.domain.Doctor;

import iss.workshops.telemedicinemobile.domain.Appointment;
import iss.workshops.telemedicinemobile.domain.Patient;

import iss.workshops.telemedicinemobile.domain.TimeSlots;
import iss.workshops.telemedicinemobile.domain.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

  /*  @POST("userRest/authenticate")
    Call<User> login(@Query("uname") String username, @Query("pwd") String password);*/

    @POST("userRest/authenticate")
    Call<Boolean> login(@Query("uname") String username, @Query("pwd") String password);


    //doctors
    @GET("/api/doctor/list")
    Call<List<Doctor>> listDoctors();

    @GET("/api/searchDoctor/{keyword}")
    Call<List<Doctor>> searchDoctors(@Path("keyword") String keyword);

    @FormUrlEncoded
    @POST("/chat")
    Call<ChatResponse> chatWithTheBit(@Field("chatInput") String chatText);

    @POST("/appointmentRest/setAppointment")
    Call<Appointment> postAppointment(@Body Appointment appointment, @Query("date") String date);

    @GET("/appointmentRest/getAllDoctors")
    Call<ArrayList<Doctor>> doctors();

    //validate timeslots
    @POST("/appointmentRest/validate")
    Call<Void> validateAppointment(@Body Appointment appointment, @Query("date") String date);
    /*@POST("/appointmentRest/setAppointment")
    Call<Appointment> postAppointment(@Body Appointment appointment);*/

    @GET("api/list")
    Call<List<Appointment>> getAppointments(@Query("patientId") String username);


    @GET("api/patient")
    Call<Patient> getPatients(@Query("patientId") String patientId);

    //ddashboard APIs
    @GET("/todaysize")
    Call<Integer> getTodayNum();

    @GET("/numofpatients")
    Call<Integer> getNumOfPatients();

    @GET("/numofdoctors")
    Call<Integer> getNumOfDoctors();

    @GET("/numofappointments")
    Call<ArrayList<Integer>> getNumofAppointments();


    //KAT (24/8/2021) - not hardcoded - for retrieving clinics by zone
    @GET("api/clinics")
    Call<List<Clinic>> getClinics(@Query("zone") String zone);

    //KAT (25/8/2021) - not hardcoded - for retrieving all clinics
    @GET("api/allclinics")
    Call<List<Clinic>> getAllClinics();

    //KAT (26/8/2021) - not hardcoded - for retrieving searched clinics
    @GET("api/searchedclinics")
    Call<List<Clinic>> getSearchedClinics(@Query("searchValue") String searchValue);


    @POST("registerRest/registerPatient")
    Call<Boolean> register(@Query("nric") String nric, @Query("fname") String fname, @Query("lname") String lname, @Query("email") String email, @Query("mobile") String mobile, @Query("pwd") String pwd1, @Query("gender") String gender);

    @POST("registerRest/updatePatient")
    Call<Boolean> updateUserProfile(@Query("nric") String nric, @Query("fname") String fname, @Query("lname") String lname, @Query("email") String email, @Query("mobile") String mobile, @Query("pwd") String pwd1, @Query("gender") String gender);
}

