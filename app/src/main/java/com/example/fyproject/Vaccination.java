package com.example.fyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.fyproject.DataAccess.V_DataAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Vaccination extends AppCompatActivity {

    private V_DataAccess VDataAccess;
    private DatabaseReference reference;
    private String currentUserId;
    boolean pCV13Box1, menBBox1, rotavirusBox1, SixINoneBox1, SixINoneBox2, menBBox2, rotavirusBox2,
            SixINoneBox3, pCV13Box2, menCBox1, mMRBox1, menBBox3, hibMenCBox, pCV13Box3, menBBox4,
            FourINoneBox1, mMRBox2, hPVBox1, tdapBox1, menACWYBox1, fluBox1, covidBox, swineFluBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinations);

        VDataAccess = new V_DataAccess();
        //retrieving the current users ID from firebase
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //initials the check box's with Unique IDS
        final CheckBox PCV13Box1 = findViewById(R.id.PCV13Box1);
        final CheckBox MenBBox1 = findViewById(R.id.MenBBox1);
        final CheckBox RotavirusBox1 = findViewById(R.id.RotavirusBox1);
        final CheckBox sixINoneBox1 = findViewById(R.id.sixINoneBox1);
        final CheckBox sixINoneBox2 = findViewById(R.id.sixINoneBox2);
        final CheckBox MenBBox2 = findViewById(R.id.MenBBox2);
        final CheckBox RotavirusBox2 = findViewById(R.id.RotavirusBox2);
        final CheckBox sixINoneBox3 = findViewById(R.id.sixINoneBox3);
        final CheckBox PCV13Box2 = findViewById(R.id.PCV13Box2);
        final CheckBox MenCBox1 = findViewById(R.id.MenCBox1);
        final CheckBox MMRBox1 = findViewById(R.id.MMRBox1);
        final CheckBox MenBBox3 = findViewById(R.id.MenBBox3);
        final CheckBox HibMenCBox = findViewById(R.id.HibMenCBox);
        final CheckBox PCV13Box3 = findViewById(R.id.PCV13Box3);
        final CheckBox MenBBox4 = findViewById(R.id.MenBBox4);
        final CheckBox fourINoneBox1 = findViewById(R.id.fourINoneBox1);
        final CheckBox MMRBox2 = findViewById(R.id.MMRBox2);
        final CheckBox HPVBox1 = findViewById(R.id.HPVBox1);
        final CheckBox TdapBox1 = findViewById(R.id.TdapBox1);
        final CheckBox MenACWYBox1 = findViewById(R.id.MenACWYBox1);
        final CheckBox FluBox1 = findViewById(R.id.FluBox1);
        final CheckBox CovidBox = findViewById(R.id.CovidBox);
        final CheckBox SwineFluBox = findViewById(R.id.SwineFluBox);

        Button Update = findViewById(R.id.Update);
        //listens for the update button to be clicked
        Update.setOnClickListener(V -> {

            HashMap<String, Object> data = new HashMap<>();
            //data for the check box's
            data.put("PCV13Box1", PCV13Box1.isChecked());
            data.put("MenBBox1", MenBBox1.isChecked());
            data.put("RotavirusBox1", RotavirusBox1.isChecked());
            data.put("sixINoneBox1", sixINoneBox1.isChecked());
            data.put("sixINoneBox2", sixINoneBox2.isChecked());
            data.put("MenBBox2", MenBBox2.isChecked());
            data.put("RotavirusBox2", RotavirusBox2.isChecked());
            data.put("sixINoneBox3", sixINoneBox3.isChecked());
            data.put("PCV13Box2", PCV13Box2.isChecked());
            data.put("MenCBox1", MenCBox1.isChecked());
            data.put("MMRBox1", MMRBox1.isChecked());
            data.put("MenBBox3", MenBBox3.isChecked());
            data.put("HibMenCBox", HibMenCBox.isChecked());
            data.put("PCV13Box3", PCV13Box3.isChecked());
            data.put("MenBBox4", MenBBox4.isChecked());
            data.put("fourINoneBox1", fourINoneBox1.isChecked());
            data.put("MMRBox2", MMRBox2.isChecked());
            data.put("HPVBox1", HPVBox1.isChecked());
            data.put("TdapBox1", TdapBox1.isChecked());
            data.put("MenACWYBox1", MenACWYBox1.isChecked());
            data.put("FluBox1", FluBox1.isChecked());
            data.put("CovidBox", CovidBox.isChecked());
            data.put("SwineFluBox", SwineFluBox.isChecked());

            //update the date where the uuid matches the current users UUID
            VDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        //references the firebase for the details inside the vaccination table where the UUID matches the curentUserId
        reference = FirebaseDatabase.getInstance().getReference("VHandler").child(currentUserId).child("Vaccination");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //checking to see if check boxes have been check and if so they are displayed
                    pCV13Box1 = Boolean.TRUE.equals(snapshot.child("PCV13Box1").getValue(Boolean.class));
                    menBBox1 = Boolean.TRUE.equals(snapshot.child("MenBBox1").getValue(Boolean.class));
                    rotavirusBox1 = Boolean.TRUE.equals(snapshot.child("RotavirusBox1").getValue(Boolean.class));
                    SixINoneBox1 = Boolean.TRUE.equals(snapshot.child("sixINoneBox1").getValue(Boolean.class));
                    SixINoneBox2 = Boolean.TRUE.equals(snapshot.child("sixINoneBox2").getValue(Boolean.class));
                    menBBox2 = Boolean.TRUE.equals(snapshot.child("MenBBox2").getValue(Boolean.class));
                    rotavirusBox2 = Boolean.TRUE.equals(snapshot.child("RotavirusBox2").getValue(Boolean.class));
                    SixINoneBox3 = Boolean.TRUE.equals(snapshot.child("sixINoneBox3").getValue(Boolean.class));
                    pCV13Box2 = Boolean.TRUE.equals(snapshot.child("PCV13Box2").getValue(Boolean.class));
                    menCBox1 = Boolean.TRUE.equals(snapshot.child("MenCBox1").getValue(Boolean.class));
                    mMRBox1 = Boolean.TRUE.equals(snapshot.child("MMRBox1").getValue(Boolean.class));
                    menBBox3 = Boolean.TRUE.equals(snapshot.child("MenBBox3").getValue(Boolean.class));
                    hibMenCBox = Boolean.TRUE.equals(snapshot.child("HibMenCBox").getValue(Boolean.class));
                    pCV13Box3 = Boolean.TRUE.equals(snapshot.child("PCV13Box3").getValue(Boolean.class));
                    menBBox4 = Boolean.TRUE.equals(snapshot.child("MenBBox4").getValue(Boolean.class));
                    FourINoneBox1 = Boolean.TRUE.equals(snapshot.child("fourINoneBox1").getValue(Boolean.class));
                    mMRBox2 = Boolean.TRUE.equals(snapshot.child("MMRBox2").getValue(Boolean.class));
                    hPVBox1 = Boolean.TRUE.equals(snapshot.child("HPVBox1").getValue(Boolean.class));
                    tdapBox1 = Boolean.TRUE.equals(snapshot.child("TdapBox1").getValue(Boolean.class));
                    menACWYBox1 = Boolean.TRUE.equals(snapshot.child("MenACWYBox1").getValue(Boolean.class));
                    fluBox1 = Boolean.TRUE.equals(snapshot.child("FluBox1").getValue(Boolean.class));
                    covidBox = Boolean.TRUE.equals(snapshot.child("CovidBox").getValue(Boolean.class));
                    swineFluBox = Boolean.TRUE.equals(snapshot.child("SwineFluBox").getValue(Boolean.class));

                    //sets the state of the check boxs of the page
                    PCV13Box1.setChecked(pCV13Box1);
                    MenBBox1.setChecked(menBBox1);
                    RotavirusBox1.setChecked(rotavirusBox1);
                    sixINoneBox1.setChecked(SixINoneBox1);
                    sixINoneBox2.setChecked(SixINoneBox2);
                    MenBBox2.setChecked(menBBox2);
                    RotavirusBox2.setChecked(rotavirusBox2);
                    sixINoneBox3.setChecked(SixINoneBox3);
                    PCV13Box2.setChecked(pCV13Box2);
                    MenCBox1.setChecked(menCBox1);
                    MMRBox1.setChecked(mMRBox1);
                    MenBBox3.setChecked(menBBox3);
                    HibMenCBox.setChecked(hibMenCBox);
                    PCV13Box3.setChecked(pCV13Box3);
                    MenBBox4.setChecked(menBBox4);
                    fourINoneBox1.setChecked(FourINoneBox1);
                    MMRBox2.setChecked(mMRBox2);
                    HPVBox1.setChecked(hPVBox1);
                    TdapBox1.setChecked(tdapBox1);
                    MenACWYBox1.setChecked(menACWYBox1);
                    FluBox1.setChecked(fluBox1);
                    CovidBox.setChecked(covidBox);
                    SwineFluBox.setChecked(swineFluBox);
                }
            }
            //notify the user if something went wrong
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vaccination.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

    }
}