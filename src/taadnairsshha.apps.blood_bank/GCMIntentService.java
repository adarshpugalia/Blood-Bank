package taadnairsshha.apps.bloodbank;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

public class GCMIntentService extends IntentService
{
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	
	public GCMIntentService()
	{
		super("GCMIntentService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent)
	{
		String message = intent.getExtras().getString("message");
		
		if(message!=null)
		{
			SharedPreferences prefs = getSharedPreferences("DonateBlood", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("message", message);
			if(prefs.contains("Count"))
			{
				int count = prefs.getInt("Count", 0);
				count++;
				editor.putString("donate"+String.valueOf(count), message);
				editor.putInt("Count", count);
				editor.commit();
			}
			else
			{
				editor.putInt("Count", 1);
				editor.putString("donate1", message);
				editor.commit();
			}
			
			
			{
				sendNotification(message);
			}
		}
		GCMBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	private void sendNotification(String message)
	{
		String[] info = message.split("\\$");
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.ic_launcher)
			    .setContentTitle("Blood Required!")
			    .setContentText(info[0] + " needs your blood!");
		
		Intent resultIntent = new Intent(this, DonateBlood.class);
		
		PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    resultIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
		
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.setAutoCancel(true);
		
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(1, mBuilder.build());
	}
}
