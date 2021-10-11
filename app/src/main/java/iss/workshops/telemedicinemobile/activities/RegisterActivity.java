package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
{
    EditText nric,fname,lname,email,mobile,pwd,confirmpwd;
    RadioGroup gender;
    RadioButton selectedGender,male,female;
    Button signup;
    Intent intentToLogin;

    String nric1,fname1,lname1,email1,mobile1,pwd1,confirmpwd1,male1,female1;
    String selectedGender1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intentToLogin = new Intent(this, UserActivity.class);

        nric = (EditText) findViewById(R.id.NRIC);
        fname = (EditText) findViewById(R.id.FirstName);
        lname = (EditText) findViewById(R.id.LastName);
        email = (EditText) findViewById(R.id.Email);
        mobile = (EditText) findViewById(R.id.Mobile);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);
        pwd = (EditText) findViewById(R.id.Password);
        confirmpwd = (EditText) findViewById(R.id.ConfirmPassword);
        signup= findViewById(R.id.signupButton);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nric1 = nric.getText().toString();
                fname1 = fname.getText().toString();
                lname1 = lname.getText().toString();
                email1 = email.getText().toString();
                mobile1 = mobile.getText().toString();
                pwd1 = pwd.getText().toString();
                confirmpwd1 = confirmpwd.getText().toString();
                //male1 = male.toString();
                confirmpwd1 = confirmpwd.getText().toString();


               /* int selected = gender.getCheckedRadioButtonId();
                selectedGender = (RadioButton) findViewById(selected);
                selectedGender1 = selectedGender.toString();*/
                if(male.isChecked())
                    selectedGender1 = "Male";
                else
                    selectedGender1 = "Female";

                if (nric.getText().length() > 0 && fname.getText().length() > 0 && lname.getText().length() > 0 &&
                        email.getText().length() > 0 && mobile.getText().length() > 0 && pwd.getText().length() > 0 &&
                        confirmpwd.getText().length() > 0 )
                {
                    if (!validate(nric1,fname1,lname1,email1,mobile1,pwd1,confirmpwd1))
                    {
                        String toastMessage = "All fields are not correct";
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        registerUser(nric1,fname1,lname1,email1,mobile1,pwd1,confirmpwd1,selectedGender1);
                    }


                }
                else
                {
                    String toastMessage = "All the fields are not populated";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void registerUser(String nric, String fname, String  lname, String email, String mobile,String pwd, String confirmpwd,String gender)
    {

            User u = new User();
            u.setUserName(nric);
            u.setPassword(pwd);




           Call<Boolean> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .register(nric,fname,lname,email,mobile,pwd,gender);


           createUser(call);

    }

    private boolean validate(String nric, String fname, String  lname, String email, String mobile,String pwd, String confirmpwd)
    {
        if(pwd1.equalsIgnoreCase(confirmpwd1))
        {

            return true;
        }
        else
        {
            String toastMessage = "Password and confirm Password are not same";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void createUser(Call<Boolean> call)
    {
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Unsuccessful"+response.code(), Toast.LENGTH_SHORT).show();
                }
                else if(response.body() == false) {
                    Toast.makeText(RegisterActivity.this, "User already found", Toast.LENGTH_SHORT).show();
                }
                else if (response.body() == true) {

                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                    startActivity(intentToLogin);
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}