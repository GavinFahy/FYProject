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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    private TextView register_link;
    private EditText editEmail, editPassword;
    private Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        register_link = (TextView) findViewById(R.id.register_link);
        register_link.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.LoginButton);
        signIn.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_link:
                startActivity(new Intent(this, register.class));
                break;

            case R.id.LoginButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        //retrieves the email and password that is entered into the email and password fields
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //checks to see if the email field is empty
        if(email.isEmpty()){
            editEmail.setError("Email field cannot be empty!");
            editEmail.requestFocus();
            return;
        }

        //checks to see if the password field is empty
        if(password.isEmpty()){
            editPassword.setError("Password field cannot be empty!");
            editPassword.requestFocus();
            return;
        }
        //Checks to see if your email address is a valid email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Not a valid email address!");
            editEmail.requestFocus();
            return;
        }

        //Checks to see if the entered email is longer than the required  digits
        if(password.length() < 6){
            editPassword.setError("Password length must be at least 6 digits long");
            editPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checks t see if the above fields are all correct it then attempts to sign the user in
                if(task.isSuccessful()){
                    //if the user signs in successfully they are redirected to the Homepage of the application
                    Intent intent = new Intent(login.this, HomePage.class);
                    startActivity(intent);
                }else{
                    //else it returns with the message that you failed to sign in
                    Toast.makeText(login.this, "Failed to login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
