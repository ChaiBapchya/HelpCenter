package com.gpnv.helpcenter.main_activity_fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpnv.helpcenter.CustomAdapter;
import com.gpnv.helpcenter.DBAdapter;
import com.gpnv.helpcenter.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<String> obj_name = new ArrayList<String>();
    public ArrayList<String> obj_time = new ArrayList<>();
   private String a[] = new String[100];
    //private Long min[] = new Long[100];
    //private Long hr[] = new Long[100];
    private String b[]= new String[100];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Medicine> medicines;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicationFragment newInstance(String param1, String param2) {
        MedicationFragment fragment = new MedicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MedicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        //RecyclerView activityList = (RecyclerView) view.findViewById(R.id.medication_list);
        //activityList.setAdapter(new MedicationAdapter(getActivity()));
        //activityList.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.medication_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        initializeData();
        CustomAdapter adapter = new CustomAdapter(medicines);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void initializeData() {
        medicines = new ArrayList<>();
        Context context = this.getActivity();
        DBAdapter dbAdapter = new DBAdapter(context);


        Cursor cursor = dbAdapter.getMedicinesCursor();
        int i = 0;
        int j = 0;

        while (cursor.moveToNext()) {
            int index2 = cursor.getColumnIndex("medicineName");
            int index5 = cursor.getColumnIndex("dosageTime");
            int index4=cursor.getColumnIndex("stock");
            String name = cursor.getString(index2).toString();
            String time = cursor.getString(index5).toString();
            int stock=cursor.getInt(index4);
            //Toast.makeText(this, name, Toast.LENGTH_LONG).show();

            obj_name.add(i, cursor.getString(index2).toString());
            a[i] = obj_name.get(i);
            obj_time.add(i, cursor.getString(index5));
            b[i] = obj_time.get(i);


            medicines.add(new Medicine(a[i], time,stock+"pills left"));
            Log.d("H", name);
            Log.d("H", "" + time);
            i++;
        }

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

   /* public static class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
        private List<Information.Medication> medications;
        private Context mContext;

        public MedicationAdapter(Context context) {
            mContext = context;
            this.medications = Information.Medication.getAll();
        }
*//*
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;
            private TextView timesText;
            private TextView stockText;
            private ImageView overflowMenu;

            public ViewHolder(View rootView) {
                super(rootView);
                nameText = (TextView) rootView.findViewById(R.id.medication_name_text);
                timesText = (TextView) rootView.findViewById(R.id.medication_times_text);
                stockText = (TextView) rootView.findViewById(R.id.medication_stock_text);
                overflowMenu = (ImageView) rootView.findViewById(R.id.medication_overflow_menu);
            }
        }

        @Override
        public MedicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_activity, parent, false);
            return new MedicationAdapter.ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final MedicationAdapter.ViewHolder holder, final int position) {


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

        @Override
        public int getItemCount() {
            return medications.size();
        }
    }*/

}
