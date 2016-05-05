/*
 * This activity lets the user to LogIn if he is already registered.
 * It also lets the unregistered user create a new profile.
 */

package taadnairsshha.apps.bloodbank;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class LogIn extends Activity{

	PublicVariables var;
	
	// Variables for GCM
	public boolean isPlay = false;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    String SENDER_ID = "716092216952";
    GoogleCloudMessaging gcm;
    AtomicInteger msg_id = new AtomicInteger();
    
    
    public ProgressDialog dialog = null;
    public InternetConnection connection = null;
    
    SharedPreferences prefs;
    Context context;
    String reg_id = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		// Getting the context of the application.
		context = getApplicationContext();
		
		// Checking if the device is connected.
		connection = new InternetConnection(context);
		if(connection.isConnected())
		{
			// if the Play Services are enabled.
			if(checkPlayServices())
			{
				gcm = GoogleCloudMessaging.getInstance(this);
				reg_id = getRegistrationId(context);
				
				// if reg_id doesn't exist, register for gcm.
				if(reg_id.isEmpty())
				{
					registerInBackground();
				}
			}
		}
		else
		{
			// No internet connection found.
			Toast.makeText(getApplicationContext(), "No Internet Connection Detected!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	// This function is called when Log In button is clicked.
	public void login(View view)
	{
		// Loging in if the device is connected.
		connection = new InternetConnection(getApplicationContext());
		if(connection.isConnected())
		{
			// Getting the phone number and password from fields.
			EditText edit = (EditText) (findViewById(R.id.edit_phone));
			String phone = "+91" + edit.getText().toString();
			edit = (EditText) (findViewById(R.id.edit_password));
			String password = edit.getText().toString();
			
			String id = getRegistrationId(getApplicationContext());
			
			// if id expired or is empty.
			if(id.isEmpty())
			{
				registerInBackground();
				id = getRegistrationId(getApplicationContext());
			}
			
			// Checking if they are not empty.
			if(phone.length()==0 || password.length()==0)
			{
				// creating a toast depending on which field is empty.
				Context context = getApplicationContext();
				String text = "";
				if(phone.length()==0 && password.length()==0)
					text = "Please enter Phone No. and Password!";
				else if(phone.length()==0)
					text = "Please enter Phone Number!";
				else
					text = "Please enter Password!";
				
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			}
			
			// Checking for 10-digit mobile number
			else if(phone.length()==10)
			{
				// Prepending "+91" to the phone number.
				phone = "+91"+phone;
				try
				{
					// Sending the query to the server.
					String reply = new SendRequest().execute("login.php", "3", "Id", id, "Phone", phone, "Password", password).get();
					
					// 000Webhost.com appends random data at the end! :(
					String[] split = reply.split("#");
					reply = split[0];
					
					if(reply.equals("0"))
						Toast.makeText(getApplicationContext(), "This number is not registered!", Toast.LENGTH_SHORT).show();
					else if(reply.equals("1"))
						Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
					else if(reply.equals("2"))
						Toast.makeText(getApplicationContext(), "Error. Please try again!", Toast.LENGTH_SHORT).show();
					else
					{
						
						String[] user_info = reply.split("\\$");
						
						// committing to the shared preference file.
						SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putString("Name", user_info[0]);
						editor.putString("Phone", user_info[1]);
						editor.putString("Blood Group", user_info[2]);
						editor.putString("City", user_info[3]);
						editor.commit();
									
						// starting the home activity.
						Intent intent_home = new Intent(this, Home.class);
						startActivity(intent_home);
					}
					
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (ExecutionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				Toast.makeText(getApplicationContext(), "Please Enter a valid Phone No!", Toast.LENGTH_SHORT).show();
		}
	}
	
	// Function called when the new user text is clicked.
	public void newUser(View view)
	{
		Intent intent = new Intent(this, VerifyContact.class);
		startActivity(intent);
	}
	
	// Function that checks if play services are available.
	private boolean checkPlayServices()
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(resultCode!=ConnectionResult.SUCCESS)
		{
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
			{
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "The device is not supported on Google Play!", Toast.LENGTH_SHORT).show();
				finish();
			}
			return false;
		}
		return true;
	}
	
	// Function to get the registration id.
	@SuppressLint("NewApi")
	private String getRegistrationId(Context context)
	{
		final SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
		String registration_id = prefs.getString(PROPERTY_REG_ID, "");
		
		if(registration_id.isEmpty())
		{
			return "";
		}
		
		int registered_version = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int current_version = getAppVersion(context);
		if(registered_version!=current_version)
		{
			return "";
		}
		
		return registration_id;
	}
	
	// Function that returns the application version.
	private static int getAppVersion(Context context)
	{
		try
		{
			PackageInfo package_info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return package_info.versionCode;
		}
		catch(NameNotFoundException e)
		{
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
	// Function to register for gcm with the cloud.
	private void registerInBackground()
	{
		dialog = new ProgressDialog(LogIn.this);
        dialog.setMessage("Registering with Google Cloud Servers!");
        dialog.show();
		
		new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params)
			{
				String msg = "";
				try
				{
					if(gcm == null)
					{
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					reg_id = gcm.register(SENDER_ID);
					int appVersion = getAppVersion(context);
					
					SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(PROPERTY_REG_ID, reg_id);
					editor.putInt(PROPERTY_APP_VERSION, appVersion);
					editor.commit();
				}
				catch(IOException e)
				{
					msg = "Error: " + e.getMessage();
				}
				
				return msg;
			}
			
			@Override
			protected void onPostExecute(String msg)
			{
				dialog.dismiss();
			}
		}.execute(null, null, null);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}