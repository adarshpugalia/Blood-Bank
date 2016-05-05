package taadnairsshha.apps.bloodbank;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* This class checks the InternetConnection for a device.
 * It has a private member connectionContext which stores the context of the call.
 * It has a boolean public function that returns if the device is connected.
 */
public class InternetConnection
{
	// connectionContext stores the context of the application passed from the
	// calling context.
	private Context connectionContext;
	
	// Constructor
	public InternetConnection(Context context)
	{
		connectionContext = context;
	}
	
	// This function checks if the device is connected to the Internet.
	// Returns true if the device is connected.
	public boolean isConnected()
	{
		// Create a ConnectivityManager that answers queries regarding connectivity.
		ConnectivityManager connectivityManager = (ConnectivityManager)connectionContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// Get the internet connection status.
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		return (activeNetwork!=null && activeNetwork.isConnectedOrConnecting());
	}
}