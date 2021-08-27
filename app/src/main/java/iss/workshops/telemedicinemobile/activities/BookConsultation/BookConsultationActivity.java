package iss.workshops.telemedicinemobile.activities.BookConsultation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import iss.workshops.telemedicinemobile.API;
import iss.workshops.telemedicinemobile.MainActivity;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Doctor;
import iss.workshops.telemedicinemobile.domain.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookConsultationActivity extends AppCompatActivity {


    TextView mUsername;
    ListView lvDoctor;
    ArrayList<Doctor> doctors;
    Intent intentToBookingPage;
    Button homeBtn;

    Patient patient;
    Intent intentToDashboard;


    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_consultation);

        lvDoctor = (ListView) findViewById(R.id.doclist);
        setuphomebtn();

        //get intent
        Intent intent = getIntent();

        patient = (Patient) intent.getSerializableExtra("patient");

            getDoctors();
            lvDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intentToBookingPage = new Intent(BookConsultationActivity.this, BookingPageActivity.class);
                    //send selected doctor and user details to booking page
                    intentToBookingPage.putExtra("doctor", doctors.get(position));
                    intentToBookingPage.putExtra("patient", patient);
                    startActivity(intentToBookingPage);

                }
            });
        }

    private void setuphomebtn() {
        homeBtn = findViewById(R.id.homeBtn);

        if (homeBtn != null) {
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentToDashboard = new Intent(BookConsultationActivity.this,MainActivity.class);
                    intentToDashboard.putExtra("username",patient.getPatientId());
                    startActivity(intentToDashboard);
                }
            });
        }
    }
    private void getDoctors() {
        Call<ArrayList<Doctor>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .doctors();
        displayDoctors(call);
    }

    public void displayDoctors(Call<ArrayList<Doctor>> call) {

        call.enqueue(new Callback<ArrayList<Doctor>>() {
            @Override
            public void onResponse(Call<ArrayList<Doctor>> call, Response<ArrayList<Doctor>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(BookConsultationActivity.this, "Unsuccessful" + response.code(), Toast.LENGTH_LONG).show();
                } else {
                    //get list of doctors objects
                    doctors = response.body();
                    //call custom adapter
                    BookCustomAdapter adapter = new BookCustomAdapter(BookConsultationActivity.this, R.layout.row_book_consultation, doctors);
                    lvDoctor.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Doctor>> call, Throwable t) {
                t.getStackTrace();
            }
        });
    }
}