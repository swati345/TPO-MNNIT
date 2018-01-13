package com.example.admin1.myapplication;

/**
 * Created by admin1 on 07-10-2017.
 */
public class feed {
    String Name;
    String Year;
    String Branch;
    String company;
    String feedback;
    public feed()
    {}
    public feed(String name,String year,String branch,String feedb,String comp)
    {
        Name=name;
        Year=year;
        Branch=branch;
        feedback=feedb;
        company=comp;
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
    public String getfeed(){ return feedback;}
    public String getCompany(){return company;}
}
