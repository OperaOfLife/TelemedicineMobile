package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import iss.workshops.telemedicinemobile.R;
  import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.LocationManager;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Looper;
        import android.util.Log;
        import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
        import android.widget.Toast;


        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

        import iss.workshops.telemedicinemobile.API;
        import iss.workshops.telemedicinemobile.PatientClinicListAdapter;
        import iss.workshops.telemedicinemobile.R;
        import iss.workshops.telemedicinemobile.RetrofitClient;
        import iss.workshops.telemedicinemobile.domain.Clinic;
        import iss.workshops.telemedicinemobile.domain.Patient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class LocateClinicsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView latitudeTV, longitudeTV, addressTV;
    private Button btn_userLocation;
    private static final int AFL_REQUEST_CODE = 1;
    private static final int R_REQUEST_CODE = 2;
     Patient patient;
    Intent intentToClinicList;
    RecyclerView recyclerView;
    PatientClinicListAdapter patientClinicListAdapter;
    List<Clinic> clinicList = new ArrayList<>();
    EditText searchBar;
    ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_clinics);

        //set up NSEW buttons
        setupNSEWBtns();

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
        searchBar = findViewById(R.id.searchBar);
        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setImageResource(R.drawable.ic_search);

        //button onclick animation
        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

        if (searchBtn != null && searchBar != null) {
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String searchValue = searchBar.getText().toString();
                    if (searchValue != null) {
                        Toast.makeText(LocateClinicsActivity.this, "Search Results", Toast.LENGTH_SHORT).show();
                        view.startAnimation(buttonClick);
                        displaySearchedClinics(searchValue);
                    }
                }
            });
        }

        displayClinics();

    }

    private void displaySearchedClinics(String searchValue) {
        Call<List<Clinic>> searchCall = RetrofitClient
                .getInstance()
                .getAPI()
                .getSearchedClinics(searchValue);

        searchCall.enqueue(new Callback<List<Clinic>>() {

            @Override
            public void onResponse(Call<List<Clinic>> call, Response<List<Clinic>> response) {
                //if method call is successful...

                //check response (200-300 = successful; if not means something went wrong e.g. response 404)
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "error searching clinics" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                clinicList = response.body();

                //sort by date (most recent till earliest date) before putting into recyclerview (12/8/2021)
                //Collections.sort(clinicList);

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

    private void setupNSEWBtns() {
        Button nBtn = findViewById(R.id.northButton);
        Button sBtn = findViewById(R.id.southButton);
        Button eBtn = findViewById(R.id.eastButton);
        Button wBtn = findViewById(R.id.westButton);

        nBtn.setOnClickListener(this);
        sBtn.setOnClickListener(this);
        eBtn.setOnClickListener(this);
        wBtn.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch(id) {
            case(R.id.northButton):
                intentToClinicList = new Intent(LocateClinicsActivity.this,  PatientClinicListActivity.class);
                intentToClinicList.putExtra("zone", "North");
                startActivity(intentToClinicList);
                break;

            case(R.id.southButton):
                intentToClinicList = new Intent(LocateClinicsActivity.this, PatientClinicListActivity.class);
                intentToClinicList.putExtra("zone", "South");
                startActivity(intentToClinicList);
                break;

            case(R.id.eastButton):
                intentToClinicList = new Intent(LocateClinicsActivity.this, PatientClinicListActivity.class);
                intentToClinicList.putExtra("zone", "East");
                startActivity(intentToClinicList);
                break;

            case(R.id.westButton):
                intentToClinicList = new Intent(LocateClinicsActivity.this, PatientClinicListActivity.class);
                intentToClinicList.putExtra("zone", "West");
                startActivity(intentToClinicList);
                break;
        }
    }


    private void displayClinics() {
        Call<List<Clinic>> clinicCall = RetrofitClient
                .getInstance()
                .getAPI()
                .getAllClinics();

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
                //Collections.sort(clinicList);

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