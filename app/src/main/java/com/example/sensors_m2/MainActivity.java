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
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

import com.example.sensors_m2.Graphe.MpcharteTemp;
import com.example.sensors_m2.activities.Detail_CO2_Activity;
import com.example.sensors_m2.activities.Detail_Temp_Activity;

import com.example.sensors_m2.activities.Detail_CO2_Activity;
import com.example.sensors_m2.activities.Detail_Temp_Activity;
import com.github.mikephil.charting.data.Entry;

public class MainActivity extends AppCompatActivity {

    //private EditText editTextNumber;
    private EditText editTextNumber;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Bienvenue dans ChatFarm", Toast.LENGTH_LONG).show();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


        //FindViewById :
        editTextNumber = findViewById(R.id.editText);

        ImageView eraser=findViewById(R.id.eraser);
        ImageView check=findViewById(R.id.check);
        ImageView edit=findViewById(R.id.edit);

        Button button_temp=findViewById(R.id.temperature);
        Button button_humid=findViewById(R.id.humidity);
        Button button_CO2=findViewById(R.id.CO2);
        Button button_smoke=findViewById(R.id.smoke);


        //bouton qui supprime le contenu de la case EditText
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setText("");
            }
        });

        //bouton qui rend la case de téléphone non modifiable
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setEnabled(false);
                eraser.setEnabled(false);
            }
        });

        //bouton qui rend la case de téléphone modifiable
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setEnabled(true);
                eraser.setEnabled(true);
            }
        });

        //boutons qui ouvrent une nouvelle fenêtre après appui sur capteur
        button_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur Température", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Detail_Temp_Activity.class);
                startActivity(intent);
            }
        });

        button_CO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur CO2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Detail_CO2_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void sendSMS(View view) {
        String message = "test";
        String number = editTextNumber.getText().toString();


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

}

