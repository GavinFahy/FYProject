package com.example.fyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import java.util.Scanner;

public class Blood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_activity);
//
//        // Create a scanner to read input from the user
//        Scanner scanner = new Scanner(System.in);
//
//        // Prompt the user to enter their age
//        Toast.makeText(this, "Enter your age:", Toast.LENGTH_SHORT).show();
//        int age = scanner.nextInt();
//
//        // Prompt the user to enter their gender
//        Toast.makeText(this, "Enter your gender (M or F):", Toast.LENGTH_SHORT).show();
//        String gender = scanner.next();
//
//        // Prompt the user to enter their blood pressure
//        Toast.makeText(this, "Enter your blood pressure (systolic/diastolic):", Toast.LENGTH_SHORT).show();
//        int systolic = scanner.nextInt();
//        int diastolic = scanner.nextInt();
//
//        // Calculate the average blood pressure for the user's age and gender
//        int averageSystolic;
//        int averageDiastolic;
//        if (gender.equals("M")) {
//            // Average blood pressure for men by age group
//            if (age < 20) {
//                averageSystolic = 116;
//                averageDiastolic = 76;
//            } else if (age < 30) {
//                averageSystolic = 121;
//                averageDiastolic = 79;
//            } else if (age < 40) {
//                averageSystolic = 125;
//                averageDiastolic = 81;
//            } else if (age < 50) {
//                averageSystolic = 129;
//                averageDiastolic = 83;
//            } else if (age < 60) {
//                averageSystolic = 134;
//                averageDiastolic = 85;
//            } else {
//                averageSystolic = 138;
//                averageDiastolic = 87;
//            }
//        } else {
//            // Average blood pressure for women by age group
//            if (age < 20) {
//                averageSystolic = 110;
//                averageDiastolic = 70;
//            } else if (age < 30) {
//                averageSystolic = 114;
//                averageDiastolic = 72;
//            } else if (age < 40) {
//                averageSystolic = 119;
//                averageDiastolic = 75;
//            } else if (age < 50) {
//                averageSystolic = 124;
//                averageDiastolic = 77;
//            } else if (age < 60) {
//                averageSystolic = 130;
//                averageDiastolic = 80;
//            } else {
//                averageSystolic = 135;
//                averageDiastolic = 83;
//            }
//        }
//
//        // Compare the user's blood pressure to the average for their age and gender
//        if (systolic > averageSystolic || diastolic > averageDiastolic) {
//            Toast.makeText(this, "Your blood pressure is high for your age and gender.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Your blood pressure is within the normal range for your age and gender.", Toast.LENGTH_SHORT).show();
//        }
    }
}