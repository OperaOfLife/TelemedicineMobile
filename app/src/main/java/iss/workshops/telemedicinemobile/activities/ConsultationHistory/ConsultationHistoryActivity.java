package iss.workshops.telemedicinemobile.activities.ConsultationHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import iss.workshops.telemedicinemobile.API;
import iss.workshops.telemedicinemobile.activities.ConsultationHistory.PatientConsultationHistoryAdapter;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Appointment;
import iss.workshops.telemedicinemobile.domain.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PatientConsultationHistoryAdapter patientConsultationHistoryAdapter;
    List<Appointment> appointmentList;
    Patient patient;
    TextView patientFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_history);

        //set up Back button for activity
        setupBackBtn();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //create retrofit object and define its base url that you declared in MethodCallAPI interface
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //create instance of MethodCallAPI interface so you can call methods that you need from the interface
        retrofit.create(API.class);

        Intent response = getIntent();
        String userName = response.getStringExtra("username");

        //get patient first name from username
        getPatientFirstName(userName);



        //get appointment details for appointment date, prescription and mc
        getAppointmentDetails(userName);
    }

    private void getPatientFirstName(String userName) {
        Call<Patient> patientCall = RetrofitClient
                .getInstance()
                .getAPI()
                .getPatients(userName);

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
                if (patientFirstName != null && firstName != null) {
                    patientFirstName.setText(firstName);
                }

            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getAppointmentDetails(String userName) {
        //now that we have a retrofit object and API object, we can call our API method getAppointments().
        Call<List<Appointment>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getAppointments(userName);

//we call the method on a background thread using enqueue instead of execute to prevent blocking.
        call.enqueue(new Callback<List<Appointment>>() {

            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                //if method call is successful...

                //check response (200-300 = successful; if not means something went wrong e.g. response 404)
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "error in response", Toast.LENGTH_LONG).show();
                    return;
                }

                //trying method 2 (11/8/2021)
                appointmentList = response.body();

                //sort by date (most recent till earliest date) before putting into recyclerview (12/8/2021)
                Collections.sort(appointmentList, Collections.reverseOrder(new Comparator<Appointment>() {
                    @Override
                    public int compare(Appointment o1, Appointment o2) {
                        if (o1.getAppointmentDate() == null || o2.getAppointmentDate() == null)
                            return 0;
                        return o1.getAppointmentDate().compareTo(o2.getAppointmentDate());
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }
                }));

                //put appointmentList items into recyclerview and notifyDataSetChanged()
                for (int i = 0; i < appointmentList.size(); i++) {
                    patientConsultationHistoryAdapter = new PatientConsultationHistoryAdapter(getApplicationContext(), appointmentList);
                    recyclerView.setAdapter(patientConsultationHistoryAdapter);
                    patientConsultationHistoryAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void setupBackBtn() {
        Button btn = findViewById(R.id.back);

        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.back) {
                        finish();
                    }
                }
            });
        }
    }
}