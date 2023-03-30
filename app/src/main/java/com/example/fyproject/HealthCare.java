package com.example.fyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class HealthCare extends AppCompatActivity {

    private EditText ageEditText;
    private EditText heartRateEditText;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private TextView resultTextView;

    private EditText Weight;
    private EditText Height;
    private TextView resultBMI;
    private TextView BMIText;

    private EditText Agefield;
    private EditText Systolic;
    private EditText Diastolic;
    private RadioButton bloodMen;
    private RadioButton bloodFemale;
    private TextView bloodresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care);

        ageEditText = findViewById(R.id.ageEditText);
        heartRateEditText = findViewById(R.id.heartRateEditText);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        resultTextView = findViewById(R.id.resultTextView);

        Weight = findViewById(R.id.Weight);
        Height = findViewById(R.id.Height);
        resultBMI = findViewById(R.id.resultBMI);
        BMIText = findViewById(R.id.BMIText);

        Agefield = findViewById(R.id.Agefield);
        Systolic = findViewById(R.id.Systolic);
        Diastolic = findViewById(R.id.Diastolic);
        bloodMen = findViewById(R.id.bloodMen);
        bloodFemale = findViewById(R.id.bloodFemale);
        bloodresult = findViewById(R.id.bloodresult);
    }


    public void calculateHeartRate(View view) {
        //checks if the radio button selected is male or female after the calculateHeartRate button is selected
        if (maleRadioButton.isChecked()) {
            genderMale();
        } else {
            genderFemale();
        }
    }

    //if the user selected female
    private void genderFemale() {
        int age = Integer.parseInt(ageEditText.getText().toString());
        int currentHeartRate = Integer.parseInt(heartRateEditText.getText().toString());
        //if age is between 18 and 25 then using your current heartrate checks if it is above/below average or normal
        if(age >=18 && age <= 25){
            if(currentHeartRate < 60){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 63){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 26 and 35 then using your current heartrate checks if it is above/below average or normal
        if(age >=26 && age <= 35){
            if(currentHeartRate < 60){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 63){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 36 and 45 then using your current heartrate checks if it is above/below average or normal
        if(age >=36 && age <= 45){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 46 and 55 then using your current heartrate checks if it is above/below average or normal
        if(age >=46 && age <= 55){
            if(currentHeartRate < 64){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 67){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 56 and 65 then using your current heartrate checks if it is above/below average or normal
        if(age >=56 && age <= 65){
            if(currentHeartRate < 65){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 68){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is above 66 then using your current heartrate checks if it is above/below average or normal
        if(age >=66){
            if(currentHeartRate < 66){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 69){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }
    }

    //if the user selected male
    private void genderMale() {
        int age = Integer.parseInt(ageEditText.getText().toString());
        int currentHeartRate = Integer.parseInt(heartRateEditText.getText().toString());
        //if age is between 18 and 25 then using your current heartrate checks if it is above/below average or normal
        if(age >=18 && age <= 25){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 26 and 35 then using your current heartrate checks if it is above/below average or normal
        if(age >=26 && age <= 35){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 36 and 45 then using your current heartrate checks if it is above/below average or normal
        if(age >=36 && age <= 45){
            if(currentHeartRate < 64){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 67){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 46 and 55 then using your current heartrate checks if it is above/below average or normal
        if(age >=46 && age <= 55){
            if(currentHeartRate < 66){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 69){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is between 56 and 65 then using your current heartrate checks if it is above/below average or normal
        if(age >=56 && age <= 65){
            if(currentHeartRate < 67){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 70){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        //if age is above 66 then using your current heartrate checks if it is above/below average or normal
        if(age >=66){
            if(currentHeartRate < 68){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 71){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }
    }

    //after the user selectes the BMI button
    public void calculateBMI(View view){
        double weight = Double.parseDouble(Weight.getText().toString());
        double height = Double.parseDouble(Height.getText().toString());

        double BMI = (100*100*weight)/(height*height);

        BMIText.setText("You're BMI is: " + BMI);

        //checks if you are underweight, of normal weight, overweight or obese
        if(BMI < 18.5){
            resultBMI.setText("Underweight");
        }else if(BMI < 25){
            resultBMI.setText("Normal weight");
        }else if(BMI < 30){
            resultBMI.setText("Overweight");
        }else{
            resultBMI.setText("Obese");
        }
    }

    //checks to see if the useer selected the male or female radio button
    public void calculateBloodButton(View view) {
        if (bloodMen.isChecked()) {
            genderMaleBlood();
        } else {
            genderFemaleBlood();
        }
    }

    //if female selected
    private void genderFemaleBlood() {
        int age = Integer.parseInt(Agefield.getText().toString());
        int systolic = Integer.parseInt(Systolic.getText().toString());
        int diastolic = Integer.parseInt(Diastolic.getText().toString());

        //sets the average variables to 0
        int averageSystolic = 0;
        int averageDiastolic = 0;
        //checks in what age range the user selected then sets the average Systolic and Diastolic to the average for that age
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

        //checks and compares inputted systolic and diastolic  to the average and the tells the user if their blood pressure is above or below average
        if(systolic > averageSystolic || diastolic > averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else if(systolic < averageSystolic || diastolic < averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else{
            bloodresult.setText("You're blood pressure is normal");
        }

    }

    //if selected male
    private void genderMaleBlood() {
        int age = Integer.parseInt(Agefield.getText().toString());
        int systolic = Integer.parseInt(Systolic.getText().toString());
        int diastolic = Integer.parseInt(Diastolic.getText().toString());

        //sets the average variables to 0
        int averageSystolic = 0;
        int averageDiastolic = 0;
        //checks in what age range the user selected then sets the average Systolic and Diastolic to the average for that age
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

        //checks and compares inputted systolic and diastolic  to the average and the tells the user if their blood pressure is above or below average
        if(systolic > averageSystolic || diastolic > averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else if(systolic < averageSystolic || diastolic < averageDiastolic){
            bloodresult.setText("You're blood pressure is below average");
        }else{
            bloodresult.setText("You're blood pressure is normal");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem detailsButton = menu.findItem(R.id.action_personal_details);
        detailsButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(HealthCare.this, PersonalDetails.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }
}