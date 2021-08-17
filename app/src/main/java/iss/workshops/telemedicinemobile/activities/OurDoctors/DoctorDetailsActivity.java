package iss.workshops.telemedicinemobile.activities.OurDoctors;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import iss.workshops.telemedicinemobile.R;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView name,speciality,hospital,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        name=findViewById(R.id.name);
        speciality=findViewById(R.id.speciality);
        hospital=findViewById(R.id.hospital);
        content=findViewById(R.id.content1);



        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        speciality.setText(intent.getStringExtra("speciality"));
        hospital.setText("University Health Centre (UHC)");
        content.setText(intent.getStringExtra("description"));



    }
}