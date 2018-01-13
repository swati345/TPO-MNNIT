package com.example.admin1.myapplication;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by admin1 on 27-09-2017.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
