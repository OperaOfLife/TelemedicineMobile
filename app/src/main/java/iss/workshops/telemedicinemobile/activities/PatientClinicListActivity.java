package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



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

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

        import iss.workshops.telemedicinemobile.API;
        import iss.workshops.telemedicinemobile.PatientClinicListAdapter;
         import iss.workshops.telemedicinemobile.R;
        import iss.workshops.telemedicinemobile.RetrofitClient;
        import iss.workshops.telemedicinemobile.domain.Appointment;
        import iss.workshops.telemedicinemobile.domain.Clinic;
        import iss.workshops.telemedicinemobile.domain.Patient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class PatientClinicListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PatientClinicListAdapter patientClinicListAdapter;
    List<Clinic> clinicList = new ArrayList<>();
    Patient patient;
    TextView patientFirstName, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_clinic_list);

        Intent intent = getIntent();
        String zone = intent.getStringExtra("zone");

        //display zone selected in title
        title = findViewById(R.id.cliniclist_title);
        if (zone != null && title != null) {
            title.setText("Clinics (" + zone + ")");
        }

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


        displayClinicsByZone(zone);
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


    private void displayClinicsByZone(String zone) {
        Call<List<Clinic>> clinicCall = RetrofitClient
                .getInstance()
                .getAPI()
                .getClinics(zone);

        clinicCall.enqueue(new Callback<List<Clinic>>() {

            @Override
            public void onResponse(Call<List<Clinic>> call, Response<List<Clinic>> response) {
                //if method call is successful...

                //check response (200-300 = successful; if not means something went wrong e.g. response 404)
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "error retrieving clinics", Toast.LENGTH_LONG).show();
                    return;
                }

                clinicList = response.body();

                //sort by date (most recent till earliest date) before putting into recyclerview (12/8/2021)



                //put appointmentList items into recyclerview and notifyDataSetChanged()
                for (int i = 0; i < clinicList.size(); i++) {
                    patientClinicListAdapter = new PatientClinicListAdapter(getApplicationContext(), clinicList);
                    recyclerView.setAdapter(patientClinicListAdapter);
                    patientClinicListAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


}