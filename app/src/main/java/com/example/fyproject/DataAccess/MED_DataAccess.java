package com.example.fyproject.DataAccess;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MED_DataAccess {
    private DatabaseReference DBReference;
    private FirebaseAuth mAuth;
    //method used to gain access to the database
    public MED_DataAccess()
    {
        mAuth = FirebaseAuth.getInstance();
        DBReference = FirebaseDatabase.getInstance().getReference();
    }
    //method used to update the data
    public Task<Void> update(String userId, HashMap<String, Object> data) {
        return DBReference.child("MEDHandler").child(userId).child("Medicine").updateChildren(data);
    }
}
