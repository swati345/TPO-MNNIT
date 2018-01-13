package com.example.admin1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
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
 * {@link Feedback.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView editname;
    private FirebaseAuth auth;
    TextView edityear;
    EditText editidea;
    private ListView listView;
    private com.example.admin1.myapplication.picsAdapter picsadapter;
    private List<pics> piclist;
    ProgressDialog mProgressDialog;
    EditText editcomp;
    TextView companyt;
    Intent n;
    TextView branch;
    Button submit,addf;
    String getbranch,getyear,getname;
    TextView namet,yeart,brancht,feedt;
    int isflag=0;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("");
    int is=0,isa=0;
    private int checkval=0;
    private OnFragmentInteractionListener mListener;

    public Feedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static Feedback newInstance(String param1, String param2) {
        Feedback fragment = new Feedback();
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

        }//((Main2Activity)getActivity()).getSupportActionBar().setTitle("Feedback");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_feedback, container, false);
        editname=(TextView)rootView.findViewById(R.id.editTextName);
        branch=(TextView)rootView.findViewById(R.id.branch);
        submit=(Button)rootView.findViewById(R.id.buttonSave);
        addf=(Button)rootView.findViewById(R.id.Add);
        edityear=(TextView)rootView.findViewById(R.id.year);
        editidea=(EditText)rootView.findViewById(R.id.idea);
        namet=(TextView)rootView.findViewById(R.id.nametext);
        editcomp=(EditText)rootView.findViewById(R.id.compt);
        companyt=(TextView)rootView.findViewById(R.id.comp);
        yeart=(TextView)rootView.findViewById(R.id.yeartext);
        brancht=(TextView)rootView.findViewById(R.id.branchtext);
        feedt=(TextView)rootView.findViewById(R.id.feedtext);
        auth = FirebaseAuth.getInstance();
        addf.setVisibility(View.GONE);
        DatabaseReference refernce = FirebaseDatabase.getInstance().getReference();
        DatabaseReference checkref=refernce.child("users").child(auth.getCurrentUser().getUid());
        checkref.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Log.d("harry123",""+post);
                    String strkey = post.getKey().toString();
                    Log.d("harry123",""+strkey);
                    if(strkey.equalsIgnoreCase("name"))
                        getname=post.getValue().toString();
                    if(strkey.equalsIgnoreCase("year"))
                        getyear=post.getValue().toString();
                    if(strkey.equalsIgnoreCase("branch"))
                        getbranch=post.getValue().toString();
                    if (strkey.equalsIgnoreCase("check")) {
                        String str = post.getValue().toString();
                        Log.d("harry123",""+str);
                        if (str.equals("true")) {
                            addf.setVisibility(View.VISIBLE);
                            Log.d("harry123","ok");
                            //Toast.makeText(getContext(),"You can add feedback",Toast.LENGTH_LONG).show();
                            Log.d("harry123",""+"in");
                            DatabaseReference refernce1 = FirebaseDatabase.getInstance().getReference();
                             final String mainstr=auth.getCurrentUser().getUid().toString();
                            DatabaseReference checkref = refernce1.child("feedback");
                            checkref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                                        Log.d("harry123", "" + post);
                                        String ch=post.getKey().toString();
                                        if (ch.equals(mainstr)) {
                                            addf.setText(" Edit Feedback ");
                                            //Toast.makeText(getContext(), "You have already added feedback! You can edit it! ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    }
                }
                Log.d("harry123",""+getname+getbranch+getyear);
            }
            public void onCancelled(DatabaseError databaseError) {}
        });
        Log.d("harry123",""+getname+getbranch+getyear);
        addf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("harry123",""+getname+getbranch+getyear);
                editname.setText(getname);

                edityear.setText(getyear);
                branch.setText(getbranch);
                namet.setVisibility(namet.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                editname.setVisibility(editname.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                yeart.setVisibility(yeart.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                edityear.setVisibility(edityear.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                brancht.setVisibility(brancht.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                branch.setVisibility(branch.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                feedt.setVisibility(feedt.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                editidea.setVisibility(editidea.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                submit.setVisibility(submit.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                companyt.setVisibility(companyt.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                editcomp.setVisibility(editcomp.getVisibility() == View.GONE ? View.VISIBLE
                        : View.GONE);
                addf.setVisibility(View.GONE);
                submit.setEnabled(false);

                editcomp.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(s.toString().trim().length()==0){
                            editcomp.setError( "Name is required!" );
                            submit.setEnabled(false);
                            is=0;
                        } else {
                            is=1;
                            if(is==1&&isa==1)
                                submit.setEnabled(true);
                            //submit.setEnabled(true);
                        }


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });
                editidea.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(s.toString().trim().length()==0){
                            editidea.setError( "Idea ?!" );
                            submit.setEnabled(false);
                            isa=0;
                        } else {
                            isa=1;
                            if(is==1&&isa==1)
                                submit.setEnabled(true);
                            //submit.setEnabled(true);
                        }


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference refernce =ref.child("feedback");
                        //Firebase ref=new Firebase("https://mnnit-campus-recruitment.firebaseio.com/feedback");
                       // getname=editname.getText().toString();
                        // getyear=edityear.getText().toString();
                        String getidea=editidea.getText().toString();
                       // getbranch=branch.getText().toString();
                        String getcompany=editcomp.getText().toString();
                        feed feedback=new feed(getname,getyear,getbranch,getidea,getcompany);
                        //String key=ref.push().getKey();
                        refernce.child(auth.getCurrentUser().getUid()).setValue(feedback);
                        Toast.makeText(getContext(),"SUBMITTED",Toast.LENGTH_LONG).show();

                        editcomp.setError(null);
                        editidea.setError(null);
                        addf.setVisibility(View.VISIBLE);
                        yeart.setVisibility(View.GONE);
                        brancht.setVisibility(View.GONE);
                        namet.setVisibility(View.GONE);
                        feedt.setVisibility(View.GONE);
                        edityear.setVisibility(View.GONE);
                        editname.setVisibility(View.GONE);
                        editidea.setVisibility(View.GONE);
                        branch.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        editcomp.setVisibility(View.GONE);
                        companyt.setVisibility(View.GONE);
                    }
                });






            }
        });

        listView = (ListView)rootView.findViewById(R.id.list_view_for_courses);

        piclist = new ArrayList();

        Log.d("harry123", "Update onPreExecute start");
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading....");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
        try {

            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("feedback");

//                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Political_Science/Semester_2/Political_Processes_In_India");
            Log.d("harry123", "Do In back 1");
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<String> stm = new ArrayList<>();
                    int i,j;
                    for(DataSnapshot post : dataSnapshot.getChildren())
                    {
                        Log.d("debo12", "" + post);
                        String strkey="";
                        String str = post.getValue().toString();
                        String st[]=str.split(",");
                        for(i=0;i<st.length;i++)
                        {
                            for(j=0;j<st[i].length()-5;j++)
                            {
                                String s=st[i].substring(j,j+5);
                                        if(s.equalsIgnoreCase("name="))
                                        {
                                            strkey=st[i].substring(j+5);
                                        }
                            }
                        }
                        Log.d("debo13", "" + strkey);
                        Log.d("debo14", "" + str);
                        piclist.add(new pics(strkey.trim()));
                        picsadapter = new picsAdapter(getActivity(), piclist);
                        listView.setAdapter(picsadapter);
                        stm.add(strkey.trim());

                    }
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

        n = new Intent(getActivity(), feedDef.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // Toast.makeText(getActivity(), "Clicked product id "+view.getTag(), Toast.LENGTH_SHORT).show();
//                Intent n = new Intent(this, Main3Activity.class);
                n.putExtra("category",view.getTag().toString());
                Log.d("harry123_audioFileName", view.getTag().toString());

                startActivity(n);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
