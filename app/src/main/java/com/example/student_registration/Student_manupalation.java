package com.example.student_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Student_manupalation extends AppCompatActivity {

    TextView disname,disroll;

    FirebaseAuth mAuth;
    FirebaseDatabase mData;
    DatabaseReference databaseReference;
    CardView yprofile,update;
    ProgressDialog progressDialog;
    ImageView iconrem;
    String rem;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manupalation);

        disname = (TextView) findViewById(R.id.displayname);
        disroll = (TextView) findViewById(R.id.displayroll);
        iconrem = (ImageView) findViewById(R.id.iconrem);
        yprofile = (CardView) findViewById(R.id.yprofile);
        update = (CardView) findViewById(R.id.updatedetails);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(calendar.DAY_OF_MONTH,7);
        snackbar = Snackbar.make(coordinatorLayout,"Please come with your parents on: "+formatter.format(calendar.getTime()),Snackbar.LENGTH_INDEFINITE);
        progressDialog = new ProgressDialog(Student_manupalation.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Details....");
        progressDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_manupalation.this,update_info.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        databaseReference = mData.getReference(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                UserProfile pro = dataSnapshot.getValue(UserProfile.class);
                disname.setText("Name: " + pro.getStudent_Name());
                disroll.setText("Roll: " + pro.getStudent_Roll());
                rem = pro.getRem();
                if(rem.equals("1")){
                    snackbar.show();
                    iconrem.setImageResource(R.drawable.bad);
                }
                else if(rem.equals("2"))
                {
                    snackbar.dismiss();
                    iconrem.setImageResource(R.drawable.good);
                }
                else
                {
                    snackbar.dismiss();
                    iconrem.setImageResource(R.drawable.great);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(Student_manupalation.this,"Database Error",Toast.LENGTH_LONG).show();

            }
        });

        yprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_manupalation.this,YourProfile.class);
                startActivity(intent);
            }
        });
    }
}