package iss.workshops.telemedicinemobile.activities.OurDoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;

import iss.workshops.telemedicinemobile.domain.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<Doctor> doctorList;
    EditText searchDoctors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doctor);


        recyclerView = (RecyclerView) findViewById(R.id.doctorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        doctorList=new ArrayList<>();
        searchDoctors=findViewById(R.id.searchDoctors);

        searchDoctors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!String.valueOf(searchDoctors.getText()).isEmpty()) {
                    searchDoctors(String.valueOf(searchDoctors.getText()));

                } else {

                    getDoctorList();
                }

            }
        });


        getDoctorList();

    }

    private void getDoctorList() {



                Call<List<Doctor>> call = RetrofitClient.getInstance().getAPI().listDoctors();
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
    public void searchDoctors(String keyword){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        Call<List<Doctor>> call = RetrofitClient.getInstance().getAPI().searchDoctors(keyword);
        call.enqueue(new Callback<List<Doctor>>(){
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response){
                if (response.isSuccessful()){
                    doctorList = response.body();
                    adapter = new DoctorAdapter(getApplicationContext(),doctorList);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    
}