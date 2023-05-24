package com.example.sensors_m2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensors_m2.GlobalClass;
import com.example.sensors_m2.MainActivity;
import com.example.sensors_m2.R;
import com.google.firebase.auth.FirebaseAuth;

public class RelaiActivity extends AppCompatActivity {

    //déclaration des cases Texte pour les relais
    public static EditText relai1;
    public static EditText relai2;
    public static EditText relai3;
    public static EditText relai4;


    //déclaration des switchs
    public static Switch switch1;
    public static Switch switch2;
    public static Switch switch3;
    public static Switch switch4;


    //déclaration des booléens pour stocker l'état des switchs
    public static boolean isSwitchOn1;
    public static boolean isSwitchOn2;
    public static boolean isSwitchOn3;
    public static boolean isSwitchOn4;


    //déclaration de l'état pour le bouton edit
    public static boolean state = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relai);

        ImageView editBtn1=findViewById(R.id.editBtn1);
        ImageView editBtn2=findViewById(R.id.editBtn2);
        ImageView editBtn3=findViewById(R.id.editBtn3);
        ImageView editBtn4=findViewById(R.id.editBtn4);

        relai1 =findViewById(R.id.relai1);
        relai2 =findViewById(R.id.relai2);
        relai3 =findViewById(R.id.relai3);
        relai4 =findViewById(R.id.relai4);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        switch4 = findViewById(R.id.switch4);

        switch1.setChecked(GlobalClass.isSwitchOn1);
        switch2.setChecked(GlobalClass.isSwitchOn2);
        switch3.setChecked(GlobalClass.isSwitchOn3);
        switch4.setChecked(GlobalClass.isSwitchOn4);


        relai1.setText(GlobalClass.relai1);
        relai2.setText(GlobalClass.relai2);
        relai3.setText(GlobalClass.relai3);
        relai4.setText(GlobalClass.relai4);


        //bouton qui rend la case de Relai modifiable
        editBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state==false) {
                    state=true;}
                else {
                    state=false;}
                relai1.setEnabled(state);
            }
        });

        editBtn2.setOnClickListener(new View.OnClickListener() {
            boolean state=false;
            @Override
            public void onClick(View view) {
                if (state==false) {
                    state=true;}
                else {
                    state=false;}
                relai2.setEnabled(state);
            }
        });

        editBtn3.setOnClickListener(new View.OnClickListener() {
            boolean state=false;
            @Override
            public void onClick(View view) {
                if (state==false) {
                    state=true;}
                else {
                    state=false;}
                relai3.setEnabled(state);
            }
        });


        editBtn4.setOnClickListener(new View.OnClickListener() {
            boolean state=false;
            @Override
            public void onClick(View view) {
                if (state==false) {
                    state=true;}
                else {
                    state=false;}
                relai4.setEnabled(state);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.actionBack){
            GlobalClass.relai1=relai1.getText().toString();
            GlobalClass.relai2=relai2.getText().toString();
            GlobalClass.relai3=relai3.getText().toString();
            GlobalClass.relai4=relai4.getText().toString();

            GlobalClass.isSwitchOn1=switch1.isChecked();
            GlobalClass.isSwitchOn2=switch2.isChecked();
            GlobalClass.isSwitchOn3=switch3.isChecked();
            GlobalClass.isSwitchOn4=switch4.isChecked();
            /*
            switch1.setChecked(GlobalClass.isSwitchOn1);
            switch2.setChecked(GlobalClass.isSwitchOn2);
            switch3.setChecked(GlobalClass.isSwitchOn3);
            switch4.setChecked(GlobalClass.isSwitchOn4);

             */

            startActivity(new Intent(this, MainActivity.class));

        }
        else if (id== R.id.actionLogout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));

        }
        else if(id==R.id.actionRefresh){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
