package iss.workshops.telemedicinemobile.activities.OurDoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Adapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.activities.APIUtils;
import iss.workshops.telemedicinemobile.activities.service.DoctorService;
import iss.workshops.telemedicinemobile.domain.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<Doctor> doctorList;
    EditText searchDoctor;
    DoctorService doctorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doctor);

        RetrofitClient.getInstance()
        recyclerView = (RecyclerView) findViewById(R.id.doctorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        doctorList=new ArrayList<>();
        getDoctorList();

    }

    private void getDoctorList() {
        Call<List<Doctor>> call = doctorService.listDoctors();
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()) {
                    doctorList = response.body();


                    adapter=new DoctorAdapter(getApplicationContext(),doctorList);
                    recyclerView.setAdapter(adapter);




                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}