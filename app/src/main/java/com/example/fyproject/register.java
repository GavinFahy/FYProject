package com.example.fyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.Handlers.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView registerButton, login_link;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();

        login_link = (TextView) findViewById(R.id.login_link);
        login_link.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_link:
                startActivity(new Intent(this, login.class));
                break;
            case R.id.registerButton:
                registerButton();
        }
    }

    private void registerButton() {
        String email= editEmail.getText().toString().trim();
        String password= editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email field cannot be empty!");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password field cannot be empty!");
            editPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Not a valid email address!");
            editEmail.requestFocus();
            return;
        }
        if(password.length() < 6){
            editPassword.setError("Password length must be at least 6 digits long");
            editPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(register.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(register.this, login.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(register.this, "Failed to register", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(register.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
