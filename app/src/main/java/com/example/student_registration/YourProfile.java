package com.example.student_registration;
import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class YourProfile extends AppCompatActivity {
    FirebaseDatabase mData;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView stname,stschool,roll,dob,stugname,phone,updatepassword;
    ProgressDialog progressDialog;
    EditText upass;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        stname = (TextView) findViewById(R.id.stname);
        stschool = (TextView) findViewById(R.id.stusch);
        roll = (TextView) findViewById(R.id.roll);
        dob = (TextView) findViewById(R.id.dob);
        stugname = (TextView) findViewById(R.id.stugnam);
        phone = (TextView) findViewById(R.id.stuphone);
        updatepassword = (TextView) findViewById(R.id.textUpdate);
        upass = (EditText) findViewById(R.id.updatepass);
        update = (Button) findViewById(R.id.update);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance();
        databaseReference = mData.getReference(mAuth.getUid());

        progressDialog = new ProgressDialog(YourProfile.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Details....");
        progressDialog.show();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                UserProfile userprofile = dataSnapshot.getValue(UserProfile.class);
                stname.setText("Name: "+userprofile.getStudent_Name());
                stschool.setText("School: "+userprofile.getSchool());
                roll.setText("Roll: "+userprofile.getStudent_Roll());
                phone.setText("Phone: "+userprofile.getStudent_Phone());
                dob.setText("Date of Birth: "+userprofile.getStudent_DOB());
                stugname.setText("Gurdian Name: "+userprofile.getGurdian_Name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(YourProfile.this,"Database Error",Toast.LENGTH_LONG).show();
            }
        });

        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upass.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
            }
        });

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating Password....");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                mUser.updatePassword(upass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(YourProfile.this,"Password Updated Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(YourProfile.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(YourProfile.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}