package iss.workshops.telemedicinemobile.domain;

import com.google.gson.annotations.Expose;

public class Patient {
    @Expose
    private String patientId;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String gender;

    @Expose
    private String mobile;

    @Expose
    private String email;

    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }


    public Patient(String patientId, String firstName, String lastName, String gender, String mobile, String email) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
    }
}

