package iss.workshops.telemedicinemobile.activities.ConsultationHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iss.workshops.telemedicinemobile.R;

public class PatientPrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);

        Intent intent = getIntent();
        int prescriptionId = intent.getIntExtra("prescriptionId", 0);
        String prescriptionMedicine = intent.getStringExtra("prescriptionMedicine");
        String prescriptionRemarks = intent.getStringExtra("prescriptionRemarks");

        TextView id = findViewById(R.id.prescriptionId);
        TextView medicine = findViewById(R.id.medicine);
        TextView remarks = findViewById(R.id.remarks);

        if (id != null)
            id.setText(String.valueOf(prescriptionId));

        if (medicine != null)
            medicine.setText(prescriptionMedicine);

        if (remarks != null)
            remarks.setText(prescriptionRemarks);

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