package com.example.admin1.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin1 on 22-05-2017.
 */
public class picsAdapter extends BaseAdapter{
   private Context iContext;
    private List<pics>Pics;
    public picsAdapter(Context iContext, List<pics> Pics)
    { super();
      this.iContext=iContext;
      this.Pics=Pics;}
    @Override
    public int getCount() {
        return Pics.size();
    }

    @Override
    public Object getItem(int i) {
        return Pics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(iContext,R.layout.pics_list_layout,null);
        TextView l_Name=(TextView)v.findViewById(R.id.pic_category);
        String str=Pics.get(i).getName();
        str=str.replace("_"," ");
        l_Name.setText(str );

        v.setTag(Pics.get(i).getName());
        return v;
    }
}
