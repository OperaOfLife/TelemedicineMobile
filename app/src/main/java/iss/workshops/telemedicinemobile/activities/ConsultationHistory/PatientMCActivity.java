package iss.workshops.telemedicinemobile.activities.ConsultationHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.domain.MedicalCertificate;

public class PatientMCActivity extends AppCompatActivity {

    MedicalCertificate mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_mc);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mcId");
        String dateFrom = intent.getStringExtra("mcDateFrom");
        String dateTo = intent.getStringExtra("mcDateTo");
        //int duration = intent.getIntExtra("mcDuration", 0);

        TextView mcId = findViewById(R.id.mcId);
        TextView mcDateFrom = findViewById(R.id.dateFrom);
        TextView mcDateTo = findViewById(R.id.dateTo);
        //TextView mcDuration = findViewById(R.id.duration);
        TableLayout table = findViewById(R.id.table);
        //TextView noMC = findViewById(R.id.noMC);

        //noMC.setVisibility(View.INVISIBLE);

        if (id != null) {
            if (mcId != null)
                mcId.setText(id);

            if (mcDateFrom != null && dateFrom != null)
                mcDateFrom.setText(dateFrom);

            if (mcDateTo != null && dateTo != null)
                mcDateTo.setText(dateTo);

            /*if (mcDuration != null && duration != 0)
                mcDuration.setText(String.valueOf(duration) + " day(s)");*/
        }
//        else {
//            table.setVisibility(View.INVISIBLE);
//            noMC.setVisibility(View.VISIBLE);
//        }

        //set up Back button for activity
        setupBackBtn();
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