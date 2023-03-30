package com.example.fyproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.DataAccess.PD_DataAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PersonalDetails extends AppCompatActivity {

    private PD_DataAccess pdDataAccess;
    private DatabaseReference reference;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);

        pdDataAccess = new PD_DataAccess();
        //retrieving the current users ID from firebase
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //assigning edittext views to the variables inside.
        final EditText EnterName = findViewById(R.id.Name);
        final EditText EnterAge = findViewById(R.id.Age);
        final EditText EnterGender = findViewById(R.id.Gender);
        final EditText EnterWeight = findViewById(R.id.Weight);
        final EditText EnterContactNumber = findViewById(R.id.PhoneNumber);
        final EditText EnterConsultant = findViewById(R.id.Consultant);
        final EditText EnterNext_of_kin = findViewById(R.id.Next_of_kin);
        final EditText EnterNextOfKinNumber = findViewById(R.id.Number);
        Button Update = findViewById(R.id.update);

        Update.setOnClickListener(V -> {
            //updates each of the fields for each element once a new text is entered in
            String name = EnterName.getText().toString();
            String age = EnterAge.getText().toString();
            String gender = EnterGender.getText().toString();
            String weight = EnterWeight.getText().toString();
            String phoneNumber = EnterContactNumber.getText().toString();
            String consultant = EnterConsultant.getText().toString();
            String nok = EnterNext_of_kin.getText().toString();
            String number = EnterNextOfKinNumber.getText().toString();

            //making sure none of the fields are empty
            if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || weight.isEmpty()
                    || phoneNumber.isEmpty() || consultant.isEmpty() || nok.isEmpty()
                    || number.isEmpty()) {
                Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                return;
            }

            //check to see if the phone number is the correct amount of digits in length
            if (phoneNumber.length() != 10) {
                Toast.makeText(this, "Phone number must be 10 digits long", Toast.LENGTH_SHORT).show();
                return;
            }

            //creating a hashmap that will store the details for each of the new entry's.
            HashMap<String, Object> data = new HashMap<>();
            data.put("Name", EnterName.getText().toString());
            data.put("Age", EnterAge.getText().toString());
            data.put("Gender", EnterGender.getText().toString());
            data.put("Weight", EnterWeight.getText().toString());
            data.put("PhoneNumber", EnterContactNumber.getText().toString());
            data.put("Consultant", EnterConsultant.getText().toString());
            data.put("Next_of_kin", EnterNext_of_kin.getText().toString());
            data.put("Number", EnterNextOfKinNumber.getText().toString());

            //this code passes in the current users ID which is then being used to reference the correct
            //section of the firebase db inside the personalDetails table for which users details to be updated.
            pdDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        //here the firebase is referenced to gain access to the personal details elements
        reference = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("Name").getValue(String.class);
                    String age = snapshot.child("Age").getValue(String.class);

                    String gender = snapshot.child("Gender").getValue(String.class);
                    String weight = snapshot.child("Weight").getValue(String.class);
                    String phoneNumber = snapshot.child("PhoneNumber").getValue(String.class);
                    String consultant = snapshot.child("Consultant").getValue(String.class);
                    String next_of_kin = snapshot.child("Next_of_kin").getValue(String.class);
                    String number = snapshot.child("Number").getValue(String.class);

                    EnterName.setText((name));
                    EnterAge.setText(age);
                    EnterGender.setText(gender);
                    EnterWeight.setText(weight);
                    EnterContactNumber.setText(phoneNumber);
                    EnterConsultant.setText(consultant);
                    EnterNext_of_kin.setText(next_of_kin);
                    EnterNextOfKinNumber.setText(number);
                }
            }

            //If something goes wrong the user is notified
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PersonalDetails.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

    }
}
