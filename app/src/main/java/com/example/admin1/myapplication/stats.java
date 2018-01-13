package com.example.admin1.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link stats.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link stats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stats extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BarChart bc,bc2;
    private XAxis xAxis;
    String br,brn;
    int [] cStu={0,0,0,0,0,0,0,-1,0,0};
    int [] totalCStu = {0,0,0,0,0,0,0,-1,0,0};
    private DatabaseReference mDatabaseRef,database;
    final HashMap<Integer, String>numMap = new HashMap<>();
    private ArrayList<String>labels=new ArrayList<>();
    private ArrayList<String>labels2=new ArrayList<>();
    private ArrayList<BarEntry> be = new ArrayList<>();
    private ArrayList<BarEntry> be2 = new ArrayList<>();
    String[] st;
    String av,to,av2,to2;
    int i,val,sum=0;
    int sum2;
    private OnFragmentInteractionListener mListener;
    private TextView totalt,averaget,totalt2,averaget2;
    public stats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stats.
     */
    // TODO: Rename and change types and number of parameters
    public static stats newInstance(String param1, String param2) {
        stats fragment = new stats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //((Main2Activity)getActivity()).getSupportActionBar().setTitle("Statistics");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_stats, container, false);
        totalt=(TextView)rootView.findViewById(R.id.total);
        averaget=(TextView)rootView.findViewById(R.id.average);
        bc = (BarChart)rootView.findViewById(R.id.bc);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Log.d("debo19",""+ref);
        // Query for all entries with a certain child with value equal to something
        Query allPostFromAuthor = ref.child("companies").orderByChild("check").equalTo(true);
        Log.d("debo15",""+allPostFromAuthor);
        // Add listener for Firebase response on said query

        allPostFromAuthor.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count=0;

                Log.d("debo16",""+dataSnapshot);
                for(DataSnapshot post : dataSnapshot.getChildren() ){
                    Log.d("debo17",""+post);
                    String str = post.getValue().toString();
                    String strkey=post.getKey().toString();
                    Log.d("debodd",""+strkey);

                    for(int i = 0; i < (str.length()-6);i++)
                    {
                        String s = str.substring(i, i+6);
                        if(s.equals("count=")) {
                            Log.d("debo33", "yo" + i);
                            String str1=str.substring(i+6,i+7);
                            String str2=str.substring(i+7,i+8);
                            if(str2.equals(",")){
                                Log.d("debo333",""+str);
                                Log.d("debo3333",""+str1);
                                Log.d("debo33333",""+str2);
                                // Iterate through all posts with the same author
                                val=Integer.parseInt(str1);}
                            else
                            {int val1=Integer.parseInt(str1);
                                int val2=Integer.parseInt(str2);
                                val=val1*10+val2;

                            }
                        }
                    }
                    sum=sum+val;
                    be.add(new BarEntry(count,val));
                    labels.add(strkey.trim().replace('_',' '));
                    count++;

                }
                BarDataSet bds= new BarDataSet(be,"Count of students");
                Log.d("harry123",""+labels);
                //bds.setBarWidth(10f);

                BarData data = new BarData(bds);
                bc.getXAxis().setGranularity(1);
                bc.getXAxis().setGranularityEnabled(true);
                bc.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                /*xAxis = bc.getXAxis();
                xAxis.setValueFormatter(new AxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {

                        return numMap.get((int)value);
                    }

                    @Override
                    public int getDecimalDigits() {
                        return 0;
                    }
                });*/
                //data.setBarWidth(2f);
                bc.setData(data);

                bc.setDragEnabled(true);
                bc.setTouchEnabled(true);
                bc.getDescription().setText("");
                bds.setColors(ColorTemplate.COLORFUL_COLORS);
                bc.setScaleEnabled(true);
                to=String.valueOf(sum);
                totalt.setText(to);
                double aver=sum/1000.0;
                av=String.valueOf(aver);
                averaget.setText(av);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        DatabaseReference rr = FirebaseDatabase.getInstance().getReference();
        Log.d("debo19",""+rr);
        Query allPostFromAuthor3 = rr.child("users");
        allPostFromAuthor3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren() )
                {
                    //int count2=0;
                    Log.d("debo75",""+post);
                    String str = post.getValue().toString();
                    Log.d("debo75",""+str);
                    for(int i = 0; i < (str.length()-7);i++)
                    {
                        String s = str.substring(i, i+7);
                        if(s.equals("branch=")) {
                            Log.d("debo66", "yooo" + i);
                            int pos = str.indexOf(',',i);
                            if(pos>=0)
                                brn=str.substring(i+7,pos);
                            else
                                brn=str.substring(i+7,str.length()-1);
                            Log.d("debo666", "yoooo" + brn);
                        }
                    }
                    if(brn.equalsIgnoreCase("biotech")||brn.equalsIgnoreCase("BT")||brn.equalsIgnoreCase("Biotechnology"))
                    {
                        totalCStu[0]++;
                    }
                    else if(brn.equalsIgnoreCase("civil")||brn.equalsIgnoreCase("civ"))
                    {
                        totalCStu[1]++;
                    }
                    else if(brn.equalsIgnoreCase("electrical")||brn.equalsIgnoreCase("EE"))
                    {
                        totalCStu[2]++;
                    }
                    else if(brn.equalsIgnoreCase("mech")||brn.equalsIgnoreCase("Mechanical"))
                    {
                        totalCStu[3]++;
                    }
                    else if(brn.equalsIgnoreCase("CSE")||brn.equalsIgnoreCase("CS")||brn.equalsIgnoreCase("Computer"))
                    {
                        totalCStu[4]++;
                    }
                    else if(brn.equalsIgnoreCase("ECE")||brn.equalsIgnoreCase("Electronics")||brn.equalsIgnoreCase("EC"))
                    {
                        totalCStu[5]++;
                    }
                    else if(brn.equalsIgnoreCase("PIE")||brn.equalsIgnoreCase("Production"))
                    {
                        totalCStu[6]++;
                    }
                    else if(brn.equalsIgnoreCase("IT")||brn.equalsIgnoreCase("Information Technology")||brn.equalsIgnoreCase("Information")) {
                        totalCStu[8]++;
                    }
                    else if(brn.equalsIgnoreCase("Chem")||brn.equalsIgnoreCase("Chemical")||brn.equalsIgnoreCase("CHE"))
                    {
                        totalCStu[9]++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        totalt2=(TextView)rootView.findViewById(R.id.total2);
        averaget2=(TextView)rootView.findViewById(R.id.average2);
        bc2 = (BarChart)rootView.findViewById(R.id.bc2);
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference();
        Log.d("debo19",""+refer);
        Query allPostFromAuthor2 = ref.child("users").orderByChild("check").equalTo(true);
        Log.d("debo33",""+allPostFromAuthor2);
        allPostFromAuthor2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren() )
                {
                    //int count2=0;
                    Log.d("debo75",""+post);
                    String str = post.getValue().toString();
                    //String strkey=post.getKey().toString();
                    Log.d("debo75",""+str);
                    for(int i = 0; i < (str.length()-7);i++)
                    {
                        String s = str.substring(i, i+7);
                        if(s.equals("branch=")) {
                            Log.d("debo66", "yooo" + i);
                            int pos = str.indexOf(',',i);
                            if(pos>=0)
                                br=str.substring(i+7,pos);
                            else
                                br=str.substring(i+7,str.length()-1);
                            Log.d("debo666", "yoooo" + br);
                        }
                    }
                    if(br.equalsIgnoreCase("biotech")||br.equalsIgnoreCase("BT")||br.equalsIgnoreCase("Biotechnology"))
                    {
                        cStu[0]++;
                    }
                    else if(br.equalsIgnoreCase("civil")||br.equalsIgnoreCase("civ"))
                    {
                        cStu[1]++;
                    }
                    else if(br.equalsIgnoreCase("electrical")||br.equalsIgnoreCase("EE"))
                    {
                        cStu[2]++;
                    }
                    else if(br.equalsIgnoreCase("mech")||br.equalsIgnoreCase("Mechanical"))
                    {
                        cStu[3]++;
                    }
                    else if(br.equalsIgnoreCase("CSE")||br.equalsIgnoreCase("CS")||br.equalsIgnoreCase("Computer"))
                    {
                        cStu[4]++;
                    }
                    else if(br.equalsIgnoreCase("ECE")||br.equalsIgnoreCase("Electronics")||br.equalsIgnoreCase("EC"))
                    {
                        cStu[5]++;
                    }
                    else if(br.equalsIgnoreCase("PIE")||br.equalsIgnoreCase("Production"))
                    {
                        cStu[6]++;
                    }
                    else if(br.equalsIgnoreCase("IT")||br.equalsIgnoreCase("Information Technology")||br.equalsIgnoreCase("Information")) {
                        cStu[8]++;
                    }
                    else if(br.equalsIgnoreCase("Chem")||br.equalsIgnoreCase("Chemical")||br.equalsIgnoreCase("CHE"))
                    {
                        cStu[9]++;
                    }

                    //be2.add(new BarEntry(count2,val));
                    //count2++;
                }
                labels2.add("BT");
                labels2.add("Civil");
                labels2.add("EE");
                labels2.add("ME");
                labels2.add("CSE");
                labels2.add("ECE");
                labels2.add("PIE");
                labels2.add("IT");
                labels2.add("CHE");

                be2.add(new BarEntry(0,cStu[0]));
                be2.add(new BarEntry(1,cStu[1]));
                be2.add(new BarEntry(2,cStu[2]));
                be2.add(new BarEntry(3,cStu[3]));
                be2.add(new BarEntry(4,cStu[4]));
                be2.add(new BarEntry(5,cStu[5]));
                be2.add(new BarEntry(6,cStu[6]));
                be2.add(new BarEntry(7,cStu[8]));
                be2.add(new BarEntry(8,cStu[9]));

                BarDataSet bds2= new BarDataSet(be2,"Count of students");
                Log.d("harry123",""+labels2);
                //bds.setBarWidth(10f);

                BarData data2 = new BarData(bds2);
                bc2.getXAxis().setGranularity(1);
                bc2.getXAxis().setGranularityEnabled(true);
                bc2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels2));

                bc2.setData(data2);
                bc2.setDragEnabled(true);
                bc2.setTouchEnabled(true);
                bc2.getDescription().setText("");
                bds2.setColors(ColorTemplate.COLORFUL_COLORS);
                bc2.setScaleEnabled(true);
                to2=String.valueOf(sum);
                totalt2.setText(to2);
                double per[]=new double[10];
                double sum2=0;
                for(int i = 0 ; i <totalCStu.length;i++) {
                    if (i == 7)
                        per[i] = 0;
                    else if(totalCStu[i]!=0)
                        per[i] = cStu[i] / totalCStu[i];
                    else
                        per[i]=0;
                    sum2 += per[i];
                }
                double aver2=sum2/(per.length-1);
                av2=String.valueOf(aver2);
                averaget2.setText(av2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
