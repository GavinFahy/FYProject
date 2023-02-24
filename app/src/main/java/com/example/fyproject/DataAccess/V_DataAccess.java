package com.example.fyproject.DataAccess;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class V_DataAccess {
    private DatabaseReference DBReference;
    private FirebaseAuth mAuth;
    //method used to gain access to the database
    public V_DataAccess()
    {
        mAuth = FirebaseAuth.getInstance();
        DBReference = FirebaseDatabase.getInstance().getReference();
    }
    //method used to update teh data
    public Task<Void> update(String userId, HashMap<String, Object> data) {
        return DBReference.child("VHandler").child(userId).child("Vaccination").updateChildren(data);
    }
}
