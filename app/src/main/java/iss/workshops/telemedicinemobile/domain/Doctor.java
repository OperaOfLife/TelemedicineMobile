package iss.workshops.telemedicinemobile.domain;

public class Doctor {
    private String doctorId;
    private String firstName;
    private String lastName;
    private String gender;
    private String mobile;
    private String email;
    private String speciality;
    private String description;
    private String hospital;

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorId() {
        return doctorId;
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

    public String getSpeciality() {
        return speciality;
    }

    public String getDescription() {
        return description;
    }
    public String getHospital() {
        return hospital;
    }
    public Doctor(String firstName, String lastName, String gender, String speciality, String description,String hospital) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.speciality = speciality;
        this.description = description;
        this.hospital=hospital;
    }
}