package iss.workshops.telemedicinemobile.activities.BookConsultation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Appointment;
import iss.workshops.telemedicinemobile.domain.Doctor;
import iss.workshops.telemedicinemobile.domain.Patient;
import iss.workshops.telemedicinemobile.domain.TimeSlots;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingPageActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mUser;
    TextView mDoctor;
    AppCompatButton mBook;
    Spinner mTimeslots;
    TimeSlots selected;

    Patient inPatient;
    Doctor doctor;

    EditText mCalender;
    LocalDate xDate;
    String date;
    String newDate;
    String currentDate = " ";
    TextView mStatus;

    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

                Intent intent = getIntent();

                inPatient = (Patient)intent.getSerializableExtra("patient");
                doctor = (Doctor) intent.getSerializableExtra("doctor");

                displayHeaders();
                setUpCalender();
                setUpTimeSlots();
                setUpButton();

            }

            private void displayHeaders(){
                mUser = findViewById(R.id.patientname);
                mDoctor = findViewById(R.id.doctorname);
                mStatus = findViewById(R.id.status);

                if(mUser != null && mDoctor != null){
                    mUser.setText(inPatient.getFirstName()+" "+ inPatient.getLastName());
                    mDoctor.setText("Dr. "+ doctor.getFirstName());
                }

            }

            private void setUpCalender(){
                mCalender = findViewById(R.id.etCalender);

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);




                mCalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                BookingPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month +1;
                                //8/7/1993
                                date = day + "-" + month + "-" + year;
                                mCalender.setText(date);
                                calendar.set(year,month-1,day);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                newDate = sdf.format(calendar.getTime());
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

            }

            private void setUpTimeSlots(){
                mTimeslots = (Spinner)findViewById(R.id.timeslots);

                //basic adapter
                ArrayAdapter<TimeSlots> adapter = new ArrayAdapter<TimeSlots>(this, R.layout.support_simple_spinner_dropdown_item, TimeSlots.values());
                mTimeslots.setAdapter(adapter);

            }
            private void setUpButton(){
                mBook = (AppCompatButton) findViewById(R.id.book);
                mBook.setOnClickListener(this);
            }



    @Override
    public void onClick(View v) {
        setUpAppointment();
    }
    protected void setUpAppointment(){
        //create new appointment to store doctor and appointment date.
        if(doctor != null && newDate != null) {
            //validate if repeating date and supposedly timeslot
            if(currentDate.equals(newDate)){
                Toast.makeText(BookingPageActivity.this,"Duplicated Date time slot",Toast.LENGTH_SHORT).show();
            }
            else {
                currentDate = newDate;
                selected = (TimeSlots) mTimeslots.getSelectedItem();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
                //LocalDate someDate = LocalDate.parse(xDate,formatter);
                Appointment appointment = new Appointment();
                appointment.setDoctor(doctor);
                appointment.setPatient(inPatient);
                appointment.setAppointmentTime(selected);
                Call<Appointment> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .postAppointment(appointment, newDate);
                postAppointment(call);
            }
        }
    }
    private void postAppointment(Call<Appointment> call) {

        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(!response.isSuccessful())
                    Toast.makeText(BookingPageActivity.this, "Unsuccessful "+response.code(), Toast.LENGTH_SHORT).show();
                Appointment appoint = response.body();
                String content = " ";
                content += "Appointment set";
                mStatus.setText(content);
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                t.printStackTrace();
            }
        });


    } }