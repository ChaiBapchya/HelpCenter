package com.gpnv.helpcenter.ancillary_activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gpnv.helpcenter.DBAdapter;
import com.gpnv.helpcenter.Information;
import com.gpnv.helpcenter.MainActivity;
import com.gpnv.helpcenter.MyReceiver;
import com.gpnv.helpcenter.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewMedicationActivity extends AppCompatActivity implements
        NoticeDialogListener {
    private EditText dateTill;
    private EditText name;
    private EditText amountLeft;
    private RecyclerView scheduleList;
    private Spinner dosageType;
    private Button addSchedule;
    private ScheduleAdapter scheduleAdapter;
    private DBAdapter dbHelper;
    EditText medicineName;
    EditText dosageUnits;
    EditText dosageTime;
    EditText duration;
    EditText stock;
    EditText storeName;
    EditText storeNumber;

    public PendingIntent pendingIntent;
public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("F", "Dialog closed");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);

        dateTill = (EditText) findViewById(R.id.new_medication_till_date);
        MainActivity.SetDate tillTime = new MainActivity.SetDate(dateTill, (Context) this);
        addSchedule = (Button) findViewById(R.id.new_medication_add_schedule_item);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = NewMedicationActivity.this.getLayoutInflater();
                android.support.v7.app.AlertDialog.Builder builder = new
                        android.support.v7.app.AlertDialog.Builder(NewMedicationActivity.this);
                builder.setView(inflater.inflate(R.layout.dialog_medication_time, null))
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogFragment fragment = new NoticeDialogFragment(getApplicationContext());
                                fragment.show(getSupportFragmentManager(), "date");
                            }
                        })
                        .create()
                        .show();
            }
        });
        LayoutInflater inflater = this.getLayoutInflater();
                // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_medication_time, null);
       /*  EditText dosage = (EditText) v.findViewById(R.id.dosage_units);
         EditText date = (EditText) v.findViewById(R.id.dosage_time);
*/

        dosageType = (Spinner) findViewById(R.id.new_medication_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dosage_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosageType.setAdapter(adapter);

        /*scheduleAdapter = new ScheduleAdapter(this);
        scheduleList = (RecyclerView) findViewById(R.id.new_medication_times_list);
        scheduleList.setAdapter(scheduleAdapter);
        scheduleList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
*/
        medicineName=(EditText) findViewById(R.id.new_medication_name_edit);
        dosageType= (Spinner) findViewById(R.id.new_medication_type_spinner);
        NoticeDialogFragment ndf = new NoticeDialogFragment(getApplicationContext());
        //dosageUnits=ndf.dosage;
        //dosageTime=ndf.date;
        duration=(EditText) findViewById(R.id.new_medication_till_date);
        stock=(EditText) findViewById(R.id.new_medication_amount_left);
        storeName=(EditText) findViewById(R.id.store_name);
        storeNumber=(EditText) findViewById(R.id.store_number);
        dbHelper= new DBAdapter(this);


    }
    public void gotoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addToDatabase(View v){
        /*String name= medicineName.getText().toString();
        String type=dosageType.getSelectedItem().toString();
//        int units= Integer.parseInt(dosageUnits.getText().toString());
        //String units= dosageUnits.getText().toString();
        //int dosUnits=Integer.parseInt(units);
        //String time= dosageTime.getText().toString();
        String setDuration= duration.getText().toString();
        int setStock= Integer.parseInt(stock.getText().toString());
        //int stock=new Integer(setStock).intValue();
        String sName= storeName.getText().toString();
        String sNumber= storeNumber.getText().toString();
        //long id=dbHelper.insertMedicines(name,type,2,time,setDuration,setStock,sName,sNumber);
        if(id<0){
            Toast.makeText(this, "unsuccessful", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
        }
        String data= dbHelper.getMedicines();
                //prepare your data
                TextView tv =(TextView) findViewById(R.id.display_text);
                tv.setText(data);
                // you can set id, tag, text color, font, ...
*/
                //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        ;
    }
    /*
public void viewDetails(View view)
{
    String data= dbHelper.getAllData();
    Toast.makeText(this, "data is viewed", Toast.LENGTH_SHORT).show();
}
*/
    public static class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
        protected List<Information.FixedMedicationTime> schedules;
        public Context mContext;

        public ScheduleAdapter(Context context) {
            mContext = context;

            schedules = new ArrayList<Information.FixedMedicationTime>();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView timeText;
            private TextView dosageText;
            private Button deleteButton;

            public ViewHolder(View rootView) {
                super(rootView);
                timeText = (TextView) rootView.findViewById(R.id.schedule_time_text);
                dosageText = (TextView) rootView.findViewById(R.id.schedule_dosage_text);
                deleteButton = (Button) rootView.findViewById(R.id.schedule_delete_button);
            }
        }

        public void addSchedule(String time, int dosage) {
            Information.FixedMedicationTime schedule = new Information.FixedMedicationTime();
            schedule.dosage = dosage;
            schedule.time = time;
            schedules.add(schedule);
            notifyDataSetChanged();
        }

        @Override
        public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_list_item, parent, false);
            return new ScheduleAdapter.ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final ScheduleAdapter.ViewHolder holder, final int position) {
           // Date date = new Date(schedules.get(position).time);
            holder.dosageText.setText(String.valueOf(schedules.get(position).dosage));
            //holder.timeText.setText(date.getHours() + ":" + date.getMinutes());
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    schedules.remove(position);
                    notifyDataSetChanged();
                }
            });
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
            return schedules.size();
        }
    }

    public void onDialogPositiveClick(String time, int dosage) {
        Log.d("F", "Dialog closed");
        Information.FixedMedicationTime schedule = new Information.FixedMedicationTime();
        schedule.time = time;
        schedule.dosage = dosage;
        scheduleAdapter.schedules.add(schedule);
        scheduleAdapter.notifyDataSetChanged();
        //Toast.makeText(this, "constructor called", Toast.LENGTH_SHORT).show();
    }


    public class NoticeDialogFragment extends DialogFragment {


        // Use this instance of the interface to deliver action events
        NoticeDialogListener mListener;
public Context tcontext;
        public NoticeDialogFragment(Context context){
            tcontext=context;
        }
        // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            // Verify that the host activity implements the callback interface
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListener) activity;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString()
                        + " must implement NoticeDialogListener");
            }
        }
           @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            View v = inflater.inflate(R.layout.dialog_medication_time, null);


              final EditText date= (EditText) v.findViewById(R.id.dosage_time);
            //MainActivity.SetTime time = new MainActivity.SetTime(date, getActivity());
              final EditText dosage = (EditText) v.findViewById(R.id.dosage_units);
            //Toast.makeText(this, "constructor called", Toast.LENGTH_SHORT).show();
            String tu = date.getText().toString();
            Log.d("K", "" + tu);

            //Toast.makeText(this,tu+"",Toast.LENGTH_LONG).show();

            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                mListener.onDialogPositiveClick(
                                        date.getText().toString(),
                                        Integer.parseInt(dosage.getText().toString()));
                            } catch (Exception e) {

                            }
                            String name= medicineName.getText().toString();
                            String type=dosageType.getSelectedItem().toString();
//        int units= Integer.parseInt(dosageUnits.getText().toString());
                            //String units= dosageUnits.getText().toString();
                            //int dosUnits=Integer.parseInt(units);
                            //String time= dosageTime.getText().toString();
                            String setDuration= duration.getText().toString();
                            int setStock= Integer.parseInt(stock.getText().toString());
                            //int stock=new Integer(setStock).intValue();
                            String sName= storeName.getText().toString();
                            String sNumber= storeNumber.getText().toString();

                            /*if(id<0){
                                Toast.makeText(this, "unsuccessful", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
                            }*/
                            try {
                                dbHelper.insertMedicines(name,type, Integer.parseInt(dosage.getText().toString()) ,date.getText().toString(),setDuration,setStock,sName,sNumber);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String data= dbHelper.getMedicines();
                            TextView tv =(TextView) findViewById(R.id.display_text);
                            tv.setText(data);
                            alarmGenerator(tcontext);

                        }
                    });

            return builder.create();
        }
        public void alarmGenerator(Context context)
        {
Toast.makeText(context, "In alarm generator", Toast.LENGTH_LONG).show();
            int hr=0,min=0,sec=0;
            DBAdapter dbAdapter = new DBAdapter(context);
            Cursor cursor = dbAdapter.getMedicineTime();
            while (cursor.moveToNext()) {
                String string1 = cursor.getString(0);
              //  Toast.makeText(tcontext,string1 , Toast.LENGTH_LONG).show();
                String[] newString = string1.split(":");
                hr = Integer.parseInt(newString[0]);
                min = Integer.parseInt(newString[1]);
                //    sec = Integer.parseInt(newString[2]);
            }
            Calendar calendar = Calendar.getInstance();
            if(hr<=12)
            {
                calendar.set(Calendar.AM_PM, Calendar.AM);

            }
            else{
                calendar.set(Calendar.AM_PM, Calendar.PM);
           //     hr=hr-12;
            }
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            //Toast.makeText(tcontext,hr+min+"le ayaaaa" , Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(tcontext, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(tcontext, 0, myIntent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}

interface NoticeDialogListener {
    public void onDialogPositiveClick(String time, int dosage);
}
