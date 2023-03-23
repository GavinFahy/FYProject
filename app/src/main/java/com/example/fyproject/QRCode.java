package com.example.fyproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QRCode extends AppCompatActivity {

    ImageView QRImage;
    EditText QRname;
    Button idBtnGenerateQR;

    EditText idEdtPassword;

    Button testButton;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        QRname = findViewById(R.id.QRname);
        QRImage = findViewById(R.id.QRImage);
        idBtnGenerateQR = findViewById(R.id.idBtnGenerateQR);
        idEdtPassword = findViewById(R.id.idEdtPassword);

        idBtnGenerateQR.setOnClickListener(v ->{
            String password = idEdtPassword.getText().toString().trim();
            checkPassword(password);
        });

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(V -> scanCode());
    }

    private void checkPassword(String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String correctPassword = snapshot.child("password").getValue(String.class);
                if (password.equals(correctPassword)) {
                    generateQR();
                } else {
                    Toast.makeText(QRCode.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QRCode.this, "Error retrieving user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //generates the QR code
    private void generateQR() {
        //retrieves teh text that is entered into teh
        String text = QRname.getText().toString().trim();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            //Here the text string is being encoded as a QR code
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400,400);
            //converts teh bitmatrix into a bitmap image
            BarcodeEncoder encoder = new BarcodeEncoder();
            //returns a bitmap object that represents the QR code image
            Bitmap bitmap = encoder.createBitmap(matrix);
            //setting the bitmap image as the QRImage so it will be displayed
            QRImage.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(QRCode.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            //when the button is clicked an alert dialog is presented to the screen asking if they would like to compose an email.
            builder.setPositiveButton("Compose Email", new DialogInterface.OnClickListener() {
                //once the button is clicked it opens an intent that redirects the user to their email app of choice
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    //this line uses an intent to open an email application on the users mobile device
                    intent.setData(Uri.parse("mailto:"));
                    //this line of code sets the result value which is the name of the email to the contents of the to section of the email
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{result.getContents()});
                    //this sets the subject of teh email to QR code scan
                    intent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scan");

                    //this code references firebase for where the personalDetails reside on the current users id.
                    DatabaseReference pdRef = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
                    pdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //teh below code is used to reference each element that resides inside the personal details table and then,
                            //prints them into the body of the email.
                            StringBuilder emailbody = new StringBuilder();
                            emailbody.append("Name: ").append(snapshot.child("Name").getValue(String.class)).append("\n");
                            emailbody.append("Age: ").append(snapshot.child("Age").getValue(String.class)).append("\n");
                            emailbody.append("Gender: ").append(snapshot.child("Gender").getValue(String.class)).append("\n");
                            emailbody.append("Weight: ").append(snapshot.child("Weight").getValue(String.class)).append("\n");
                            emailbody.append("Phone Number: ").append(snapshot.child("PhoneNumber").getValue(String.class)).append("\n");
                            emailbody.append("Consultant: ").append(snapshot.child("Consultant").getValue(String.class)).append("\n");
                            emailbody.append("Next of Kin: ").append(snapshot.child("Next_of_kin").getValue(String.class)).append("\n");
                            emailbody.append("Number: ").append(snapshot.child("Number").getValue(String.class)).append("\n");

                            DatabaseReference hpRef = FirebaseDatabase.getInstance().getReference("HPHandler").child(currentUserId).child("HealthProblems");
                            hpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    emailbody.append("Health Problems: \n");
                                    emailbody.append("Breathing: ").append(snapshot.child("breathingText").getValue(String.class)).append("\n");
                                    emailbody.append("Sight: ").append(snapshot.child("sightText").getValue(String.class)).append("\n");
                                    emailbody.append("Hearing: ").append(snapshot.child("hearingText").getValue(String.class)).append("\n");
                                    emailbody.append("Heart: ").append(snapshot.child("heartText").getValue(String.class)).append("\n");
                                    emailbody.append("Disability: ").append(snapshot.child("disabilityText").getValue(String.class)).append("\n");
                                    emailbody.append("Wheelchair: ").append(snapshot.child("wheelchairText").getValue(String.class)).append("\n");
                                    emailbody.append("Other: ").append(snapshot.child("otherText").getValue(String.class)).append("\n");
                                    emailbody.append("\n");


                                    DatabaseReference ciRef = FirebaseDatabase.getInstance().getReference("CIHandler").child(currentUserId).child("history");
                                    ciRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            emailbody.append("Current: ").append(snapshot.child("Current").getValue(String.class)).append("\n");
                                            emailbody.append("History: ").append(snapshot.child("History").getValue(String.class)).append("\n");

                                            emailbody.append("\n\nQR Code contents: ").append(result.getContents());

                                            intent.putExtra(Intent.EXTRA_TEXT, emailbody.toString());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(QRCode.this, "Error retrieving Medical history", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(QRCode.this, "Error retrieving health problems", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(QRCode.this, "Error retrieving personal details", Toast.LENGTH_SHORT).show();
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