package com.example.sensors_m2.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensors_m2.MainActivity;
import com.example.sensors_m2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText inputEmail,inputPassword,createAccount,confirmPassword;
    Button btnRegister;
    TextView alreadyUser;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        alreadyUser=findViewById(R.id.alreadyUser);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String checkPassword=confirmPassword.getText().toString();

        if (!password.equals(checkPassword)) {
            confirmPassword.setError("Password mismatch");
        }
        else if (password.isEmpty()||password.length()<6) {
            confirmPassword.setError("Password empty or length<6, please correct");
        }
        else {
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            //on ajoute à la BDD Firebase les champs remplis par l'utilisateur
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        //partie qui passe à l'activité suivante après connexion successfull
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
