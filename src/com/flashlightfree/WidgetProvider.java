package com.flashlightfree;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider{

	private static final String CLICK_NAME_ACTION = "com.flashlightfree.action.widget.click";
	private static RemoteViews rv;
	
	
	@Override  
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,  
            int[] appWidgetIds) {
		
		final int N = appWidgetIds.length;  
        for (int i = 0; i < N; i++) {  
            int appWidgetId = appWidgetIds[i];  
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }  
        
        
	}
	
	 private void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		// TODO Auto-generated method stub
		 
		 rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		 Intent intentClick = new Intent(CLICK_NAME_ACTION);
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
					intentClick, 0);
		 rv.setOnClickPendingIntent(R.id.powerbtn, pendingIntent);
		 appWidgetManager.updateAppWidget(appWidgetId, rv);
	}

	@Override  
	    public void onDeleted(Context context, int[] appWidgetIds) {
		 
		 final int N = appWidgetIds.length;  
	        for (int i = 0; i < N; i++) {  
	            int appWidgetId = appWidgetIds[i];  
	        } 
	 }
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		if (rv == null) {
			rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		}
		
		if (intent.getAction().equals(CLICK_NAME_ACTION))
		{
			if(!uitil.poweronoff )
			{
				uitil.poweronoff=!uitil.poweronoff;
				rv.setImageViewResource(R.id.powerbtn,R.drawable.poweron80);
				
				uitil.powerled=new PowerLED();
				uitil.powerled.turnOn();
				
			}
			else
			{
				uitil.poweronoff=!uitil.poweronoff;
				rv.setImageViewResource(R.id.powerbtn,R.drawable.poweroff80);
				uitil.powerled.turnOff();
				uitil.powerled.Destroy();
			}
		}
		
		AppWidgetManager appWidgetManger = AppWidgetManager
				.getInstance(context);
		int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(
				context, WidgetProvider.class));
		appWidgetManger.updateAppWidget(appIds, rv);
	}
	 
}
