package com.example.fyproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode extends AppCompatActivity {

    ImageView idIVQrcode;
    EditText idEdt;
    Button idBtnGenerateQR;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        idEdt = findViewById(R.id.idEdt);
        idIVQrcode = findViewById(R.id.idIVQrcode);
        idBtnGenerateQR = findViewById(R.id.idBtnGenerateQR);

        idBtnGenerateQR.setOnClickListener(v ->{
            generateQR();
        });
    }

    private void generateQR() {
        String text = idEdt.getText().toString().trim();
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

}