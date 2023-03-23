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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care);

        ageEditText = findViewById(R.id.ageEditText);
        heartRateEditText = findViewById(R.id.heartRateEditText);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        resultTextView = findViewById(R.id.resultTextView);
    }

    public void calculateHeartRate(View view) {
        int age = Integer.parseInt(ageEditText.getText().toString());
        int currentHeartRate = Integer.parseInt(heartRateEditText.getText().toString());
        String gender;
        if (maleRadioButton.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        // Calculate standard heart rate based on age and gender
        int standardHeartRate;
        if (gender.equals("Male")) {
            standardHeartRate = 220 - age;
        } else {
            standardHeartRate = 210 - age;
        }

        // Compare current heart rate to standard heart rate
        if (currentHeartRate > standardHeartRate) {
            resultTextView.setText("Your heart rate is above the standard for someone of your age and gender.");
        } else if (currentHeartRate < standardHeartRate) {
            resultTextView.setText("Your heart rate is below the standard for someone of your age and gender.");
        } else {
            resultTextView.setText("Your heart rate is at the standard for someone of your age and gender.");
        }
    }
}