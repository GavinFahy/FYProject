package com.example.fyproject;

public class HPHandler {
    public String breathingText, sightText, hearingText, heartText, disabilityText, wheelchairText, otherText;

    public HPHandler(){

    }

    public HPHandler(String breathingText, String sightText, String hearingText, String heartText, String disabilityText, String wheelchairText, String otherText){
        this.breathingText = breathingText;
        this.sightText = sightText;
        this.hearingText = hearingText;
        this.heartText = heartText;
        this.disabilityText = disabilityText;
        this.wheelchairText = wheelchairText;
        this.otherText = otherText;
    }
}
