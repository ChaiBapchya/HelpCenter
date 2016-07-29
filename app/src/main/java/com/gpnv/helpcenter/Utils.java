   package com.gpnv.helpcenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Locale;

public class Utils {
	public	static String StringTitle;
	public static String StringText;
	public static NotificationManager mManager;

					static TextToSpeech t1;
	@SuppressWarnings("static-access")
	public static void generateNotification(Context context){

		mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context,ReminderActivity.class);
		Notification notification ;//new Notification(R.drawable.fab_add,"This is a test message!", System.currentTimeMillis());
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

if(MainActivity.mealmedflag){
	Log.d("Meal",MainActivity.mealmedflag+"");
	StringTitle="Please take meals";
	StringText="Meals";
	MainActivity.mealmedflag=false;
			}
		else
{
	Log.d("Meal",MainActivity.mealmedflag+"");
	StringTitle="Please take medicines";
	StringText="Medicines";

}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		notification = builder.setContentIntent(pendingNotificationIntent).setLights(0x0,100000,100000)
				.setSmallIcon(R.drawable.fab_add).setWhen(System.currentTimeMillis())
				.setAutoCancel(true).setContentTitle(StringTitle)
				.setContentText(StringText).build();

		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);


		notification.flags |= Notification.FLAG_AUTO_CANCEL;

//		notification.setLatestEventInfo(context, "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
		mManager.notify(0, notification);

		Log.d("F", "Notification triggered");
//
//        mManager.notify(1, notification);

		//notification.setLatestEventInfo(context, "HelpCenter", "Please take medicines", pendingNotificationIntent);
		//mManager.notify(0, notification);


		t1=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					t1.setLanguage(Locale.UK);
					String toSpeak = "Please take medicines";

					t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
				}

			}
		});




	}
}
