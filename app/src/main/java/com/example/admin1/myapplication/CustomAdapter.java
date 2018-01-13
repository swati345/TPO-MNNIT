package com.example.admin1.myapplication;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User-PC on 09-10-2017.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayList<String> names;
    ArrayList<String> depts;
    ArrayList<String> phnos;
    Context iContext;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Context iContext, ArrayList<String> nameList, ArrayList<String> deptList, ArrayList<String> phnos) {
        // TODO Auto-generated constructor stub
        super();
        names=nameList;
        depts=deptList;
        this.phnos=phnos;
        this.iContext=iContext;
        inflater = ( LayoutInflater )iContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //public class Holder
    //{
        TextView tv1;
        TextView tv2;
        ImageView img;
    //}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //Holder holder=new Holder();
        final View rowView = View.inflate(iContext, R.layout.list_items, null);
        tv1=(TextView) rowView.findViewById(R.id.name);
        tv2=(TextView) rowView.findViewById(R.id.dept);
        img=(ImageView) rowView.findViewById(R.id.call);
        tv1.setText(names.get(position));
        tv2.setText(depts.get(position));
        img.setImageResource(R.drawable.ic_call_black_15dp);
        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(iContext, "You Clicked "+names.get(position), Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String s = "tel:"+phnos.get(position);
                callIntent.setData(Uri.parse(s));

                if (ActivityCompat.checkSelfPermission(iContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });*/
        /*holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9871203948"));

                if (ActivityCompat.checkSelfPermission(iContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });*/
        return rowView;
    }

}
