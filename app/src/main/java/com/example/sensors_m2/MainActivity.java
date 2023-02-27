package com.example.sensors_m2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

import com.github.mikephil.charting.data.Entry;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMessage;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Bienvenue dans ChatFarm", Toast.LENGTH_LONG).show();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


        //FindViewById :
        editTextMessage = findViewById(R.id.editText);
        editTextNumber = findViewById(R.id.editTextNumber);

        GlobalClass.Phone= findViewById(R.id.textView);
        GlobalClass.Text = findViewById(R.id.textView2);

        Button button = findViewById(R.id.Ask_Data);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait une demande de donnée", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), mpchartexample.class);
                startActivity(intent);
            }
        });
    }

    public void sendSMS(View view) {
        String message = editTextMessage.getText().toString();
        String number = editTextNumber.getText().toString();
        String[] text= ProcessSMS(message);

        GlobalClass.values.add(new Entry(Integer.parseInt(text[0]),Integer.parseInt(text[1])));

        SmsManager mySmsManager = SmsManager.getDefault();      //on importe l'API dans une variable
        try {
            mySmsManager.sendTextMessage(number,null,message,null,null);
            Log.e(TAG,"Message successfully sent");
            Toast.makeText(MainActivity.this, "SMS envoyé", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e(TAG,"Couldn't send message, try again");
            Toast.makeText(MainActivity.this, "Echec de l'envoi", Toast.LENGTH_SHORT).show();
        }
    }

    public String[] ProcessSMS (String message){
        String x;
        String y;

        x= message.substring(0,message.indexOf("/"));
        y =message.substring(message.indexOf("/"));


        return new String[]{x,y};
    }




}

