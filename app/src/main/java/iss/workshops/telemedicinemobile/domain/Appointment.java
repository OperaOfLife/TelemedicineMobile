package iss.workshops.telemedicinemobile.domain;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.util.Date;

public class Appointment {


    @Expose
    private int id;

    @Expose
    private Date appointmentDate;

    @Expose
    private String appointmentTime;

    @Expose
    private Doctor doctor;

    @Expose
    private Patient patient;

    @Expose
    private Prescription prescription;

    @Expose
    private MedicalCertificate mc;

    public Appointment(int id, Date appointmentDate, String appointmentTime, Doctor doctor, Patient patient, Prescription prescription, MedicalCertificate mc) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctor = doctor;
        this.patient = patient;
        this.prescription = prescription;
        this.mc = mc;
    }

    public Appointment() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public void setMc(MedicalCertificate mc) {
        this.mc = mc;
    }

    public int getId() {
        return id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public MedicalCertificate getMc() {
        return mc;
    }
}