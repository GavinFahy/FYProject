package com.example.fyproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.DataAccess.I_DataAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Infections extends AppCompatActivity {

    private I_DataAccess IDataAccess;
    private DatabaseReference reference;
    private String currentUserId;
    boolean measlesboX, mumpsBox, rubellaBox, chickenpoxBox, pertussisBox, measlesBox2, mumpsBox2, rubellaBox2, chickenpoxBox2, pertussisBox2, gastroenteritisBox,
            vRIBox, vomitBox, diarrhoeaBox, organismsBox, mRSABox, eSBLBox, vREBox, cREBox, transferBox, isolationBoxYes, isolationBoxNo, otherHospitalBoxYes,
            otherHospitalBoxNo, mRSABox2, mRGNBBox2, cREBox2, vREBox2, immunocompromisedBoxYes, immunocompromisedBoxNo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infections);

        IDataAccess = new I_DataAccess();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //check for text fields
        final EditText EnterVomit = findViewById(R.id.EnterVomit);
        final EditText EnterDiarrhoea = findViewById(R.id.EnterDiarrhoea);
        final EditText EnterHospital = findViewById(R.id.EnterHospital);
        final EditText IsolationReason = findViewById(R.id.IsolationReason);
        final EditText MRSADate = findViewById(R.id.MRSADate);
        final EditText MRGNBDate = findViewById(R.id.MRGNBDate);
        final EditText CREDate = findViewById(R.id.CREDate);
        final EditText VREDate = findViewById(R.id.VREDate);
        final EditText ImmunocompromisedReason = findViewById(R.id.ImmunocompromisedReason);
        //checks for check box's
        final CheckBox MeaslesboX = findViewById(R.id.MeaslesboX);
        final CheckBox MumpsBox = findViewById(R.id.MumpsBox);
        final CheckBox RubellaBox = findViewById(R.id.RubellaBox);
        final CheckBox ChickenpoxBox = findViewById(R.id.ChickenpoxBox);
        final CheckBox PertussisBox = findViewById(R.id.PertussisBox);
        final CheckBox MeaslesBox2 = findViewById(R.id.MeaslesBox2);
        final CheckBox MumpsBox2 = findViewById(R.id.MumpsBox2);
        final CheckBox RubellaBox2 = findViewById(R.id.RubellaBox2);
        final CheckBox ChickenpoxBox2 = findViewById(R.id.ChickenpoxBox2);
        final CheckBox PertussisBox2 = findViewById(R.id.PertussisBox2);
        final CheckBox GastroenteritisBox = findViewById(R.id.GastroenteritisBox);
        final CheckBox VRIBox = findViewById(R.id.VRIBox);
        final CheckBox VomitBox = findViewById(R.id.VomitBox);
        final CheckBox DiarrhoeaBox = findViewById(R.id.DiarrhoeaBox);
        final CheckBox OrganismsBox = findViewById(R.id.OrganismsBox);
        final CheckBox MRSABox = findViewById(R.id.MRSABox);
        final CheckBox ESBLBox = findViewById(R.id.ESBLBox);
        final CheckBox VREBox = findViewById(R.id.VREBox);
        final CheckBox CREBox = findViewById(R.id.CREBox);
        final CheckBox TransferBox = findViewById(R.id.TransferBox);
        final CheckBox IsolationBoxYes = findViewById(R.id.IsolationBoxYes);
        final CheckBox IsolationBoxNo = findViewById(R.id.IsolationBoxNo);
        final CheckBox OtherHospitalBoxYes = findViewById(R.id.OtherHospitalBoxYes);
        final CheckBox OtherHospitalBoxNo = findViewById(R.id.OtherHospitalBoxNo);
        final CheckBox MRSABox2 = findViewById(R.id.MRSABox2);
        final CheckBox MRGNBBox2 = findViewById(R.id.MRGNBBox2);
        final CheckBox CREBox2 = findViewById(R.id.CREBox2);
        final CheckBox VREBox2 = findViewById(R.id.VREBox2);
        final CheckBox ImmunocompromisedBoxYes = findViewById(R.id.ImmunocompromisedBoxYes);
        final CheckBox ImmunocompromisedBoxNo = findViewById(R.id.ImmunocompromisedBoxNo);

        Button Update = findViewById(R.id.Update);

        Update.setOnClickListener(V -> {

            HashMap<String, Object> data = new HashMap<>();
            //data for the text fields
            data.put("EnterVomit", EnterVomit.getText().toString());
            data.put("EnterDiarrhoea", EnterDiarrhoea.getText().toString());
            data.put("EnterHospital", EnterHospital.getText().toString());
            data.put("IsolationReason", IsolationReason.getText().toString());
            data.put("MRSADate", MRSADate.getText().toString());
            data.put("MRGNBDate", MRGNBDate.getText().toString());
            data.put("CREDate", CREDate.getText().toString());
            data.put("VREDate", VREDate.getText().toString());
            data.put("ImmunocompromisedReason", ImmunocompromisedReason.getText().toString());

            //data for the check box's
            data.put("MeaslesboX", MeaslesboX.isChecked());
            data.put("MumpsBox", MumpsBox.isChecked());
            data.put("RubellaBox", RubellaBox.isChecked());
            data.put("ChickenpoxBox", ChickenpoxBox.isChecked());
            data.put("PertussisBox", PertussisBox.isChecked());
            data.put("MeaslesBox2", MeaslesBox2.isChecked());
            data.put("MumpsBox2", MumpsBox2.isChecked());
            data.put("RubellaBox2", RubellaBox2.isChecked());
            data.put("ChickenpoxBox2", ChickenpoxBox2.isChecked());
            data.put("PertussisBox2", PertussisBox2.isChecked());
            data.put("GastroenteritisBox", GastroenteritisBox.isChecked());
            data.put("VRIBox", VRIBox.isChecked());
            data.put("VomitBox", VomitBox.isChecked());
            data.put("DiarrhoeaBox", DiarrhoeaBox.isChecked());
            data.put("OrganismsBox", OrganismsBox.isChecked());
            data.put("MRSABox", MRSABox.isChecked());
            data.put("ESBLBox", ESBLBox.isChecked());
            data.put("VREBox", VREBox.isChecked());
            data.put("CREBox", CREBox.isChecked());
            data.put("TransferBox", TransferBox.isChecked());
            data.put("IsolationBoxYes", IsolationBoxYes.isChecked());
            data.put("IsolationBoxNo", IsolationBoxNo.isChecked());
            data.put("OtherHospitalBoxYes", OtherHospitalBoxYes.isChecked());
            data.put("OtherHospitalBoxNo", OtherHospitalBoxNo.isChecked());
            data.put("MRSABox2", MRSABox2.isChecked());
            data.put("MRGNBBox2", MRGNBBox2.isChecked());
            data.put("CREBox2", CREBox2.isChecked());
            data.put("VREBox2", VREBox2.isChecked());
            data.put("ImmunocompromisedBoxYes", ImmunocompromisedBoxYes.isChecked());
            data.put("ImmunocompromisedBoxNo", ImmunocompromisedBoxNo.isChecked());

            IDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        reference = FirebaseDatabase.getInstance().getReference("IHandler").child(currentUserId).child("Infections");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //checking if data fields exist and if so display them
                    String enterVomit = snapshot.child("EnterVomit").getValue(String.class);
                    String enterDiarrhoea = snapshot.child("EnterDiarrhoea").getValue(String.class);
                    String enterHospital = snapshot.child("EnterHospital").getValue(String.class);
                    String isolationReason = snapshot.child("IsolationReason").getValue(String.class);
                    String mRSADate = snapshot.child("MRSADate").getValue(String.class);
                    String mRGNBDate = snapshot.child("MRGNBDate").getValue(String.class);
                    String cREDate = snapshot.child("CREDate").getValue(String.class);
                    String vREDate = snapshot.child("VREDate").getValue(String.class);
                    String immunocompromisedReason = snapshot.child("ImmunocompromisedReason").getValue(String.class);

                    boolean measlesboX, mumpsBox, rubellaBox, chickenpoxBox, pertussisBox, measlesBox2, mumpsBox2, rubellaBox2, chickenpoxBox2, pertussisBox2, gastroenteritisBox,
                            vRIBox, vomitBox, diarrhoeaBox, organismsBox, mRSABox, eSBLBox, vREBox, cREBox, transferBox, isolationBoxYes, isolationBoxNo, otherHospitalBoxYes,
                            otherHospitalBoxNo, mRSABox2, mRGNBBox2, cREBox2, vREBox2, immunocompromisedBoxYes, immunocompromisedBoxNo;


                    //checking to see if check boxes have been check and if so they are displayed
                    measlesboX = Boolean.TRUE.equals(snapshot.child("MeaslesboX").getValue(Boolean.class));
                    mumpsBox = Boolean.TRUE.equals(snapshot.child("MumpsBox").getValue(Boolean.class));
                    rubellaBox = Boolean.TRUE.equals(snapshot.child("RubellaBox").getValue(Boolean.class));
                    chickenpoxBox = Boolean.TRUE.equals(snapshot.child("ChickenpoxBox").getValue(Boolean.class));
                    pertussisBox = Boolean.TRUE.equals(snapshot.child("PertussisBox").getValue(Boolean.class));
                    measlesBox2 = Boolean.TRUE.equals(snapshot.child("MeaslesBox2").getValue(Boolean.class));
                    mumpsBox2 = Boolean.TRUE.equals(snapshot.child("MumpsBox2").getValue(Boolean.class));
                    rubellaBox2 = Boolean.TRUE.equals(snapshot.child("RubellaBox2").getValue(Boolean.class));
                    chickenpoxBox2 = Boolean.TRUE.equals(snapshot.child("ChickenpoxBox2").getValue(Boolean.class));
                    pertussisBox2 = Boolean.TRUE.equals(snapshot.child("PertussisBox2").getValue(Boolean.class));
                    gastroenteritisBox = Boolean.TRUE.equals(snapshot.child("GastroenteritisBox").getValue(Boolean.class));
                    vRIBox = Boolean.TRUE.equals(snapshot.child("VRIBox").getValue(Boolean.class));
                    vomitBox = Boolean.TRUE.equals(snapshot.child("VomitBox").getValue(Boolean.class));
                    diarrhoeaBox = Boolean.TRUE.equals(snapshot.child("DiarrhoeaBox").getValue(Boolean.class));
                    organismsBox = Boolean.TRUE.equals(snapshot.child("OrganismsBox").getValue(Boolean.class));
                    mRSABox = Boolean.TRUE.equals(snapshot.child("MRSABox").getValue(Boolean.class));
                    eSBLBox = Boolean.TRUE.equals(snapshot.child("ESBLBox").getValue(Boolean.class));
                    vREBox = Boolean.TRUE.equals(snapshot.child("VREBox").getValue(Boolean.class));
                    cREBox = Boolean.TRUE.equals(snapshot.child("CREBox").getValue(Boolean.class));
                    transferBox = Boolean.TRUE.equals(snapshot.child("TransferBox").getValue(Boolean.class));
                    isolationBoxYes = Boolean.TRUE.equals(snapshot.child("IsolationBoxYes").getValue(Boolean.class));
                    isolationBoxNo = Boolean.TRUE.equals(snapshot.child("IsolationBoxNo").getValue(Boolean.class));
                    otherHospitalBoxYes = Boolean.TRUE.equals(snapshot.child("OtherHospitalBoxYes").getValue(Boolean.class));
                    otherHospitalBoxNo = Boolean.TRUE.equals(snapshot.child("OtherHospitalBoxNo").getValue(Boolean.class));
                    mRSABox2 = Boolean.TRUE.equals(snapshot.child("MRSABox2").getValue(Boolean.class));
                    mRGNBBox2 = Boolean.TRUE.equals(snapshot.child("MRGNBBox2").getValue(Boolean.class));
                    cREBox2 = Boolean.TRUE.equals(snapshot.child("CREBox2").getValue(Boolean.class));
                    vREBox2 = Boolean.TRUE.equals(snapshot.child("VREBox2").getValue(Boolean.class));
                    immunocompromisedBoxYes = Boolean.TRUE.equals(snapshot.child("ImmunocompromisedBoxYes").getValue(Boolean.class));
                    immunocompromisedBoxNo = Boolean.TRUE.equals(snapshot.child("ImmunocompromisedBoxNo").getValue(Boolean.class));



                    EnterVomit.setText(enterVomit);
                    EnterDiarrhoea.setText(enterDiarrhoea);
                    EnterHospital.setText(enterHospital);
                    IsolationReason.setText(isolationReason);
                    MRSADate.setText(mRSADate);
                    MRGNBDate.setText(mRGNBDate);
                    CREDate.setText(cREDate);
                    VREDate.setText(vREDate);
                    ImmunocompromisedReason.setText(immunocompromisedReason);

                    MeaslesboX.setChecked(measlesboX);
                    MumpsBox.setChecked(mumpsBox);
                    RubellaBox.setChecked(rubellaBox);
                    ChickenpoxBox.setChecked(chickenpoxBox);
                    PertussisBox.setChecked(pertussisBox);
                    MeaslesBox2.setChecked(measlesBox2);
                    MumpsBox2.setChecked(mumpsBox2);
                    RubellaBox2.setChecked(rubellaBox2);
                    ChickenpoxBox2.setChecked(chickenpoxBox2);
                    PertussisBox2.setChecked(pertussisBox2);
                    GastroenteritisBox.setChecked(gastroenteritisBox);
                    VRIBox.setChecked(vRIBox);
                    VomitBox.setChecked(vomitBox);
                    DiarrhoeaBox.setChecked(diarrhoeaBox);
                    OrganismsBox.setChecked(organismsBox);
                    MRSABox.setChecked(mRSABox);
                    ESBLBox.setChecked(eSBLBox);
                    VREBox.setChecked(vREBox);
                    CREBox.setChecked(cREBox);
                    TransferBox.setChecked(transferBox);
                    IsolationBoxYes.setChecked(isolationBoxYes);
                    IsolationBoxNo.setChecked(isolationBoxNo);
                    OtherHospitalBoxYes.setChecked(otherHospitalBoxYes);
                    OtherHospitalBoxNo.setChecked(otherHospitalBoxNo);
                    MRSABox2.setChecked(mRSABox2);
                    MRGNBBox2.setChecked(mRGNBBox2);
                    CREBox2.setChecked(cREBox2);
                    VREBox2.setChecked(vREBox2);
                    ImmunocompromisedBoxYes.setChecked(immunocompromisedBoxYes);
                    ImmunocompromisedBoxNo.setChecked(immunocompromisedBoxNo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Infections.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });
    }
}