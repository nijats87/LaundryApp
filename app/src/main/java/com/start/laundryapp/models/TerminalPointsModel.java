package com.start.laundryapp.models;

public class TerminalPointsModel extends OrderTypeModel{

    private int longitude;
    private int latitude;

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;

    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
