package iss.workshops.telemedicinemobile.activities.OurDoctors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.activities.BookConsultation.BookingPageActivity;
import iss.workshops.telemedicinemobile.domain.Doctor;
import iss.workshops.telemedicinemobile.domain.Patient;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView name, speciality, content;
    Button homeBtn;
    Button bookBtn;
    Patient p;
    Doctor d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        name = findViewById(R.id.name);
        speciality = findViewById(R.id.speciality);
        bookBtn = findViewById(R.id.bookbutton);


        content = findViewById(R.id.content1);


        Intent intent = getIntent();
        p = (Patient) intent.getSerializableExtra("patient");
        d = (Doctor) intent.getSerializableExtra("doctor");

        name.setText(intent.getStringExtra("name"));
        speciality.setText(intent.getStringExtra("speciality"));

        content.setText(intent.getStringExtra("description"));


        setuphomebtn();
        setupbookbtn();

    }

    private void setuphomebtn() {
        homeBtn = findViewById(R.id.homeBtn);

        if (homeBtn != null) {
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void setupbookbtn() {


        if (bookBtn != null) {
            bookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), BookingPageActivity.class);
                    intent.putExtra("patient", p);
                    intent.putExtra("doctor", d);
                    startActivity(intent);


                }
            });
        }


    }
}