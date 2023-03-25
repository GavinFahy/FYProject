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

        //initials the UI elements of the page + sets up the click listeners
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
            //when the link is clicked it will bring the user to the login page
            case R.id.login_link:
                startActivity(new Intent(this, login.class));
                break;
                //if register button is selected go to the registerButton activity
            case R.id.registerButton:
                registerButton();
        }
    }

    private void registerButton() {
        //retrieves the email and password field contents
        String email= editEmail.getText().toString().trim();
        String password= editPassword.getText().toString().trim();

        //checks to see if the email fields has been filled in
        if(email.isEmpty()){
            editEmail.setError("Email field cannot be empty!");
            editEmail.requestFocus();
            return;
        }

        //checks to see if the password field has been filled in
        if(password.isEmpty()){
            editPassword.setError("Password field cannot be empty!");
            editPassword.requestFocus();
            return;
        }

        //checks to see if the email is a valid email address
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Not a valid email address!");
            editEmail.requestFocus();
            return;
        }

        //checks to see if the password is of at leasst the required length of 6
        if(password.length() < 6){
            editPassword.setError("Password length must be at least 6 digits long");
            editPassword.requestFocus();
            return;
        }

        //here the application uses firebase to attempt to create a new user using the details from the password and email fields
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //once the task is complete if it is successful it will create a new
                        //User object that stores the users password and email.
                        if(task.isSuccessful()){
                            User user = new User(email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //if this task is complete successfully it returns a message saying so
                                            if(task.isSuccessful()){
                                                Toast.makeText(register.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(register.this, login.class);
                                                startActivity(intent);
                                            }else{
                                                //else it returns a message saying you failed to register
                                                Toast.makeText(register.this, "Failed to register", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            //else it returns a message saying you failed to register
                            Toast.makeText(register.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
