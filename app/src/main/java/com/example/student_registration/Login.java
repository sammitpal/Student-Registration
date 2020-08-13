package com.example.student_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText lemail,lpass;
    Button login;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lemail = (EditText) findViewById(R.id.lemail);
        lpass = (EditText) findViewById(R.id.lpass);
        login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait...");

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lemail.getText().length() != 0 && lpass.getText().length() != 0)
                {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(lemail.getText().toString(), lpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                checkEmailVerification();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this,"Field Empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Boolean ef = firebaseUser.isEmailVerified();
        if(ef)
        {
            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this,Student_manupalation.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(Login.this,"Verify your email",Toast.LENGTH_LONG).show();
        }
    }
}