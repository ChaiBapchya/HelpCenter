package com.gpnv.helpcenter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.gpnv.helpcenter.MainActivity.setAlarm;


public class ReminderActivity extends Activity {
    TextToSpeech t1;
     int notificationID=1 ;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Button snooze = (Button) findViewById(R.id.reminder_snooze_btn);
        Button taken = (Button) findViewById(R.id.reminder_medtaken_btn);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override

            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    String toSpeak = "Please take medicines";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm(5, ((AlarmManager) getSystemService(ALARM_SERVICE)), context);
                finish();
            }
        });

        taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setAlarm(5, ((AlarmManager) getSystemService(ALARM_SERVICE)), context);
                DBAdapter adapter = new DBAdapter(context);

               // adapter.updateMedicineStock();
                finish();
            }
        });

    }
    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v.findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            Toast.makeText(this,"CheckBox is ticked",Toast.LENGTH_LONG).show();
            displayNotification();


        }else{
            Toast.makeText(this,"CheckBox is unticked",Toast.LENGTH_LONG).show();
        }
    }

    public void displayNotification()
    {

        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent i = new Intent(context, CallNotification.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, i, 0);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Notification notif = new NotificationCompat.Builder(context)
                .setContentTitle("Order More??")
                .setContentText("Call now..")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_call_24dp)
                .build();
        CharSequence from = "Order More?";
        CharSequence message = "Call store now";

        notif.setLatestEventInfo(context, from, message, pendingIntent);

        //---100ms delay, vibrate for 250ms, pause for 100 ms and
        // then vibrate for 500ms---
        notif.vibrate = new long[] { 100, 250, 100, 500};
        nm.notify(notificationID, notif);
         /*Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:9870450100"));
        startActivity(callIntent);
*/
        TextView medicine_name=(TextView) findViewById(R.id.reminder_medicine_name);
        medicine_name.setText(""+DBAdapter.medicine_name);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_reminder, menu);
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
}
