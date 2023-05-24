package com.example.sensors_m2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensors_m2.activities.Detail_Humidity_Activity;
import com.example.sensors_m2.activities.Detail_CO2_Activity;
import com.example.sensors_m2.activities.Detail_Temp_Activity;
import com.example.sensors_m2.activities.LoginActivity;
import com.example.sensors_m2.activities.RelaiActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    //private EditText editTextNumber;
    public static EditText editTextNumber;


    //Variable pour les capteurs


    //Variable pour afficher les valeurs des différents capteurs
    public static TextView RT_temp;
    public static TextView RT_CO2;
    public static TextView RT_humid;
    public static TextView RT_smoke;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



    // Définir le nombre maximal d'éléments dans la liste
    private static int maxListSize = 10;

    private static CollectionReference coordinatesRef = db.collection("users").document(userId).collection("coordinates");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Bienvenue dans ChatFarm", Toast.LENGTH_LONG).show();


        //pour forcer la permission de lecture de SMS
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.BROADCAST_SMS}, PackageManager.PERMISSION_GRANTED);


        //FindViewById :
        editTextNumber = findViewById(R.id.editTextNumber);

        ImageView eraser = findViewById(R.id.eraser);
        ImageView check = findViewById(R.id.check);
        ImageView edit = findViewById(R.id.edit);

        RT_temp=findViewById(R.id.RT_temp);
        RT_CO2=findViewById(R.id.RT_CO2);
        RT_humid=findViewById(R.id.RT_humid);
        RT_smoke=findViewById(R.id.RT_smoke);
        TextView state_smoke=findViewById(R.id.state_smoke);

        Button button_temp = findViewById(R.id.temperature);
        Button button_humid = findViewById(R.id.humidity);
        Button button_CO2 = findViewById(R.id.CO2);
        Button button_relai = findViewById(R.id.relai);



        //Gestion de l'affichage des capteurs

        if (GlobalClass.value_smoke > 115) {
            state_smoke.setText("SMOKE DETECTED");
            state_smoke.setTextColor(Color.RED);
        } else {
            state_smoke.setText("NO SMOKE DETECTED");
            state_smoke.setTextColor(Color.GREEN);
        }


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

        button_humid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur Humidité", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Detail_Humidity_Activity.class);
                startActivity(intent);
            }
        });

        button_relai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Vous avez fait cliqué sur Relai", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), RelaiActivity.class);
                startActivity(intent);
            }
        });
    }

    // Méthode pour ajouter une nouvelle coordonnée
    public static void addCoordinate(double x, double y) {
        // Obtenir toutes les coordonnées de l'utilisateur
        int numdoc=0;
        coordinatesRef.document(""+numdoc);
        coordinatesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                Log.d(TAG, "nbre doc: " + documents.size());
                // Si la liste est pleine (a atteint le nombre maximal d'éléments)
                if (documents.size() >= maxListSize) {

                    // Supprimer la première coordonnée (la plus ancienne)
                    DocumentSnapshot oldestDocument = documents.get(0);
                    coordinatesRef.document(oldestDocument.getId()).delete();
                }

                // Décaler les coordonnées existantes vers la fin de la liste
                for (int i = documents.size() - 1; i > 0; i--) {
                    DocumentSnapshot currentDocument = documents.get(i);
                    DocumentSnapshot previousDocument = documents.get(i - 1);

                    double newX = previousDocument.getDouble("x");
                    double newY = previousDocument.getDouble("y");

                    coordinatesRef.document(currentDocument.getId()).update("x", newX, "y", newY);
                }

                // Ajouter la nouvelle coordonnée à la fin de la liste
                Map<String, Object> newCoordinate = new HashMap<>();
                newCoordinate.put("x", x);
                newCoordinate.put("y", y);

                coordinatesRef.add(newCoordinate)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // La coordonnée a été ajoutée avec succès
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Une erreur s'est produite lors de l'ajout de la coordonnée
                            }
                        });
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

    /* ============================ Menu pour faire retour ====================== */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.actionBack){
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

