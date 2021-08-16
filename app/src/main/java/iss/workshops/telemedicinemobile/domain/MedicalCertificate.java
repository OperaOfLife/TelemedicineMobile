package iss.workshops.telemedicinemobile.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MedicalCertificate {
    @SerializedName("id")
    @Expose
    private String mcId;

    @Expose
    private Date dateFrom;

    @Expose
    private Date dateTo;

    @Expose
    private int duration;

    public String getMcId() {
        return mcId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public int getDuration() {
        return duration;
    }

    public MedicalCertificate(String mcId, Date dateFrom, Date dateTo, int duration) {
        this.mcId = mcId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
    }

}

