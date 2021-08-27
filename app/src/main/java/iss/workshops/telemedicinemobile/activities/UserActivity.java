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

import com.google.android.material.textfield.TextInputLayout;

import iss.workshops.telemedicinemobile.API;
import iss.workshops.telemedicinemobile.MainActivity;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.activities.PatientHomeActivity;
import iss.workshops.telemedicinemobile.domain.Patient;
import iss.workshops.telemedicinemobile.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    TextInputLayout usernameTIL;
    TextInputLayout passwordTIL;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    Intent intentToPatientHome,intentToBookConsultation;

    String username;
    String password;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intentToPatientHome = new Intent(this, MainActivity.class);


        loginButton = findViewById(R.id.login_button);
        usernameTIL= findViewById(R.id.login_name_layout);
        passwordTIL= findViewById(R.id.login_password_layout);



        SharedPreferences pref = getSharedPreferences("user_credentials" , MODE_PRIVATE);
        if ( pref.contains("username") && pref.contains("password"))
        {
            String user = pref.getString("username","");
            String pwd= pref.getString("password","");

            usernameTIL.getEditText().setText(user);
            passwordTIL.getEditText().setText(pwd);

        }



        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                username = usernameTIL.getEditText().getText().toString();
                password = passwordTIL.getEditText().getText().toString();
                if (usernameTIL.getEditText().getText().length() > 0 && passwordTIL.getEditText().getText().length() > 0) {

                    SharedPreferences pref= getSharedPreferences("user_credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();


                    loginUser(username, password);


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




            Call<Boolean> call = RetrofitClient
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

    public void userLogin(Call<Boolean> call)
    {
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Unsuccessful"+response.code(), Toast.LENGTH_SHORT).show();
                }
                else if(response.body() == false) {
                    Toast.makeText(UserActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                else if (response.body() == true) {

                    Toast.makeText(UserActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                    intentToPatientHome.putExtra("username", username);
                    startActivity(intentToPatientHome);
                } 

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}