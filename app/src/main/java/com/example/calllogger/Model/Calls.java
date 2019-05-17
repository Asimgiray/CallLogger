package com.example.calllogger.Model;



public class Calls {


    private String phNumber;
    private String callType;
    private String callDate;
    private String callDuration;
    private String location;


    public Calls(String phNumber, String callType, String callDate, String callDuration, String location) {
        this.phNumber = phNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.location = location;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public String getCallType() {
        return callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public String getLocation() {
        return location;
    }



}
