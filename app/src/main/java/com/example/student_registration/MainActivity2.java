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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    EditText email;
    EditText password,cpassword;
    Button cont;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        email = (EditText) findViewById(R.id.email);
        cont = (Button) findViewById(R.id.verify);
        password = (EditText) findViewById(R.id.pass);
        cpassword = (EditText) findViewById(R.id.cpass);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity2.this);
        progressDialog.setTitle("Creating your profile");
        progressDialog.setMessage("Please Wait.....");

        if (password.getText().toString().equals(cpassword.getText().toString())) {
            cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email.getText().length() != 0 && password.getText().length() != 0) {
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    sendemailverification();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity2.this,"Field Empty!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    private void sendemailverification()
    {
        FirebaseUser mUser = mAuth.getInstance().getCurrentUser();
        mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity2.this,"Verification mail sent",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity2.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}