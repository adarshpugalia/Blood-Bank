/* 
 * This activity just displays the wallpaper.
 * Tapping on the screen opens the first activity.
 */
package taadnairsshha.apps.bloodbank;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	
	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Toast to intimate user for tapping.
		Toast.makeText(getApplicationContext(), "Tap to Continue", Toast.LENGTH_SHORT).show();
	}
	
	// Function called when the user taps on the screen.
	public void check(View view)
	{
		// Getting the shared preference object.
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
		
		// If the shared preference file exists, it means the user is registered.
		// Starting the Home Activity.
		if(sharedPref.contains("Phone"))
		{
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
		}
		
		// Else forwarding the user to the log in/registration .
		else
		{
			Intent intent = new Intent(this, LogIn.class);
			startActivity(intent);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
