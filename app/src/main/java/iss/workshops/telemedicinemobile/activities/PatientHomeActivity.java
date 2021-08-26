package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import iss.workshops.telemedicinemobile.R;

import iss.workshops.telemedicinemobile.activities.BookConsultation.BookConsultationActivity;
import iss.workshops.telemedicinemobile.activities.ConsultationHistory.ConsultationHistoryActivity;
import iss.workshops.telemedicinemobile.activities.HealthNews.HealthNewsActivity;
import iss.workshops.telemedicinemobile.activities.OurDoctors.DoctorActivity;

import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.activities.ourChatBot.ChatBotMainActivity;
import iss.workshops.telemedicinemobile.domain.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatientHomeActivity extends AppCompatActivity {

    Intent intentToDashBoard,intentToHistory,intentToDoctors,intentToBook,intentToHealthNews,intentToChatBot;

     Context context;

    TextView textViewDashBoard,textViewDoctors,textViewHistory,textViewBook,textViewHealth, textViewUserName,textViewChatBot;
    Patient patient;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        intentToDoctors = new Intent(this, DoctorActivity.class);
        intentToBook = new Intent(this, BookConsultationActivity.class);
        intentToHistory = new Intent(this, ConsultationHistoryActivity.class);
        intentToHealthNews = new Intent(this, HealthNewsActivity.class);
        intentToChatBot = new Intent(this, ChatBotMainActivity.class);
        intentToDashBoard = new Intent(this, DashBoardActivity.class);

        textViewDoctors = findViewById(R.id.ourDoctors);
        textViewBook = findViewById(R.id.bookConsultation);
        textViewHistory = findViewById(R.id.consultationHistory);
        textViewHealth = findViewById(R.id.healthNews);
        textViewChatBot = findViewById(R.id.chatBot);
        textViewDashBoard = findViewById(R.id.dashboard);
        textViewDoctors.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHistory.setMovementMethod(LinkMovementMethod.getInstance());
        textViewBook.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHealth.setMovementMethod(LinkMovementMethod.getInstance());
        textViewChatBot.setMovementMethod(LinkMovementMethod.getInstance());
        textViewDashBoard.setMovementMethod(LinkMovementMethod.getInstance());

        //KAT (14/8/2021) - get patient user name to display on top right of activity
        Intent response = getIntent();
        if (response != null) {
            //get patient first name from username
            username = response.getStringExtra("username");
            getPatientFirstName(username);
            textViewUserName = findViewById(R.id.tv_userName);

            textViewUserName.setText(username);
            //pass username to next activity
            intentToHistory.putExtra("username", username);
        }

        //dashboard
        textViewDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToDashBoard);
            }
        });




        textViewDoctors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(intentToDoctors); }
        });

        textViewBook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getPatients(username);
                 }
        });

        textViewHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(intentToHistory); }
        });

        textViewHealth.setOnClickListener(new View.OnClickListener()
        {

        @Override
        public void onClick(View v) {

        startActivity(intentToHealthNews); }
    });

        textViewChatBot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(intentToChatBot); }
        });
}


    private void getPatients(String username) {

        Call<Patient> bookCall = RetrofitClient
                .getInstance()
                .getAPI()
                .getPatients(username);

        bookCall.enqueue(new Callback<Patient>() {

            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                //if method call is successful...


                //check response (200-300 = successful; if not means something went wrong e.g. response 404)
                if (!response.isSuccessful()) {


                    Toast.makeText(getApplicationContext(), "error in response", Toast.LENGTH_LONG).show();
                    return;
                }

                patient = response.body();

                //send user details over
                intentToBook.putExtra("patients", patient);
                startActivity(intentToBook);

            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });


    }


    private void getPatientFirstName(String username){
        Call<Patient> patientCall=RetrofitClient
        .getInstance()
        .getAPI()
        .getPatients(username);


        patientCall.enqueue(new Callback<Patient>() {

            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                //if method call is successful...


                //check response (200-300 = successful; if not means something went wrong e.g. response 404)
                if (!response.isSuccessful()) {


                    Toast.makeText(getApplicationContext(), "error in response", Toast.LENGTH_LONG).show();
                    return;
                }

                patient = response.body();
                String firstName = patient.getFirstName();
                if (textViewUserName != null && firstName != null) {
                    textViewUserName.setText(firstName);
                }

            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}