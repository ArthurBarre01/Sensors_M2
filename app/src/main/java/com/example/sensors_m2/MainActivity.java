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

import com.example.sensors_m2.Graphe.MpcharteTemp;
import com.example.sensors_m2.activities.DetailCO2Activity;
import com.example.sensors_m2.activities.DetailTempActivity;
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

        GlobalClass.Phone= findViewById(R.id.textView);
        GlobalClass.Text = findViewById(R.id.textView2);

        Button button = findViewById(R.id.ask_Data);
        Button button_temp=findViewById(R.id.temperature);
        Button button_humid=findViewById(R.id.humidity);
        Button button_CO2=findViewById(R.id.CO2);
        Button button_smoke=findViewById(R.id.smoke);



        //bouton qui affiche le graphe de données
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait une demande de données", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MpcharteTemp.class);
                startActivity(intent);
            }
        });

        //boutons qui ouvrent une nouvelle fenêtre après appui sur capteur
        button_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur Température", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DetailTempActivity.class);
                startActivity(intent);
            }
        });

        button_CO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message lorsque le bouton est cliqué
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur CO2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DetailCO2Activity.class);
                startActivity(intent);
            }
        });
    }

    public void sendSMS(View view) {
        String message = editTextMessage.getText().toString();
        String number = "0672428937";
        //String number = editTextNumber.getText().toString();
        String[] text= ProcessSMS(message);

        GlobalClass.Temp_values.add(new Entry(Integer.parseInt(text[0]),Integer.parseInt(text[1])));

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

