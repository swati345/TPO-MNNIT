package com.example.admin1.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class compDef extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mref;
    private DatabaseReference mDR;
    String regStu;
    private DatabaseReference mr;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    double cpi;
    String branch;
    String name;
    ArrayList<String> studentsRegistered = new ArrayList<>();
    ProgressDialog mProgressDialog;
    Intent n;
    boolean registerable;
    boolean alreadyReg;
    String alreadyPlaced;
    Button reg;
    TextView comp_name, location, stipend, elig, courses,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_def);
        Intent g = getIntent();
        final String category = g.getExtras().getString("category");
        comp_name = (TextView) findViewById(R.id.namep);
        location = (TextView) findViewById(R.id.locationp);
        stipend = (TextView) findViewById(R.id.stipendp);
        profile = (TextView) findViewById(R.id.profilep);
        elig = (TextView) findViewById(R.id.eligp);
        courses = (TextView) findViewById(R.id.coursesp);
        reg = (Button)findViewById(R.id.reg);
        comp_name.setText(category);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("Location");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String str = dataSnapshot.getValue().toString();
                Log.d("harry1234","str.."+str);
                location.setText(str);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("Profile");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String str = dataSnapshot.getValue().toString();
                Log.d("harry1234","str.."+str);
                profile.setText(str);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("Eligibility");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String str = dataSnapshot.getValue().toString();
                Log.d("harry1234","str.."+str);
                elig.setText(str);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("Stipend");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String str = dataSnapshot.getValue().toString();
                Log.d("harry1234","str.."+str);
                stipend.setText(str);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("Courses");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String str = dataSnapshot.getValue().toString();
                Log.d("harry1234","str.."+str);
                courses.setText(str);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

/*
        mDR = FirebaseDatabase.getInstance().getReference("").child("users").child(auth.getCurrentUser().getUid());
        mDR.keepSynced(true);//FirebaseDatabase.getInstance().getReference().child("users").child(category).child("check");

        mDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                Log.d("deboxx",str);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("check");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                if(str.equals("true"))
                {
                    registerable = false;
                    //Toast.makeText(compDef.this, "Registration Closed", Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerable = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(registerable)
                {
                    //Toast.makeText(compDef.this, "Registered", Toast.LENGTH_LONG).show();
                    auth = FirebaseAuth.getInstance();
                    mr = FirebaseDatabase.getInstance().getReference("").child("users").child(auth.getCurrentUser().getUid());
                    mr.keepSynced(true);
                    mr.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue().toString();
                            Log.d("debo55",str);

                            for(int i = 0; i < (str.length()-4);i++)
                            {
                                String s = str.substring(i, i+4);
                                if(s.equals("cpi=")) {
                                    Log.d("debo555", "yo" + i);
                                    int pos = str.indexOf(',',i);
                                    String str1=str.substring(i+4,pos);
                                    cpi=Double.parseDouble(str1);
                                    Log.d("debo5555", "yoo" + cpi);
                                }
                            }

                            for(int i = 0; i < (str.length()-7);i++)
                            {
                                String s = str.substring(i, i+7);
                                if(s.equals("branch=")) {
                                    Log.d("debo66", "yooo" + i);
                                    int pos = str.indexOf(',',i);
                                    branch=str.substring(i+7,pos);
                                    Log.d("debo666", "yoooo" + branch);
                                }
                            }

                            for(int i = 0; i < (str.length()-5);i++)
                            {
                                String s = str.substring(i, i+5);
                                if(s.equals("name=")) {
                                    Log.d("debo66", "yooo" + i);
                                    int pos = str.indexOf(',',i);
                                    if(pos>=0)
                                        name=str.substring(i+5,pos);
                                    else
                                        name=str.substring(i+5,str.length()-1);
                                    Log.d("debo666", "yoooo" + name);
                                }
                            }

                            for(int i = 0; i < (str.length()-6);i++)
                            {
                                String s = str.substring(i, i+6);
                                if(s.equals("check=")) {
                                    Log.d("debozz", "yooo" + i);
                                    int pos = str.indexOf(',',i);
                                    if(pos>=0)
                                        alreadyPlaced=str.substring(i+6,pos);
                                    else
                                        alreadyPlaced=str.substring(i+6,str.length()-1);
                                    Log.d("debozzz", "yoooo" + alreadyPlaced);
                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    ref = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("registrations");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            regStu = dataSnapshot.getValue().toString();
                            Log.d("debo987",regStu);
                            String[] sr = regStu.split("QQ");
                            for(int i = 0; i < sr.length; i++)
                            {
                                studentsRegistered.add(sr[i]);
                                if(name.equalsIgnoreCase(sr[i]))
                                {
                                    alreadyReg=true;
                                }
                                else
                                {
                                    alreadyReg=false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    mref = FirebaseDatabase.getInstance().getReference().child("companies").child(category).child("cutoff");
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue().toString();
                            Log.d("debo44",str);
                            int count2=0;

                            for(int i = 0; i < (str.length()-4);i++)
                            {
                                String s = str.substring(i, i+4);
                                if(s.equals("all=")) {
                                    count2++;
                                    Log.d("debo99", "yooo" + i);
                                    //int pos = str.indexOf(i+5);
                                    String ccc=str.substring(i+4,str.length()-1);
                                    Log.d("debo999", "yoooo" + ccc);
                                    double cccc=Double.parseDouble(ccc);
                                    if(alreadyPlaced.equalsIgnoreCase("false")) {
                                        if (cpi >= cccc) {
                                            if (alreadyReg == false) {
                                                alreadyReg = true;
                                                regStu = regStu + "QQ" + name;
                                                Log.d("debo525", regStu);
                                                ref.setValue(regStu);
                                                Toast.makeText(compDef.this, "You have successfully registered", Toast.LENGTH_LONG).show();
                                            } else {
                                                Log.d("debo5255", regStu);
                                                Toast.makeText(compDef.this, "You have ALREADY registered", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(compDef.this, "You are NOT eligible", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    }
                                    else
                                    {
                                        Toast.makeText(compDef.this, "You are NOT eligible as you are ALREADY placed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            if(count2==0) {
                                int count = 0;
                                String temp1 = branch + "=";
                                for (int i = 0; i < (str.length() - (temp1.length())); i++) {
                                    String s = str.substring(i, i + (temp1.length()));
                                    if (temp1.equalsIgnoreCase(s)) {
                                        count++;
                                        Log.d("debo88", "yooo" + i);
                                        int pos = str.indexOf(',', i);
                                        String c;
                                        if(pos>=0)
                                            c = str.substring(i + (temp1.length()), pos);
                                        else
                                            c = str.substring(i+(temp1.length()),str.length()-1);
                                        //cpi=Double.parseDouble(str1);
                                        Log.d("debo888", "yoooo" + c);
                                        double cc = Double.parseDouble(c);
                                        if(alreadyPlaced.equalsIgnoreCase("false")) {
                                            if (cpi >= cc) {

                                                if (alreadyReg == false) {
                                                    alreadyReg = true;
                                                    regStu = regStu + "QQ" + name;
                                                    Log.d("debo525", regStu);
                                                    ref.setValue(regStu);
                                                    Toast.makeText(compDef.this, "You have successfully registered", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Log.d("debo5255", regStu);
                                                    Toast.makeText(compDef.this, "You have ALREADY registered", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(compDef.this, "You are NOT eligible", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(compDef.this, "You are NOT eligible as you are ALREADY placed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                if (count == 0) {
                                    Toast.makeText(compDef.this, "You are NOT eligible", Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(compDef.this, "Registration Closed", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}