package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.StrictMode;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iss.workshops.telemedicinemobile.API;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.activities.PatientHomeActivity;
import iss.workshops.telemedicinemobile.domain.Patient;
import iss.workshops.telemedicinemobile.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    Intent intentToPatientHome,intentToBookConsultation;

    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        intentToPatientHome = new Intent(this, PatientHomeActivity.class);

        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);




        SharedPreferences pref = getSharedPreferences("user_credentials" , MODE_PRIVATE);
        if ( pref.contains("username") && pref.contains("password"))
        {
            String user = pref.getString("username","");
            String pwd= pref.getString("password","");

            usernameEditText.setText(user);
            passwordEditText.setText(pwd);
        }



        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0) {

                    String username=usernameEditText.getText().toString();
                    String password=passwordEditText.getText().toString();

                    SharedPreferences pref= getSharedPreferences("user_credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();


                    loginUser(username, password);


                    intentToPatientHome.putExtra("username", username);

                    startActivity(intentToPatientHome);

                }
                else
                {
                    String toastMessage = "Username or Password are not populated";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String username, String password)
    {
        if (!validate(username, password))
        {
            String toastMessage = "Username or Password are not populated";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
        }
        else
        {
            User u = new User();
            u.setUserName(username);
            u.setPassword(password);




            Call<User> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .login(username,password);


            userLogin(call);
        }
    }

    private boolean validate(String username, String password) {
        if (username.isEmpty() || password.isEmpty())
        {
            return false;


        }
        return true;
    }

    public void userLogin(Call<User> call)
    {
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()== 200)
                {
                    String msg= "Successful" + response.code();

                    Toast.makeText(UserActivity.this, msg, Toast.LENGTH_LONG).show();


                }
                else {


                    Toast.makeText(UserActivity.this, "UNSuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(UserActivity.this, "Something went wrong! Try again later", Toast.LENGTH_LONG).show();


            }
        });
    }


}