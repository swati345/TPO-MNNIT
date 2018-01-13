package com.example.admin1.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class feedDef extends AppCompatActivity {
    TextView name,branch,year,company,feedbckdef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_def);
        name=(TextView)findViewById(R.id.namef);
        year=(TextView)findViewById(R.id.yearf);
        branch=(TextView)findViewById(R.id.branchf);
        company=(TextView)findViewById(R.id.companyf);
        feedbckdef=(TextView)findViewById(R.id.feedb);
        Intent g= getIntent();
        String category= g.getExtras().getString("category");
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        Query allPost = mDatabaseRef.child("feedback").orderByChild("name").equalTo(category);
        Log.d("harry123",""+mDatabaseRef);
//                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Political_Science/Semester_2/Political_Processes_In_India");
        Log.d("harry123", "Do In back 1");
        allPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()) {
                    String strkey=post.getKey().toString();
                    String str=post.getValue().toString();
                    Log.d("harry123",""+strkey+str);
                    String s[]=str.split(",");
                    int i,j;
                    for(i=0;i<s.length;i++)
                    {
                        int index=s[i].indexOf('=');
                        String st=s[i].substring(index+1);
                        if(i==0)
                            name.setText(st);
                        else if(i==1)
                            feedbckdef.setText(st);
                        else if(i==2)
                            company.setText(st);
                        else if(i==3)
                            branch.setText(st);
                        else
                        {
                            st=st.substring(0,st.length()-1);
                            year.setText(st);
                        }
                    }
                  /*  if(strkey.equalsIgnoreCase("name"))
                        name.setText(str);
                    else if(strkey.equalsIgnoreCase("year"))
                        year.setText(str);
                    else if(strkey.equalsIgnoreCase("branch"))
                        branch.setText(str);
                    else if(strkey.equalsIgnoreCase("company"))
                        company.setText(str);
                    else
                        feedbckdef.setText(str);*/
                }

                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
}
