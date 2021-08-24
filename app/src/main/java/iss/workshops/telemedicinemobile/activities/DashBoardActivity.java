package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardActivity extends AppCompatActivity {

    TextView tvQueue, tvPatients, tvDoctors, tvWaiting;
    BarChart bcAppoints ;

    int todayAppoint;
    int patientNum;
    int doctorNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


                //get current queue size - number of appointments today
                //Total number of patients
                //number of doctors
                //estimated waiting time. queue size x 30mins

                tvQueue = findViewById(R.id.todaysize);
                tvPatients = findViewById(R.id.totalpatient);
                tvDoctors = findViewById(R.id.totaldoctor);
                tvWaiting = findViewById(R.id.waitingtime);
                bcAppoints = findViewById(R.id.barchart);


                //APIs to call
                getTodaySize();
                getListOfAppoints();
            }

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
                            Toast.makeText(DashBoardActivity.this, "Unsuccessful" + response.code(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DashBoardActivity.this, "Unsuccessful" + response.code(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DashBoardActivity.this, "Unsuccessful"+ response.code(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DashBoardActivity.this, "Unsuccessful"+ response.code(), Toast.LENGTH_SHORT).show();
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