package iss.workshops.telemedicinemobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import iss.workshops.telemedicinemobile.activities.BookConsultation.BookConsultationActivity;
import iss.workshops.telemedicinemobile.activities.ConsultationHistory.ConsultationHistoryActivity;

import iss.workshops.telemedicinemobile.activities.HealthNews.HealthNewsActivity;
import iss.workshops.telemedicinemobile.activities.OurDoctors.DoctorActivity;
import iss.workshops.telemedicinemobile.activities.ourChatBot.ChatBotMainActivity;
import iss.workshops.telemedicinemobile.domain.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Intent intentToDashBoard,intentToHistory,intentToDoctors,
            intentToBook,intentToHealthNews,intentToChatBot;
    Patient patient;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String username;
    Intent response;
    
    //dashboard
    TextView tvQueue, tvPatients, tvDoctors, tvWaiting;
    BarChart bcAppoints ;

    int todayAppoint;
    int patientNum;
    int doctorNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response = getIntent();
        username = response.getStringExtra("username");


        intentToDoctors = new Intent(this, DoctorActivity.class);
        intentToBook = new Intent(this, BookConsultationActivity.class);
        intentToHistory = new Intent(this, ConsultationHistoryActivity.class);
        intentToHealthNews = new Intent(this, HealthNewsActivity.class);
        intentToChatBot = new Intent(this, ChatBotMainActivity.class);
        intentToDashBoard = new Intent(this, MainActivity.class);

        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


        //after the cloud completed
        //loadUserInformation();


        //tool bar
        setSupportActionBar(toolbar);
       //nav
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        
        //dashboard
        tvQueue = findViewById(R.id.todaysize);
        tvPatients = findViewById(R.id.totalpatient);
        tvDoctors = findViewById(R.id.totaldoctor);
        tvWaiting = findViewById(R.id.waitingtime);
        bcAppoints = findViewById(R.id.barchart);


        //APIs to call
        getTodaySize();
        getListOfAppoints();





    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
//          case R.id.dashboard:
//              startActivity(intentToDashBoard);
//              break;
          case R.id.nav_healthnews:
              startActivity(intentToHealthNews);
              break;
          case R.id.nav_OurDoctors:
              startActivity(intentToDoctors);
              break;
          case R.id.nav_BookConsultation:
              patient=getPatients(username);
              intentToBook.putExtra("patient", patient);
              startActivity(intentToBook);
              break;
          case R.id.nav_ConsultationHistory:
              intentToHistory.putExtra("username",username);
              startActivity(intentToHistory);
              break;
          case R.id.nav_SymptomChecker:
              startActivity(intentToChatBot);
              break;
          default:
              throw new IllegalStateException("Unexpected value: " + item.getItemId());
      }

        return true;
    }
    private void loadUserInformation(){
        if(!username.equals("username")) {
        View view = navigationView.getHeaderView(0);
        TextView name=(TextView)view.findViewById(R.id.head_user_name);
        name.setText(username);

        Patient patient = getPatients(username);
        TextView email = (TextView) view.findViewById(R.id.head_user_email);
        email.setText(patient.getEmail());
        }


    }


    private Patient getPatients(String username) {
        Patient patient1;

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

            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

        return patient;

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


            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    //dashboard



    synchronized private void getTodaySize(){
        Call<Integer> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getTodayNum();
        todaySize(call);

    }
    synchronized private void getTotalPatients(){
        Call<Integer> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getNumOfPatients();
        totalPatients(call);
    }
    synchronized private void getTotalDoctors(){
        Call<Integer> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getNumOfDoctors();
        totalDoctors(call);
    }
    synchronized private void getWaiting(){
        //if patient / doctor < 0 , display text
        if(todayAppoint < doctorNum){
            tvWaiting.setText("No Queue");
        }
        else {
            int time = patientNum * 30;
            int average = (int) ((double) time / (double) doctorNum);

            tvWaiting.setText(String.valueOf(average) + "minutes");
        }
    }
    private void getListOfAppoints(){
        Call<ArrayList<Integer>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getNumofAppointments();
        totalAppointments(call);
    }
    private void todaySize(Call<Integer> call){

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Unsuccessful" + response.code(), Toast.LENGTH_SHORT).show();
                }
                todayAppoint = response.body();
                tvQueue.setText(String.valueOf(todayAppoint));
                getTotalPatients();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void totalPatients(Call<Integer> call){
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful())
                    Toast.makeText(MainActivity.this, "Unsuccessful" + response.code(), Toast.LENGTH_SHORT).show();
                patientNum = response.body();
                tvPatients.setText(String.valueOf(patientNum));
                getTotalDoctors();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void totalDoctors(Call<Integer> call){
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful())
                    Toast.makeText(MainActivity.this, "Unsuccessful"+ response.code(), Toast.LENGTH_SHORT).show();
                doctorNum = response.body();
                tvDoctors.setText(String.valueOf(doctorNum));
                getWaiting();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void totalAppointments(Call<ArrayList<Integer>> call){
        call.enqueue(new Callback<ArrayList<Integer>>() {
            @Override
            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                if(!response.isSuccessful())
                    Toast.makeText(MainActivity.this, "Unsuccessful"+ response.code(), Toast.LENGTH_SHORT).show();
                ArrayList<Integer> weeklylist = response.body();
                showWeeklyAppointments(weeklylist);
            }

            @Override
            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showWeeklyAppointments(ArrayList<Integer> weeklylist){
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        ArrayList<BarEntry> weekly = new ArrayList<>();

        for(int i = 0; i < weeklylist.size(); i++){
            weekly.add(new BarEntry(i,(Integer)weeklylist.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(weekly, "This Week's Appointments");
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        bcAppoints.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        bcAppoints.setFitBars(true);
        bcAppoints.setData(barData);
        bcAppoints.setVisibleXRangeMinimum(7);
        bcAppoints.getDescription().setText("This Week's Appointments");
        bcAppoints.animateY(2000);


    }
    
    

}
