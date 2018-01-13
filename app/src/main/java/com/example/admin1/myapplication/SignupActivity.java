package com.example.admin1.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,name,year,branch,cpi,snrschool,address;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    String email,password,addres,getname,getbranch,getsnrschool,temcpi,temyear;
    int getyear;
    double getcpi;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
       name = (EditText) findViewById(R.id.name);
       year = (EditText) findViewById(R.id.year);
       branch = (EditText) findViewById(R.id.Branch);
       cpi = (EditText) findViewById(R.id.cpi);
       snrschool = (EditText) findViewById(R.id.snrsec);
      address = (EditText) findViewById(R.id.a);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 email = inputEmail.getText().toString().trim();
                 password = inputPassword.getText().toString().trim();
                 getname=name.getText().toString();
                temyear=year.getText().toString();

                 getbranch=branch.getText().toString();
                temcpi=cpi.getText().toString();

                 getsnrschool=snrschool.getText().toString();
                 addres=address.getText().toString();
                if(email.length()==0)
                {
                    inputEmail.setError("Email id required");
                    return;
                }
                if(getname.length()==0)
                {
                    name.setError("Name id required");
                    return;
                }
                if(getbranch.length()==0)
                {
                    branch.setError("Branch id required");
                    return;
                }
                if(temcpi.length()==0)
                {
                    cpi.setError("Cpi id required");
                    return;
                }
                if(temyear.length()==0)
                {
                    year.setError("Year id required");
                    return;
                }
                if(password.length()==0)
                {
                    inputPassword.setError("Password id required");
                    return;
                }
                if(getsnrschool.length()==0)
                {
                    snrschool.setError("Phone No. id required");
                    return;
                }
                if(addres.length()==0)
                {
                    address.setError("Registeration no. id required");
                    return;
                }
                if(password.length()<=6)
                {
                    inputPassword.setError("Password should be of minimum 6 characters");
                    return;
                }
                if(getsnrschool.length()!=10)
                {
                    snrschool.setError("Phone no. is of 10 digits");
                    return;
                }
                if(addres.length()!=8)
                {
                    address.setError("Registeration No. should be of 8 digits");
                    return;
                }
                /*

                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getname)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getbranch)) {
                    Toast.makeText(getApplicationContext(), "Enter branch!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(temyear)) {
                    Toast.makeText(getApplicationContext(), "Enter Year!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(temcpi)) {
                    Toast.makeText(getApplicationContext(), "Enter cpi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful())
                                Toast.makeText(SignupActivity.this, "Reister not succesful! Check your information!", Toast.LENGTH_SHORT).show();
                                         progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                           Toast.LENGTH_SHORT).show();
                                } else {


                                    Log.d("harry1234","/Users/"+auth.getCurrentUser().getUid());
                                    DatabaseReference refernce =ref.child("users");

                                    user user1=new user(getname,temyear,getbranch,temcpi,getsnrschool,addres,"https://firebasestorage.googleapis.com/v0/b/mnnit-campus-recruitment.appspot.com/o/profile.png?alt=media&token=8704f33f-4246-4327-947b-a50d2d885823");
                                    refernce.child(auth.getCurrentUser().getUid()).setValue(user1);
                                   // refernce.child(auth.getCurrentUser().getUid()).child("name").setValue("trisha");
                                    startActivity(new Intent(SignupActivity.this, Main2Activity.class));
                                    finish();
                                }
                            }
                        });



            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
