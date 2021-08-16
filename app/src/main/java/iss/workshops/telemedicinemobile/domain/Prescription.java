package iss.workshops.telemedicinemobile.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prescription {
    @SerializedName("id")
    @Expose
    private int prescriptionId;

    @Expose
    private String medicine;

    @Expose
    private String remarks;


    public int getPrescriptionId() {
        return prescriptionId;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getRemarks() {
        return remarks;
    }

    public Prescription(int prescriptionId, String medicine, String remarks) {
        this.prescriptionId = prescriptionId;
        this.medicine = medicine;
        this.remarks = remarks;
    }

}

