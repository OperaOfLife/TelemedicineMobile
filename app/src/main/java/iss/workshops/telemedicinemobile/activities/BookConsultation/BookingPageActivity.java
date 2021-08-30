package iss.workshops.telemedicinemobile.activities.BookConsultation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import iss.workshops.telemedicinemobile.MainActivity;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.activities.DashBoardActivity;
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
    Button homeBtn;

    Patient inPatient;
    Doctor doctor;

    TextView mCalender;
    LocalDate xDate;
    String date;
    String newDate;
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
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(year,month-1,day);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                newDate = sdf.format(myCalendar.getTime());
                            }
                        },year,month,day);
                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
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
                homeBtn = findViewById(R.id.homeBtn);
                homeBtn.setOnClickListener(this);

            }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.book)
            validateAppointment();
            //setUpAppointment();
        else if(id == R.id.homeBtn){
            finish();
        }

    }


    protected void setUpAppointment(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
            //LocalDate someDate = LocalDate.parse(xDate,formatter);
            TimeSlots selected = (TimeSlots)mTimeslots.getSelectedItem();
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

    private void validateAppointment() {
        if(date== null) {
            Toast.makeText(this, "Input a date", Toast.LENGTH_SHORT).show();
            return;
        }
        TimeSlots selected2 = (TimeSlots)mTimeslots.getSelectedItem();
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(selected2);
        appointment.setDoctor(doctor);

        Call<Void> call = RetrofitClient
                .getInstance()
                .getAPI()
                .validateAppointment(appointment,newDate);
        validateThem(call);
    }
    private void validateThem(Call<Void> call){

        call.enqueue(new Callback<Void>() {
            //issue is timeslots and doctor object
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful())
                    Toast.makeText(BookingPageActivity.this, "Duplicated Value ", Toast.LENGTH_SHORT).show();
                else if (response.isSuccessful()) {
                    Toast.makeText(BookingPageActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                    setUpAppointment();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postAppointment(Call<Appointment> call) {

        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(!response.isSuccessful())
                    Toast.makeText(BookingPageActivity.this, "Unsuccessful "+response.code(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(BookingPageActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                t.printStackTrace();
            }
        });


    } }