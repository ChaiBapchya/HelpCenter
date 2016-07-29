package com.gpnv.helpcenter.main_activity_fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpnv.helpcenter.DBAdapter;
import com.gpnv.helpcenter.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> obj_name = new ArrayList<String>();
    ArrayList<String> obj_time= new ArrayList<>();
    TextView medication_name_1;
    TextView medication_time_1;
    TextView medication_name_2;
    TextView medication_time_2;
    TextView medication_name_3;
    TextView medication_time_3;
    TextView lunch_time;
    TextView dinner_time;
    String lunch;
    String dinner;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String a[]=new String[100];
    private String  b[]=new String[100];

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Context context= this.getActivity();
        DBAdapter dbAdapter= new DBAdapter(context);

        lunch=DBAdapter.lunchTime;
        dinner=DBAdapter.dinnerTime;

        Cursor cursor = dbAdapter.getMedicinesCursor();
        int i=0;
        int j=0;

        while (cursor.moveToNext()) {
                int index2 = cursor.getColumnIndex("medicineName");
                int index5 = cursor.getColumnIndex("dosageTime");
                String name = cursor.getString(index2).toString();
                String time = cursor.getString(index5).toString();
                //Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                obj_name.add(i, cursor.getString(index2).toString());
                a[i] = obj_name.get(i);
                obj_time.add(i, cursor.getString(index5).toString());
                b[i]= obj_time.get(i);
              Log.d("H", name);
                Log.d("H", "" + time);
                i++;
            }


    }


    public void openMeals(View view) {
        MealsFragment mealsFragment= new MealsFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,mealsFragment , null)
                .addToBackStack(null)
                .commit();
    }
   public void openMedication(View view) {
        MedicationFragment medicationFragment= new MedicationFragment();
       FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, medicationFragment,null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_main, container, false);


        medication_name_1=(TextView) v.findViewById(R.id.medication_name_1);
        medication_time_1=(TextView) v.findViewById(R.id.medication_time_1);
        medication_name_1.setText(a[0].toUpperCase());
        medication_time_1.setText("Time :"+ b[0]);
        medication_name_2=(TextView) v.findViewById(R.id.medication_name_2);
        medication_time_2=(TextView) v.findViewById(R.id.medication_time_2);
      medication_name_2.setText(a[1].toUpperCase());
        medication_time_2.setText("Time :"+b[1]);
        medication_name_3=(TextView) v.findViewById(R.id.medication_name_3);
        medication_time_3=(TextView) v.findViewById(R.id.medication_time_3);
        medication_name_3.setText(a[2].toUpperCase());
        medication_time_3.setText("Time :"+b[2]);

        lunch_time=(TextView) v.findViewById(R.id.meal_lunch_time);
        lunch_time.setText("Your Lunch time is set to "+lunch+"Hrs");
        dinner_time=(TextView) v.findViewById(R.id.meal_dinner_time);
        dinner_time.setText("Your Dinner time is set to "+dinner+"Hrs");



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        public void onFragmentInteraction(Uri uri);
    }

}
