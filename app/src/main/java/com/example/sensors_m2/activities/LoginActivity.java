package com.example.sensors_m2.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensors_m2.GlobalClass;
import com.example.sensors_m2.MainActivity;
import com.example.sensors_m2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    private TextView createAccount;
    private Button btnConnect;
    public static EditText inputEmail,inputPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    public static FirebaseUser mUser;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GlobalClass.isUserLoggedIn=false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputEmail=findViewById(R.id.inputEmail);
        progressDialog=new ProgressDialog(this);
        inputPassword=findViewById(R.id.inputPassword);
        createAccount=findViewById(R.id.createAccount);
        btnConnect=findViewById(R.id.btnConnect);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforConnect();

            }
        });

    }

    private void perforConnect() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        System.out.println(email);
        System.out.println(password);
        progressDialog=new ProgressDialog(this);

        if (password.isEmpty() || email.isEmpty()){
            inputPassword.setError("Password or ID mismatch");
        }

        else {
            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        GlobalClass.isUserLoggedIn=true;
                        progressDialog.dismiss();

                        //partie qui passe à l'activité suivante après connexion successfull
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onDebugButtonClick(View view) {
        String fakeEmail = "debug@debug.com";
        inputEmail.setText(fakeEmail);
        String fakePassword = "debug123";
        System.out.println(fakeEmail);
        System.out.println(fakePassword);
        progressDialog=new ProgressDialog(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(fakeEmail, fakePassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    GlobalClass.isUserLoggedIn=true;
                    progressDialog.dismiss();

                    //partie qui passe à l'activité suivante après connexion successfull
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}




