package com.gpnv.helpcenter.main_activity_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gpnv.helpcenter.Information;
import com.gpnv.helpcenter.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesFragment newInstance(String param1, String param2) {
        ActivitiesFragment fragment = new ActivitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivitiesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        RecyclerView activityList = (RecyclerView) view.findViewById(R.id.activity_list);
        activityList.setAdapter(new ActivityAdapter(getActivity()));
        activityList.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    public static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
        private List<Information.Activity> activity;
        private Context mContext;

        public ActivityAdapter (Context context) {
            mContext = context;
            this.activity = Information.Activity.getAll();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private ProgressBar goalProgress;
            private TextView goalText;
            private TextView nameText;
            private TextView extraText;
            private ImageView overflowMenu;

            public ViewHolder(View rootView) {
                super(rootView);
                goalProgress = (ProgressBar) rootView.findViewById(R.id.activity_goal_progress);
                goalText = (TextView) rootView.findViewById(R.id.activity_goal_text);
                nameText = (TextView) rootView.findViewById(R.id.activity_name_text);
                extraText = (TextView) rootView.findViewById(R.id.activity_extra_text);
                overflowMenu = (ImageView) rootView.findViewById(R.id.activity_overflow_menu);
            }
        }

        @Override
        public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_activity, parent, false);
            return new ActivityAdapter.ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final ActivityAdapter.ViewHolder holder, final int position) {
            holder.goalProgress.setProgress(10);

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
            return activity.size();
        }
    }
}
