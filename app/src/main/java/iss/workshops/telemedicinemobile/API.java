package iss.workshops.telemedicinemobile;

import iss.workshops.telemedicinemobile.domain.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API
{

    @POST("userRest/authenticate")
    Call<User> login(@Query("username") String username, @Query("password") String password);


}
