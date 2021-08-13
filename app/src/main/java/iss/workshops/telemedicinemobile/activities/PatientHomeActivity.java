package iss.workshops.telemedicinemobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import iss.workshops.telemedicinemobile.R;

public class PatientHomeActivity extends AppCompatActivity {

    Intent intentToHistory,intentToDoctors,intentToBook,intentToHealthNews;
    TextView textViewDoctors,textViewHistory,textViewBook,textViewHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        intentToDoctors = new Intent(this, OurDoctorsActivity.class);
        intentToBook = new Intent(this, BookConsultationActivity.class);
        intentToHistory = new Intent(this, ConsultationHistoryActivity.class);
        intentToHealthNews = new Intent(this, HealthNewsActivity.class);

        textViewDoctors = findViewById(R.id.ourDoctors);
        textViewBook = findViewById(R.id.bookConsultation);
        textViewHistory = findViewById(R.id.consultationHistory);
        textViewHealth = findViewById(R.id.healthNews);

        textViewDoctors.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHistory.setMovementMethod(LinkMovementMethod.getInstance());
        textViewBook.setMovementMethod(LinkMovementMethod.getInstance());
        textViewHealth.setMovementMethod(LinkMovementMethod.getInstance());

        textViewDoctors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                       startActivity(intentToDoctors); }
        });

        textViewBook.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v) {
        startActivity(intentToBook); }
        });

        textViewHistory.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v) {
        startActivity(intentToHistory); }
        });

        textViewHealth.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v) {
        startActivity(intentToHealthNews); }
    });
}


}