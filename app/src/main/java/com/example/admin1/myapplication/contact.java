package com.example.admin1.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link contact.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link contact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class contact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Intent callIntent;

    ListView lv;
    Button bt;

    public  ArrayList<String> nameList = new ArrayList<>();
    public  ArrayList<String> deptList = new ArrayList<>();
    public  ArrayList<String> phno = new ArrayList<>();

    CustomAdapter cusAdap;

    private OnFragmentInteractionListener mListener;

    public contact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment contact.
     */
    // TODO: Rename and change types and number of parameters
    public static contact newInstance(String param1, String param2) {
        contact fragment = new contact();
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
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(0xff000000);
  //      toolbar.setTitle("Contact");
        //((Main2Activity)getContext()).getSupportActionBar().setTitle("Contact");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_contact, container, false);
        lv = (ListView)rootView.findViewById(R.id.listView);
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference allPostFromAuthor = ref.child("contact");

            allPostFromAuthor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        String str = post.getValue().toString();

                        String[] parts = str.split(",");
                        int l = parts.length;
                        Log.d("debo", l + "" + parts[0] + "" + parts[1] + "" + parts[2]);

                        for (int i = 0; i < l; i++) {

                            String s[] = parts[i].split("=");
                            String val = s[1];
                            //Log.d("debo1", "val=" + s[1] + "" + i);
                            if (i == 0) {
                                phno.add(val);
                                //Log.d("debo2", "" + phno.get(0));
                            } else if (i == 1)
                                nameList.add(val);
                            else if (i == 2) {
                                val = val.substring(0,val.length()-1);
                                deptList.add(val);
                            }

                        }
                        cusAdap = new CustomAdapter(getActivity(), nameList, deptList, phno);
                        lv.setAdapter(cusAdap);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        catch (Exception e) {
//             	b.setText("title");
            System.out.println("title2 : ");
            e.printStackTrace();
        }

        cusAdap = new CustomAdapter(getActivity(), nameList, deptList, phno);
        lv.setAdapter(cusAdap);

        callIntent = new Intent(Intent.ACTION_CALL);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Calling "+nameList.get(position), Toast.LENGTH_LONG).show();
                String s = "tel:"+phno.get(position);
                callIntent.setData(Uri.parse(s));
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        }
        );

        bt=(Button)rootView.findViewById(R.id.mail);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Compose", Toast.LENGTH_LONG).show();
                String[] TO = {"mnnit.placements@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                startActivity(emailIntent);
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
