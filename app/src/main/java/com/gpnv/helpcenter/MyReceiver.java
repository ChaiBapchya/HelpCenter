package com.gpnv.helpcenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gpnv.helpcenter.main_activity_fragments.MealsFragment;

public class MyReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		/*Intent service1 = new Intent(context, MyAlarmService.class);
	     context.startService(service1);*/
		Log.i("app", "called receiver method"+context);
		MealsFragment mealsFragment= new MealsFragment();
		try{

	Utils.generateNotification(context);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
