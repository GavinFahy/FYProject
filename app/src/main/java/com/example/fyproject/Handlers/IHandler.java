package com.example.fyproject.Handlers;

public class IHandler {
    public String EnterVomit, EnterDiarrhoea, EnterHospital, IsolationReason, MRSADate, MRGNBDate, CREDate, VREDate, ImmunocompromisedReason;

    public IHandler(){

    }

    public IHandler(String EnterVomit, String EnterDiarrhoea, String EnterHospital, String IsolationReason, String MRSADate, String MRGNBDate, String CREDate, String VREDate, String ImmunocompromisedReason){
        this.EnterVomit = EnterVomit;
        this.EnterDiarrhoea = EnterDiarrhoea;
        this.EnterHospital = EnterHospital;
        this.IsolationReason = IsolationReason;
        this.MRSADate = MRSADate;
        this.MRGNBDate = MRGNBDate;
        this.CREDate = CREDate;
        this.VREDate = VREDate;
        this.ImmunocompromisedReason = ImmunocompromisedReason;
    }
}
