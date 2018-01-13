package com.example.admin1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Upcompany.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Upcompany#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upcompany extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //
    //
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tv=null;
    private ListView listView;
    private com.example.admin1.myapplication.picsAdapter picsadapter;
    private List<pics> piclist;
    private DatabaseReference mDatabaseRef;
    ProgressDialog mProgressDialog;
    Intent n;
    private OnFragmentInteractionListener mListener;

    public Upcompany() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Upcompany.
     */
    // TODO: Rename and change types and number of parameters
    public static Upcompany newInstance(String param1, String param2) {
        Upcompany fragment = new Upcompany();
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
        //((Main2Activity)getActivity()).getSupportActionBar().setTitle("Companies");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview;
         rootview= inflater.inflate(R.layout.fragment_upcompany, container, false);
        Log.d("harry123", "start second Activity");
        listView = (ListView)rootview.findViewById(R.id.list_view_for_courses);

        piclist = new ArrayList();

        Log.d("harry123", "Update onPreExecute start");
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading....");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

        try {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("companies");

//                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Political_Science/Semester_2/Political_Processes_In_India");
            Log.d("harry123", "Do In back 1");
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<String> stm = new ArrayList<>();

                    for(DataSnapshot post : dataSnapshot.getChildren())
                    {
                        Log.d("debo12", "" + post);
                        String strkey=post.getKey().toString();
                        String str = post.getValue().toString();
                        Log.d("debo13", "" + strkey);
                        Log.d("debo14", "" + str);
                        piclist.add(new pics(strkey.trim()));
                        picsadapter = new picsAdapter(getActivity(), piclist);
                        listView.setAdapter(picsadapter);
                        stm.add(strkey.trim());

                    }


                   /* String str = dataSnapshot.getValue().toString();
                    Log.d("debo3","object------>"+str);
                    Log.d("harry12345","Retriving value....");
                    String str1 = str.replaceAll("\\[(.*?)\\]", "");
                    Log.d("debo4","String--"+str1);
                    int pos = str1.indexOf('=');
                    String str2=str1.substring(0,pos);
                    Log.d("debo9","String--"+str2);
                    str1 = str1.replaceAll("=", "");
                    System.out.println(str1);
                    ArrayList<String> stm = new ArrayList<>();
                    str1 = str1.replaceAll("\\{[^{}]*\\}", "");
                    //str1 = str1.replaceAll("\\{[^{}]*\\}", "");
                    System.out.println(str1);
                    str1 = str1.substring(1, str1.length()-1);
                    System.out.println(str1);
                    Log.d("debo5","String--"+str1);
                    String[] st = str1.split(",");
                    Log.d("debo6","String--"+st[0]);
                    for(int i=0;i<st.length;i++)
                    {

                        Log.d("debo7",st[i].trim());
                        String s[] = st[i].split("=");
                        Log.d("debo8",s[0].trim());
                        piclist.add(new pics(st[i].trim()));
                        picsadapter = new picsAdapter(getActivity(), piclist);
                        listView.setAdapter(picsadapter);*/
                        /*if(st[i].indexOf('{')>=0)
                        {
                            String strf=st[i].substring(0,(st[i].indexOf('{'))-1);
                            Log.d("debo10",strf.trim());
                            stm.add(strf.trim());
                        }*/
                        //stm.add(st[i].trim());
                    //}
                    mProgressDialog.dismiss();
                }




                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
        } catch (Exception e) {
//             	b.setText("title");
            System.out.println("title2 : ");
            e.printStackTrace();
        }
        picsadapter = new picsAdapter(getActivity(),piclist);
        listView.setAdapter(picsadapter);

        n = new Intent(getActivity(), compDef.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // Toast.makeText(getActivity(), "Clicked product id "+view.getTag(), Toast.LENGTH_SHORT).show();
//                Intent n = new Intent(this, Main3Activity.class);
                n.putExtra("category", view.getTag().toString());
                Log.d("harry123_audioFileName", view.getTag().toString());

                startActivity(n);
            }


        });
        return rootview;
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
