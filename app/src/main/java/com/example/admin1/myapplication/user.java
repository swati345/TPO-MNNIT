package com.example.admin1.myapplication;

import android.icu.text.DateFormat;

/**
 * Created by admin1 on 26-09-2017.
 */
public class user {
    String Name;
    String Year;
    String Branch;
    String cpi;
    String Phone;
    String Reg;
    String pic;
    Boolean checkval;

    public user() {
    }

    public user(String name, String year, String branch, String cpi, String phone, String reg,String url) {
        this.Name = name;
        this.Year = year;
        this.Branch = branch;
        this.cpi=cpi;
        this.Phone=phone;
        this.Reg=reg;
        pic=url;
        checkval=false;
    }

    public String getName() {
        return Name;
    }

    public String getYear() {
        return Year;
    }

    public String getBranch() {
        return Branch;
    }

    public String getcpi(){return cpi;}

    public String getPhone(){return Phone;}
    public String getReg() {return Reg;}
    public String getpic(){return pic;}
    public boolean getcheck(){ return checkval;}
}

