package com.gpnv.helpcenter;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gpnv.helpcenter.ancillary_activities.NewMedicationActivity;
import com.gpnv.helpcenter.main_activity_fragments.ActivitiesFragment;
import com.gpnv.helpcenter.main_activity_fragments.MainFragment;
import com.gpnv.helpcenter.main_activity_fragments.MealsFragment;
import com.gpnv.helpcenter.main_activity_fragments.MedicationFragment;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            MealsFragment.OnFragmentInteractionListener,
            ActivitiesFragment.OnFragmentInteractionListener,
            MedicationFragment.OnFragmentInteractionListener,
            MainFragment.OnFragmentInteractionListener {
    //TextView medication_name_first;
    TextView medication_time_first;
    private MobileServiceClient mClient;
public static boolean mealmedflag=false;
    TextToSpeech t1;
    ArrayList<String> obj_name = new ArrayList<String>();
    ArrayList<String> obj_time= new ArrayList<String>();
//public static int only1time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //if(only1time==0) {
          //  Toast.makeText(this,"aya - ",Toast.LENGTH_LONG).show();
            MealsFragment mealsFragment = new MealsFragment(this);
            mealsFragment.alarmGenerator(this);
//only1time++;
        //}

        com.github.clans.fab.FloatingActionButton newMedicationButton =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item);

        newMedicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewMedicationActivity.class);
                startActivity(i);
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            mClient = new MobileServiceClient(
                    "https://helpcentergpnv.azure-mobile.net/",
                    "TINEzguKrNMvLlKZZJpxEYmPTZuUFj61",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

/*
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        long current_time = calendar1.getTimeInMillis();
        //set notification for date --> 8th January 2015 at 9:06:00 PM
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 1);

        Date currentTimePlusOneMinute = cal.getTime();
        Timestamp alramTimeStamp = new Timestamp(calendar.getTimeInMillis());
        Timestamp currentTimeStamp = new Timestamp(current_time);
        if (true) {//alramTimeStamp.after(currentTimeStamp)) {
            Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC, currentTimePlusOneMinute.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
*/

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new MainFragment());
        transaction.commit();
    }
/*
    public void openMeals(View view) {
     Intent intent= new Intent(this,MealsFragment.class);
        startActivity(intent);

       */
/* MealsFragment mealsFragment= new MealsFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.more_meals, MealsFragmentFragment, null)
                .addToBackStack(null)
                .commit();*//*

    }
*/
    public void openMedications(View view) {
        Intent intent = new Intent(MainActivity.this, MedicationFragment.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meals) {
            ft.replace(R.id.fragment_container, new MealsFragment());
        } else if (id == R.id.nav_medications) {
            ft.replace(R.id.fragment_container, new MedicationFragment());
        } else if (id == R.id.nav_home) {
            ft.replace(R.id.fragment_container, new MainFragment());
        }

        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(Uri uri) {

    }

    public static final void setAlarm(int seconds, AlarmManager alarmManager,
                                      Context context) {
        // create the pending intent
        Intent intent = new Intent(context, ReminderActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        // get the alarm manager, and scedule an alarm that triggers my
        // activity
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + seconds * 1000, pendingIntent);
        //Toast.makeText(context, "Timer set to " + seconds + " seconds.",
             //   Toast.LENGTH_SHORT).show();
    }

    public static class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

        private EditText editText;
        private Calendar myCalendar;
        private Context ctx;

        public SetTime(EditText editText, Context ctx){
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            this.myCalendar = Calendar.getInstance();
            this.ctx = ctx;
            onTimeSet(new TimePicker(ctx),
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE)
            );
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if(hasFocus){
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                new TimePickerDialog(ctx, this, hour, minute, true).show();
            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            this.editText.setText( hourOfDay + ":" + minute);
        }

    }

    public static class SetDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

        private EditText editText;
        private Calendar myCalendar;
        private Context ctx;

        public SetDate(EditText editText, Context ctx){
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            this.myCalendar = Calendar.getInstance();
            this.ctx = ctx;
            onDateSet(new DatePicker(ctx),
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if(hasFocus){
                int year = myCalendar.get(Calendar.YEAR);
                int monthOfYear = myCalendar.get(Calendar.MONTH);
                int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(ctx, this, year, monthOfYear, dayOfMonth).show();
            }
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            this.editText.setText( dayOfMonth + " " + Information.months[monthOfYear] + ", " + year);
        }

    }
}
