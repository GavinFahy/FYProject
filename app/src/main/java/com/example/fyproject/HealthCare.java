package com.example.fyproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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
    }

    public void calculateHeartRate(View view) {
        if (maleRadioButton.isChecked()) {
            genderMale();
        } else {
            genderFemale();
        }
    }

    private void genderFemale() {
        int age = Integer.parseInt(ageEditText.getText().toString());
        int currentHeartRate = Integer.parseInt(heartRateEditText.getText().toString());
        if(age >=18 && age <= 25){
            if(currentHeartRate < 60){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 63){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=26 && age <= 35){
            if(currentHeartRate < 60){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 63){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=36 && age <= 45){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=46 && age <= 55){
            if(currentHeartRate < 64){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 67){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=56 && age <= 65){
            if(currentHeartRate < 65){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 68){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

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

    private void genderMale() {
        int age = Integer.parseInt(ageEditText.getText().toString());
        int currentHeartRate = Integer.parseInt(heartRateEditText.getText().toString());
        if(age >=18 && age <= 25){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=26 && age <= 35){
            if(currentHeartRate < 62){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 65){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=36 && age <= 45){
            if(currentHeartRate < 64){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 67){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=46 && age <= 55){
            if(currentHeartRate < 66){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 69){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

        if(age >=56 && age <= 65){
            if(currentHeartRate < 67){
                resultTextView.setText("Youre heart rate is below average");
            }else if(currentHeartRate > 70){
                resultTextView.setText("Youre heart rate is above average");
            }else{
                resultTextView.setText("Youre heart rate is normal");
            }
        }

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

    public void calculateBMI(View view){
        double weight = Double.parseDouble(Weight.getText().toString());
        double height = Double.parseDouble(Height.getText().toString());

        double BMI = (100*100*weight)/(height*height);

        BMIText.setText("You're BMI is: " + BMI);

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
}