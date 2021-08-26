package iss.workshops.telemedicinemobile.domain;
import com.google.gson.annotations.Expose;

public class Clinic {

    @Expose
    private String id;

    @Expose
    private String address;

    @Expose
    private String clinicName;

    @Expose
    private String phoneNo;

    @Expose
    private String zone;

    @Expose
    private String openingHours;

    @Expose
    private double lat;

    @Expose
    private double lng;

    public Clinic(String id, String address, String clinicName, String phoneNo, String zone, String openingHours, double lat, double lng) {
        this.id = id;
        this.address = address;
        this.clinicName = clinicName;
        this.phoneNo = phoneNo;
        this.zone = zone;
        this.openingHours = openingHours;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getZone() {
        return zone;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public double getLat() { return lat; }

    public double getLng() { return lng; }
}