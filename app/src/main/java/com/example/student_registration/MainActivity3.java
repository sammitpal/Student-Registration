package com.example.student_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    private ImageView datebtn;
    private int mYear, mMonth, mDay;
    EditText date,school,gname,address,ph;
    FirebaseAuth mAuth;
    FirebaseDatabase mdata;
    DatabaseReference mRef;
    Button register;
    EditText person;
    String nam,phone,adr,gnam,db,sch,datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        datebtn = (ImageView) findViewById(R.id.dtbtn);
        date = (EditText) findViewById(R.id.date);
        school = (EditText) findViewById(R.id.school);
        person = (EditText) findViewById(R.id.name);
        gname = (EditText) findViewById(R.id.gname);
        address = (EditText) findViewById(R.id.address);
        ph = (EditText) findViewById(R.id.ph);
        register = (Button) findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity3.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(school.getText().length()!=0 && person.getText().length()!=0 && gname.getText().length()!=0 && ph.getText().length()!=0 && address.getText().length()!=0) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmss");
                    datetime = formatter.format(calendar.getTime());
                    nam = person.getText().toString();
                    phone = ph.getText().toString();
                    adr = address.getText().toString();
                    gnam = gname.getText().toString();
                    db = date.getText().toString();
                    sch = school.getText().toString();

                    mdata = FirebaseDatabase.getInstance();
                    mRef = mdata.getReference();
                    UserProfile pro = new UserProfile(nam, gnam, sch, datetime, db, phone, "3");
                    mRef.child(mAuth.getUid()).setValue(pro);
                    Toast.makeText(MainActivity3.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity3.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity3.this,"Field Empty",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}