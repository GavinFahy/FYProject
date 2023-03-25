package com.example.fyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    private EditText Agefield;
    private EditText Systolic;
    private EditText Diastolic;
    private RadioButton bloodMen;
    private RadioButton bloodFemale;
    private TextView bloodresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Agefield = findViewById(R.id.Agefield);
        Systolic = findViewById(R.id.Systolic);
        Diastolic = findViewById(R.id.Diastolic);
        bloodMen = findViewById(R.id.bloodMen);
        bloodFemale = findViewById(R.id.bloodFemale);
        bloodresult = findViewById(R.id.bloodresult);

    }

    public void calculateBloodButton(View view) {
        if (bloodMen.isChecked()) {
            genderMaleBlood();
        } else {
            genderFemaleBlood();
        }
    }

    private void genderFemaleBlood() {
        int age = Integer.parseInt(Agefield.getText().toString());
        int systolic = Integer.parseInt(Systolic.getText().toString());
        int diastolic = Integer.parseInt(Diastolic.getText().toString());

        int averageSystolic = 0;
        int averageDiastolic = 0;
        if(age >= 20 && age <= 25){
            averageSystolic = 115;
            averageDiastolic = 70;
        }else if(age >= 26 && age <= 30){
            averageSystolic = 113;
            averageDiastolic = 71;
        }else if(age >= 31 && age <= 35){
            averageSystolic = 110;
            averageDiastolic = 72;
        }else if(age >= 36 && age <= 40){
            averageSystolic = 112;
            averageDiastolic = 74;
        }else if(age >= 41 && age <= 45){
            averageSystolic = 116;
            averageDiastolic = 73;
        }else if(age >= 46 && age <= 50){
            averageSystolic = 124;
            averageDiastolic = 78;
        }else if(age >= 51 && age <= 55){
            averageSystolic = 122;
            averageDiastolic = 74;
        }else if(age >= 56 && age <= 60){
            averageSystolic = 132;
            averageDiastolic = 78;
        }else if(age >= 60){
            averageSystolic = 130;
            averageDiastolic = 77;
        }

        if(systolic > averageSystolic || diastolic > averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else if(systolic < averageSystolic || diastolic < averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else{
            bloodresult.setText("You're blood pressure is normal");
        }

    }

    private void genderMaleBlood() {
        int age = Integer.parseInt(Agefield.getText().toString());
        int systolic = Integer.parseInt(Systolic.getText().toString());
        int diastolic = Integer.parseInt(Diastolic.getText().toString());

        int averageSystolic = 0;
        int averageDiastolic = 0;
        if(age >= 20 && age <= 25){
            averageSystolic = 120;
            averageDiastolic = 78;
        }else if(age >= 26 && age <= 30){
            averageSystolic = 119;
            averageDiastolic = 76;
        }else if(age >= 31 && age <= 35){
            averageSystolic = 114;
            averageDiastolic = 75;
        }else if(age >= 36 && age <= 40){
            averageSystolic = 120;
            averageDiastolic = 75;
        }else if(age >= 41 && age <= 45){
            averageSystolic = 115;
            averageDiastolic = 78;
        }else if(age >= 46 && age <= 50){
            averageSystolic = 119;
            averageDiastolic = 80;
        }else if(age >= 51 && age <= 55){
            averageSystolic = 125;
            averageDiastolic = 80;
        }else if(age >= 56 && age <= 60){
            averageSystolic = 129;
            averageDiastolic = 79;
        }else if(age >= 60){
            averageSystolic = 143;
            averageDiastolic = 76;
        }

        if(systolic > averageSystolic || diastolic > averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else if(systolic < averageSystolic || diastolic < averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else{
            bloodresult.setText("You're blood pressure is normal");
        }
    }
}