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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import iss.workshops.telemedicinemobile.PatientConsultationHistoryAdapter;
import iss.workshops.telemedicinemobile.R;

import iss.workshops.telemedicinemobile.activities.HealthNews.HealthNewsActivity;
import iss.workshops.telemedicinemobile.activities.OurDoctors.DoctorActivity;

import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Appointment;
import iss.workshops.telemedicinemobile.domain.Patient;
import iss.workshops.telemedicinemobile.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatientHomeActivity extends AppCompatActivity {

    Intent intentToHistory,intentToDoctors,intentToBook,intentToHealthNews;

     Context context;

    TextView textViewDoctors,textViewHistory,textViewBook,textViewHealth, textViewUserName;
    Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        intentToDoctors = new Intent(this, DoctorActivity.class);
        intentToBook = new Intent(this, BookConsultationActivity.class);
        intentToHistory = new Intent(this, ConsultationHistoryActivity.class);
        intentToHealthNews = new Intent(this, HealthNewsActivity.class);

        textViewDoctors = findViewById(R.id.ourDoctors);
        textViewBook = findViewById(R.id.bookConsultation);
        textViewHistory = findViewById(R.id.consultationHistory);
        textViewHealth = findViewById(R.id.healthNews);

        textViewDoctors.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHistory.setMovementMethod(LinkMovementMethod.getInstance());
        textViewBook.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHealth.setMovementMethod(LinkMovementMethod.getInstance());

        //KAT (14/8/2021) - get patient user name to display on top right of activity
        Intent response = getIntent();
        if (response != null) {
            //get patient first name from username
            String username = response.getStringExtra("username");
            getPatientFirstName(username);
            textViewUserName = findViewById(R.id.tv_userName);

            //pass username to next activity
            intentToHistory.putExtra("username", username);
        }



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
                startActivity(intentToBook); }
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