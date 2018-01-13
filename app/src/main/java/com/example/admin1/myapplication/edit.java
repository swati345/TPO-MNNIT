package com.example.admin1.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.logging.Logger;

public class edit extends AppCompatActivity {
    private Button buttonChoose, buttonUpload,buttonapply;
    private ImageView imageView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final int SELECT_PHOTO = 100;
    Uri selectedImage;

    FirebaseStorage storage;
    EditText namet,yeart,brancht,schoolt,cpit,addresst;
    DatabaseReference mDatabaseRef;
    StorageReference storageRef, imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    private FirebaseAuth auth;


    Uri downloadUrl;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        storage = FirebaseStorage.getInstance();
        //creates a storage reference
        storageRef = storage.getReference();
        buttonChoose = (Button)findViewById(R.id.choose);
        buttonUpload = (Button)findViewById(R.id.upload);
        namet=(EditText)findViewById(R.id.name);
        yeart=(EditText)findViewById(R.id.year);
        brancht=(EditText)findViewById(R.id.branch);
        cpit=(EditText)findViewById(R.id.cpi);
        schoolt=(EditText)findViewById(R.id.school);
        addresst=(EditText)findViewById(R.id.address);
        buttonapply=(Button)findViewById(R.id.apply);
        buttonapply.setEnabled(false);
        Intent g = getIntent();
        category = g.getExtras().getString("category");
        imageView = (ImageView)findViewById(R.id.imageview2);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("").child("users").child(category);

        //mDatabaseRef.keepSynced(true);
        // StorageReference refence=ref.child("users_image").child(auth.getCurrentUser().getUid());
        buttonUpload.setEnabled(false);
        Log.d("harry123", "Do In back 1");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot post : dataSnapshot.getChildren() ) {
                    Log.d("harry123", "" + post);
                    String strkey=post.getKey().toString();
                    String str = post.getValue().toString();
                    Log.d("harry123",""+strkey+" "+str);
                    if(strkey.equals("name"))
                        namet.setText(str);
                    else if(strkey.equals("year"))
                        yeart.setText(str);
                    else if(strkey.equals("branch"))
                        brancht.setText(str);
                    else if(strkey.equals("cpi"))
                        cpit.setText(str);
                    else if(strkey.equals("phone"))
                        schoolt.setText(str);
                    else if(strkey.equals("reg"))
                        addresst.setText(str);
                    else if(strkey.equals("pic"))
                    {  downloadUrl=Uri.parse(str);
                        Picasso.with(getApplicationContext())
                                .load(str)
                                .fit()
                                .into(imageView);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        //ref.child("name").setValue("pleasseee");




        //attaching listener
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                buttonUpload.setEnabled(true);
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonapply.setEnabled(true);
                imageRef = storageRef.child("users_images/" + selectedImage.getLastPathSegment());
                //creating and showing progress dialog
                progressDialog = new ProgressDialog(edit.this);
                progressDialog.setMax(100);
                progressDialog.setMessage("Uploading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //starting upload
                uploadTask = imageRef.putFile(selectedImage);
                // Observe state change events such as progress, pause, and resume
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //sets and increments value of progressbar
                        progressDialog.incrementProgressBy((int) progress);
                    }
                });
                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(edit.this, "Error in uploading!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(edit.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //showing the uploaded image in ImageView using the download url
                        Picasso.with(edit.this).load(downloadUrl).into(imageView);
                    }
                });
            }
        });
        //ref.child("branch").setValue("pleasseee");
        //ref.child("pic").setValue(downloadUrl);
        //ref.child("name").setValue("ok");
        namet.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        brancht.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        yeart.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        cpit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                if(!buttonapply.isEnabled())
                    buttonapply.setEnabled(true);
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        schoolt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        addresst.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!buttonapply.isEnabled())
                buttonapply.setEnabled(true);
            }
        });
        if(namet.getText().toString().length()==0||yeart.getText().toString().length()==0||brancht.getText().toString().length()==0||cpit.getText().toString().length()==0||schoolt.getText().toString().length()==0||addresst.getText().toString().length()==0||addresst.getText().toString().length()!=8||schoolt.getText().toString().length()!=10)
        {   //Toast.makeText(this,"Fill all the fields ! ",Toast.LENGTH_SHORT).show();
            buttonapply.setEnabled(false);
        }
            //buttonapply.setEnabled(true);
        buttonapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child("pic").setValue(downloadUrl.toString());
                mDatabaseRef.child("name").setValue(namet.getText().toString());
                mDatabaseRef.child("year").setValue(yeart.getText().toString());
                mDatabaseRef.child("branch").setValue(brancht.getText().toString());
                mDatabaseRef.child("cpi").setValue(cpit.getText().toString());
                mDatabaseRef.child("phone").setValue(schoolt.getText().toString());
                mDatabaseRef.child("reg").setValue(addresst.getText().toString());
                startActivity(new Intent(edit.this,Main2Activity.class));
                finish();
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(edit.this, "Image selected, click on upload button", Toast.LENGTH_SHORT).show();
                    selectedImage = imageReturnedIntent.getData();
                }
        }
    }


}
