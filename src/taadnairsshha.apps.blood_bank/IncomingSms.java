package taadnairsshha.apps.bloodbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {
	
	public final static String EXTRA_MESSAGE = "taadnairsshha.apps.bloodbank.MESSAGE";
	
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    
    public void onReceive(Context context, Intent intent) {
    
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            
            if (bundle != null) {
                
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                
                for (int i = 0; i < pdusObj.length; i++) {
                    
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();                    
                    
                    if(senderNum.equals(message))
                    {
                    	abortBroadcast();
                    	Intent new_intent = new Intent(context, EnterDetails.class);
                    	new_intent.putExtra(EXTRA_MESSAGE, senderNum);
                    	new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    	context.startActivity(new_intent);
                    }
                    else
                    {
                    	Toast.makeText(context, "Phone number wasnt verified!", Toast.LENGTH_SHORT).show();
                    	Toast.makeText(context, "Please enter the correct number!", Toast.LENGTH_SHORT).show();
                    	
                    }
                    
                } // end for loop
              } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
            
        }
    }    
}