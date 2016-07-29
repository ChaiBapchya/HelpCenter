package com.gpnv.helpcenter.main_activity_fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gpnv.helpcenter.DBAdapter;
import com.gpnv.helpcenter.MainActivity;
import com.gpnv.helpcenter.MyReceiver;
import com.gpnv.helpcenter.R;

import java.util.Calendar;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Context mealcontext;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealsFragment newInstance(String param1, String param2) {
        MealsFragment fragment = new MealsFragment();
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
    }

    public MealsFragment() {


    }

    public MealsFragment(Context context) {
        // Required empty public constructor
        mealcontext = context;
    }

    public PendingIntent pendingIntent;
    public int lunch_hr, lunch_min, dinner_hr, dinner_min;

    public void alarmGenerator(Context context) {
        MainActivity.mealmedflag = true;
        Log.i("app", MainActivity.mealmedflag + "");
        Toast.makeText(context, "In alarm generator", Toast.LENGTH_LONG).show();
       Log.d("read", "In here");
        DBAdapter dbAdapter= new DBAdapter(mealcontext);

        String string2 = dbAdapter.getLunchTime();
        Log.d("lunch",string2+"**********************");
       // Toast.makeText(mealcontext, "please " + string2, Toast.LENGTH_LONG).show();

        String[] newString2 = string2.split(":");
        lunch_hr = Integer.parseInt(newString2[0]);
        lunch_min = Integer.parseInt(newString2[1]);

        Calendar calendar1 = Calendar.getInstance();
        if (lunch_hr <= 12) {
            calendar1.set(Calendar.AM_PM, Calendar.AM);

        } else {
            calendar1.set(Calendar.AM_PM, Calendar.PM);
            //     hr=hr-12;
        }
        calendar1.set(Calendar.HOUR_OF_DAY, lunch_hr);
        calendar1.set(Calendar.MINUTE, lunch_min);
        calendar1.set(Calendar.SECOND, 0);

        //Toast.makeText(mealcontext,"le ayaaaa"+lunch_hr+lunch_min , Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(mealcontext, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(mealcontext, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) mealcontext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

  /*RecyclerView activityList = (RecyclerView) view.findViewById(R.id.meal_list);
        activityList.setAdapter(new MealsAdapter(getActivity()));
        activityList.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        return view;
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
/*

    public static class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
        private List<Information.Meal> meals;
        private Context mContext;

        public MealsAdapter(Context context) {
            mContext = context;
            this.meals = Information.Meal.getAll();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;
            private TextView timeText;
            private TextView medicationText;
            private ImageView overflowMenu;

            public ViewHolder(View rootView) {
                super(rootView);
                nameText = (TextView) rootView.findViewById(R.id.meal_name_text);
                timeText = (TextView) rootView.findViewById(R.id.meal_time_text);
                medicationText = (TextView) rootView.findViewById(R.id.meal_medications_text);
                overflowMenu = (ImageView) rootView.findViewById(R.id.meal_overflow_menu);
            }
        }

        @Override
        public MealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_meal, parent, false);
            return new MealsAdapter.ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final MealsAdapter.ViewHolder holder, final int position) {


//            holder.overflowMenuButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
            Log.d("A", "Overflow menu clicked");
//                    PopupMenu popupMenu = new PopupMenu(mContext, holder.overflowMenuButton);
//                    popupMenu.inflate(R.menu.expense_list_item_popup_menu);
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            int itemId = item.getItemId();
//                            switch (itemId) {
//
//                            }
//                            return false;
//                        }
//                    });
//                    popupMenu.show();
//                }
//            });


        }
/*/
        // @Override
   /*     public int getItemCount() {
            return meals.size();
        }
    }*/

    }
