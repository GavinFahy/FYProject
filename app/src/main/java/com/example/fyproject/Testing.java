package com.example.fyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Button showText = findViewById(R.id.textButton);
        final TextView textBox = findViewById(R.id.textBox1);

        showText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textBox.getVisibility() == View.VISIBLE) {
                    textBox.setVisibility(View.INVISIBLE);
                }else{
                    textBox.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}