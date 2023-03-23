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

    ImageView idIVQrcode;
    EditText QRname;
    Button idBtnGenerateQR;

    EditText idEdtPassword;

//    Button testButton;

//    private DatabaseReference reference;

//String correctPassword = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        QRname = findViewById(R.id.QRname);
        idIVQrcode = findViewById(R.id.idIVQrcode);
        idBtnGenerateQR = findViewById(R.id.idBtnGenerateQR);
        idEdtPassword = findViewById(R.id.idEdtPassword);

        idBtnGenerateQR.setOnClickListener(v ->{
            String password = idEdtPassword.getText().toString().trim();
            checkPassword(password);
        });

//        testButton = findViewById(R.id.testButton);
//        testButton.setOnClickListener(V->{
//            scanCode();
//        });
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
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400,400);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            idIVQrcode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

    }

//    private void generateQR() {
//        final String[] text = {idEdt.getText().toString().trim()};
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String userEmail = snapshot.child("email").getValue(String.class);
//                text[0] += "&email=" + userEmail; // Add user's email to the QR code text
//                MultiFormatWriter writer = new MultiFormatWriter();
//                try {
//                    BitMatrix matrix = writer.encode(text[0], BarcodeFormat.QR_CODE, 400,400);
//
//                    BarcodeEncoder encoder = new BarcodeEncoder();
//                    Bitmap bitmap = encoder.createBitmap(matrix);
//                    idIVQrcode.setImageBitmap(bitmap);
//                }catch (WriterException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(QRCode.this, "Error retrieving user details", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


//    private void scanCode() {
//        ScanOptions options = new ScanOptions();
//        options.setPrompt("Volume up to flash on");
//        options.setBeepEnabled(true);
//        options.setOrientationLocked(true);
//        options.setCaptureActivity(CaptureAct.class);
//        barLauncher.launch(options);
//    }
//
////    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
////        if(result.getContents() != null)
////        {
////            AlertDialog.Builder builder = new AlertDialog.Builder(QRCode.this);
////            builder.setTitle("Result");
////            builder.setMessage(result.getContents());
////            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialogInterface, int i) {
////                    dialogInterface.dismiss();
////                }
////            }).show();
////        }
////        if(result.getContents() != null)
////        {
////            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
////                    "mailto", "", null));
////            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scan Result");
////            emailIntent.putExtra(Intent.EXTRA_TEXT, result.getContents());
////            emailIntent.setPackage("com.google.android.gm");
////            startActivity(Intent.createChooser(emailIntent, "Send email..."));
////        }
////    });
////    });
////
////    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
////        if(result.getContents() != null)
////        {
////            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
////            DatabaseReference personalDetailsRef = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
////            personalDetailsRef.addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    String name = snapshot.child("Name").getValue(String.class);
////                    String age = snapshot.child("Age").getValue(String.class);
////                    String gender = snapshot.child("Gender").getValue(String.class);
////                    String weight = snapshot.child("Weight").getValue(String.class);
////                    String phoneNumber = snapshot.child("PhoneNumber").getValue(String.class);
////                    String consultant = snapshot.child("Consultant").getValue(String.class);
////                    String nextOfKin = snapshot.child("Next_of_kin").getValue(String.class);
////                    String nextOfKinNumber = snapshot.child("Number").getValue(String.class);
////
////                    String emailBody = "Name: " + name + "\n" +
////                            "Age: " + age + "\n" +
////                            "Gender: " + gender + "\n" +
////                            "Weight: " + weight + "\n" +
////                            "Phone Number: " + phoneNumber + "\n" +
////                            "Consultant: " + consultant + "\n" +
////                            "Next of Kin: " + nextOfKin + "\n" +
////                            "Next of Kin Number: " + nextOfKinNumber;
////
////                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
////                            "mailto", "", null));
////                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scan Result");
////                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
////                    emailIntent.setPackage("com.google.android.gm");
////                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////                    Toast.makeText(QRCode.this, "Error retrieving user details", Toast.LENGTH_SHORT).show();
////                }
////            });
////        }
////    });
//
//
//
//        ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
//        if(result.getContents() != null)
//        {
//
//            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            reference = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String Name = snapshot.child("Name").getValue(String.class);
////                    String Age = snapshot.child("Age").getValue(String.class);
////                    String Gender = snapshot.child("Gender").getValue(String.class);
////                    String Weight = snapshot.child("Weight").getValue(String.class);
////                    String PhoneNumber = snapshot.child("PhoneNumber").getValue(String.class);
////                    String Consultant = snapshot.child("Consultant").getValue(String.class);
////                    String Next_of_kin = snapshot.child("Next_of_kin").getValue(String.class);
////                    String Number = snapshot.child("Number").getValue(String.class);
//
////                    String emailBody = "Name: " + Name + "\n" +
////                            "Age: " + Age + "\n" +
////                            "Gender: " + Gender + "\n" +
////                            "Weight: " + Weight + "\n" +
////                            "Phone Number: " + PhoneNumber + "\n" +
////                            "Consultant: " + Consultant + "\n" +
////                            "Next of Kin: " + Next_of_kin + "\n" +
////                            "Next of Kin Number: " + Number;
//
//                    String emailBody = "Name: " + Name;
//
//                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                            "mailto", "", null));
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scan Result");
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
//                    emailIntent.setPackage("com.google.android.gm");
//                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(QRCode.this, "Error retrieving user details", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    });
}