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

import iss.workshops.telemedicinemobile.MainActivity;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Patient;
import iss.workshops.telemedicinemobile.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAccountActivity extends AppCompatActivity
{
    Patient patient;
    Intent response;
    String password;

    EditText nric,fname,lname,email,mobile,pwd,confirmpwd;

    RadioButton male,female;
    Button signup;
    Intent intentToPatientHome;

    String nric1,fname1,lname1,email1,mobile1,pwd1,confirmpwd1,male1,female1;
    String gender = "";
    String selectedGender1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("patient");
        response = getIntent();
        //username=response.getStringExtra("username");
        password = response.getStringExtra("password");



            intentToPatientHome = new Intent(this, MainActivity.class);

            nric = (EditText) findViewById(R.id.NRIC);
            fname = (EditText) findViewById(R.id.FirstName);
            lname = (EditText) findViewById(R.id.LastName);
            email = (EditText) findViewById(R.id.Email);
            mobile = (EditText) findViewById(R.id.Mobile);
            male = (RadioButton) findViewById(R.id.radioMale);
            female = (RadioButton) findViewById(R.id.radioFemale);
            pwd = (EditText) findViewById(R.id.Password);
            signup= findViewById(R.id.signupButton);

            nric.setText(patient.getPatientId());
            fname.setText(patient.getFirstName());
            lname.setText(patient.getLastName());
            email.setText(patient.getEmail());
            mobile.setText(patient.getMobile());
            pwd.setText(password);
            if(patient.getGender().equalsIgnoreCase("male"))
            {
                gender = "Male";
                male.setChecked(true);
            }
            else
            {
                gender = "Female";
                female.setChecked(true);
            }




            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nric1 = nric.getText().toString();
                    fname1 = fname.getText().toString();
                    lname1 = lname.getText().toString();
                    email1 = email.getText().toString();
                    mobile1 = mobile.getText().toString();
                    pwd1 = pwd.getText().toString();

                    if(male.isChecked())
                        selectedGender1 = "Male";
                    else
                        selectedGender1 = "Female";

                    if ( fname.getText().length() > 0 && lname.getText().length() > 0 &&
                            email.getText().length() > 0 && mobile.getText().length() > 0 && pwd.getText().length() > 0 )
                    {

                            update(nric1,fname1,lname1,email1,mobile1,pwd1,confirmpwd1,selectedGender1);


                    }
                    else
                    {
                        String toastMessage = "All the fields are not populated";
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


        private void update(String nric, String fname, String  lname, String email, String mobile,String pwd, String confirmpwd,String gender)
        {

            User u = new User();
            u.setUserName(nric);
            u.setPassword(pwd);




            Call<Boolean> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .updateUserProfile(nric,fname,lname,email,mobile,pwd,gender);


            updateUser(call);

        }



        public void updateUser(Call<Boolean> call)
        {
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(ManageAccountActivity.this, "Unsuccessful"+response.code(), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body() == false) {
                        Toast.makeText(ManageAccountActivity.this, "User Not found", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.body() == true) {

                        Toast.makeText(ManageAccountActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();

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