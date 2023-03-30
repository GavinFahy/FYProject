package com.example.fyproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    Button testButton;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        QRname = findViewById(R.id.QRname);
        QRImage = findViewById(R.id.QRImage);
        idBtnGenerateQR = findViewById(R.id.idBtnGenerateQR);

        idBtnGenerateQR.setOnClickListener(v ->{
            String email = QRname.getText().toString().trim();
            checkEmail(email);
        });

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(V -> scanCode());
    }

    private void checkEmail(String email) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String correctEmail = snapshot.child("email").getValue(String.class);
                if (email.equals(correctEmail)) {
                    generateQR();
                } else {
                    Toast.makeText(QRCode.this, "Wrong Email", Toast.LENGTH_SHORT).show();
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
            //converts the bitmatrix into a bitmap image
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
                            //the below code is used to reference each element that resides inside the personal details table and then,
                            //prints them into the body of the email.
                            StringBuilder emailbody = new StringBuilder();
                            emailbody.append("\nPersonal Details: \n");
                            emailbody.append("Name: ").append(snapshot.child("Name").getValue(String.class)).append("\n");
                            emailbody.append("Age: ").append(snapshot.child("Age").getValue(String.class)).append("\n");
                            emailbody.append("Gender: ").append(snapshot.child("Gender").getValue(String.class)).append("\n");
                            emailbody.append("Weight: ").append(snapshot.child("Weight").getValue(String.class)).append("\n");
                            emailbody.append("Phone Number: ").append(snapshot.child("PhoneNumber").getValue(String.class)).append("\n");
                            emailbody.append("Consultant: ").append(snapshot.child("Consultant").getValue(String.class)).append("\n");
                            emailbody.append("Next of Kin: ").append(snapshot.child("Next_of_kin").getValue(String.class)).append("\n");
                            emailbody.append("Number: ").append(snapshot.child("Number").getValue(String.class)).append("\n");
                            emailbody.append("\n");

                            DatabaseReference hpRef = FirebaseDatabase.getInstance().getReference("HPHandler").child(currentUserId).child("HealthProblems");
                            hpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //the below code is used to reference each element that resides inside the Health problems table and then,
                                    //prints them into the body of the email.
                                    emailbody.append("\nHealth Problems: \n");
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
                                            //the below code is used to reference each element that resides inside the history table and then,
                                            //prints them into the body of the email.
                                            emailbody.append("\nMedical History: \n");
                                            emailbody.append("Current: ").append(snapshot.child("Current").getValue(String.class)).append("\n");
                                            emailbody.append("History: ").append(snapshot.child("History").getValue(String.class)).append("\n");
                                            emailbody.append("\n");

                                            DatabaseReference iRef = FirebaseDatabase.getInstance().getReference("IHandler").child(currentUserId).child("Infections");
                                            iRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //the below code is used to reference each element that resides inside the Infections table and then,
                                                    //prints them into the body of the email.
                                                    emailbody.append("\nInfections: \n");
                                                    emailbody.append("Have you had any of these communicable infections: \n");
                                                    emailbody.append("Measles: ").append(snapshot.child("MeaslesboX").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Mumps: ").append(snapshot.child("MumpsBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Rubella: ").append(snapshot.child("RubellaBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Chickenpox: ").append(snapshot.child("ChickenpoxBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Pertussis: ").append(snapshot.child("PertussisBox").getValue(Boolean.class)).append("\n");

                                                    emailbody.append("Have you had any of these communicable infections in the last 4 weeks: \n");
                                                    emailbody.append("Measles: ").append(snapshot.child("MeaslesBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Mumps: ").append(snapshot.child("MumpsBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Rubella: ").append(snapshot.child("RubellaBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Chickenpox: ").append(snapshot.child("ChickenpoxBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Pertussis: ").append(snapshot.child("PertussisBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Gastroenteritis: ").append(snapshot.child("GastroenteritisBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Viral Respiratory Illness: ").append(snapshot.child("VRIBox").getValue(Boolean.class)).append("\n");

                                                    emailbody.append("In the last 72 hours have you: \n");
                                                    emailbody.append("Vomit: ").append(snapshot.child("VomitBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Last episode: ").append(snapshot.child("EnterVomit").getValue(String.class)).append("\n");
                                                    emailbody.append("Diarrhoea: ").append(snapshot.child("DiarrhoeaBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Last episode: ").append(snapshot.child("EnterDiarrhoea").getValue(String.class)).append("\n");

                                                    emailbody.append("Have you been colonised by organisms: ").append(snapshot.child("OrganismsBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("MRSA: ").append(snapshot.child("MRSABox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("ESBL: ").append(snapshot.child("ESBLBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("VRE: ").append(snapshot.child("VREBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("CRE: ").append(snapshot.child("CREBox").getValue(Boolean.class)).append("\n");

                                                    emailbody.append("Transfer from other hospital: ").append(snapshot.child("TransferBox").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Hospital: ").append(snapshot.child("EnterHospital").getValue(String.class)).append("\n");

                                                    emailbody.append("In need of single Isolation room: \n");
                                                    emailbody.append("Yes: ").append(snapshot.child("IsolationBoxYes").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("No: ").append(snapshot.child("IsolationBoxNo").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("IsolationReason: ").append(snapshot.child("IsolationReason").getValue(String.class)).append("\n");

                                                    emailbody.append("have you been a patient in another hospital in the past 12 months: \n");
                                                    emailbody.append("Yes: ").append(snapshot.child("OtherHospitalBoxYes").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("No: ").append(snapshot.child("OtherHospitalBoxNo").getValue(Boolean.class)).append("\n");

                                                    emailbody.append("If yes, have the relevant screens been obtained: \n");
                                                    emailbody.append("MRSA: ").append(snapshot.child("MRSABox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Date: ").append(snapshot.child("MRSADate").getValue(String.class)).append("\n");
                                                    emailbody.append("MRGNB: ").append(snapshot.child("MRGNBBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Date: ").append(snapshot.child("MRGNBDate").getValue(String.class)).append("\n");
                                                    emailbody.append("VRE: ").append(snapshot.child("VREBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Date: ").append(snapshot.child("VREDate").getValue(String.class)).append("\n");
                                                    emailbody.append("CRE: ").append(snapshot.child("CREBox2").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("Date: ").append(snapshot.child("CREDate").getValue(String.class)).append("\n");

                                                    emailbody.append("Immunocompromised: \n");
                                                    emailbody.append("Yes: ").append(snapshot.child("ImmunocompromisedBoxYes").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("No: ").append(snapshot.child("ImmunocompromisedBoxNo").getValue(Boolean.class)).append("\n");
                                                    emailbody.append("ImmunocompromisedReason: ").append(snapshot.child("ImmunocompromisedReason").getValue(String.class)).append("\n");
                                                    emailbody.append("\n");

                                                    DatabaseReference VRef = FirebaseDatabase.getInstance().getReference("VHandler").child(currentUserId).child("Vaccination");
                                                    VRef.addListenerForSingleValueEvent(new ValueEventListener(){

                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            //the below code is used to reference each element that resides inside the Vaccinations table and then,
                                                            //prints them into the body of the email.
                                                            emailbody.append("\nVaccinations: \n");
                                                            emailbody.append("At 2 months old: \n");
                                                            emailbody.append("PCV13: ").append(snapshot.child("PCV13Box1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenB: ").append(snapshot.child("MenBBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("Rotavirus: ").append(snapshot.child("RotavirusBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("6 in 1 First dose: ").append(snapshot.child("sixINoneBox1").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 4 months old: \n");
                                                            emailbody.append("6 in 1 Second dose: ").append(snapshot.child("sixINoneBox2").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenB: ").append(snapshot.child("MenBBox2").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("Rotavirus: ").append(snapshot.child("RotavirusBox2").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 6 months old: \n");
                                                            emailbody.append("6 in 1 third dose: ").append(snapshot.child("sixINoneBox3").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("PCV13: ").append(snapshot.child("PCV13Box2").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenC: ").append(snapshot.child("MenCBox1").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 12 months old: \n");
                                                            emailbody.append("MMR: ").append(snapshot.child("MMRBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenB: ").append(snapshot.child("MenBBox3").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 12+ months old: \n");
                                                            emailbody.append("Hib/MenC: ").append(snapshot.child("HibMenCBox").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("PCV13: ").append(snapshot.child("PCV13Box3").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenB: ").append(snapshot.child("MenBBox4").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 4 to 5 years: \n");
                                                            emailbody.append("4 in 1 vaccine: ").append(snapshot.child("fourINoneBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MMR: ").append(snapshot.child("MMRBox2").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("At 12 to 14 years: \n");
                                                            emailbody.append("HPV: ").append(snapshot.child("HPVBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("Tdap: ").append(snapshot.child("TdapBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("MenAcwy: ").append(snapshot.child("MenACWYBox1").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("Other: \n");
                                                            emailbody.append("Flu Vaccine: ").append(snapshot.child("FluBox1").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("Covid 19: ").append(snapshot.child("CovidBox").getValue(Boolean.class)).append("\n");
                                                            emailbody.append("Swine Flue: ").append(snapshot.child("SwineFluBox").getValue(Boolean.class)).append("\n");

                                                            emailbody.append("\n\nQR Code contents: ").append(result.getContents());

                                                            intent.putExtra(Intent.EXTRA_TEXT, emailbody.toString());
                                                            startActivity(intent);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                            //notify the user there was a problem with the vaccinations table
                                                            Toast.makeText(QRCode.this, "Error retrieving Vaccination data", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    //notify the user there was a problem with the infections table
                                                    Toast.makeText(QRCode.this, "Error retrieving infections data", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            //notify the user there was a problem with the history table
                                            Toast.makeText(QRCode.this, "Error retrieving Medical history", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    //notify the user there was a problem with the health problems table
                                    Toast.makeText(QRCode.this, "Error retrieving health problems", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //notify the user there was a problem with the personal details table
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem detailsButton = menu.findItem(R.id.action_personal_details);
        detailsButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(QRCode.this, PersonalDetails.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }
}