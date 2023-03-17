package com.example.fyproject;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Testing extends AppCompatActivity {

    Button testButton;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(V -> scanCode());
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Testing.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Compose Email", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{result.getContents()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scan");

                    DatabaseReference pdRef = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
                    pdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            StringBuilder bodyBuilder = new StringBuilder();
                            bodyBuilder.append("Name: ").append(snapshot.child("Name").getValue(String.class)).append("\n");
                            bodyBuilder.append("Age: ").append(snapshot.child("Age").getValue(String.class)).append("\n");
                            bodyBuilder.append("Gender: ").append(snapshot.child("Gender").getValue(String.class)).append("\n");
                            bodyBuilder.append("Weight: ").append(snapshot.child("Weight").getValue(String.class)).append("\n");
                            bodyBuilder.append("Phone Number: ").append(snapshot.child("PhoneNumber").getValue(String.class)).append("\n");
                            bodyBuilder.append("Consultant: ").append(snapshot.child("Consultant").getValue(String.class)).append("\n");
                            bodyBuilder.append("Next of Kin: ").append(snapshot.child("Next_of_kin").getValue(String.class)).append("\n");
                            bodyBuilder.append("Number: ").append(snapshot.child("Number").getValue(String.class)).append("\n");

                            DatabaseReference hpRef = FirebaseDatabase.getInstance().getReference("HPHandler").child(currentUserId).child("HealthProblems");
                            hpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    bodyBuilder.append("Health Problems: \n");
                                    bodyBuilder.append("Breathing: ").append(snapshot.child("breathingText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Sight: ").append(snapshot.child("sightText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Hearing: ").append(snapshot.child("hearingText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Heart: ").append(snapshot.child("heartText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Disability: ").append(snapshot.child("disabilityText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Wheelchair: ").append(snapshot.child("wheelchairText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("Other: ").append(snapshot.child("otherText").getValue(String.class)).append("\n");
                                    bodyBuilder.append("\n");


                                    DatabaseReference ciRef = FirebaseDatabase.getInstance().getReference("CIHandler").child(currentUserId).child("history");
                                    ciRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            bodyBuilder.append("Current: ").append(snapshot.child("Current").getValue(String.class)).append("\n");
                                            bodyBuilder.append("History: ").append(snapshot.child("History").getValue(String.class)).append("\n");

                                            bodyBuilder.append("\n\nQR Code contents: ").append(result.getContents());

                                            intent.putExtra(Intent.EXTRA_TEXT, bodyBuilder.toString());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(Testing.this, "Error retrieving Medical history", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Testing.this, "Error retrieving health problems", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Testing.this, "Error retrieving personal details", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

}