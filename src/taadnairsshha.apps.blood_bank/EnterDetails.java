package taadnairsshha.apps.bloodbank;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_details);
		
		// setting layout parameters dynamically.
		setLayout();
				
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setLayout()
	{
		// getting screen width
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		
		// setting width for first name field
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)findViewById(R.id.text_name).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_name).setLayoutParams(param);
		
		// setting width for last name field
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_name).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_name).setLayoutParams(param);
		
		// setting width for age field
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_password).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_password).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_password).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_password).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_blood_group).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_blood_group).setLayoutParams(param);
		
		// creating and setting width for blood group drop down
		Spinner spinner = (Spinner)findViewById(R.id.spinner_blood_groups);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.blood_groups, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.spinner_blood_groups).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.spinner_blood_groups).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_city).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_city).setLayoutParams(param);
		
		// setting width for city and state fields.
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_city).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_city).setLayoutParams(param);
	}
	
	public void register(View view)
	{
		Intent intent = getIntent();
		String phone = intent.getStringExtra(IncomingSms.EXTRA_MESSAGE);
		
		EditText edit = (EditText)findViewById(R.id.edit_name);
		String name = edit.getText().toString();
		
		edit = (EditText)findViewById(R.id.edit_password);
		String password = edit.getText().toString();
		
		Spinner group = (Spinner)findViewById(R.id.spinner_blood_groups);
		String blood_group = group.getSelectedItem().toString();
		
		edit = (EditText)findViewById(R.id.edit_city);
		String city = edit.getText().toString();
		
		SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
		String id = prefs.getString("registration_id", "");
		
		if(name.equals("") || password.equals("") || blood_group.equals("") || city.equals(""))
			Toast.makeText(getApplicationContext(), "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
		else
		{
			try
			{
				String reply = new SendRequest().execute("register.php", "6", "Id", id, "Phone", phone, "Name", name, "Password", password, "BloodGroup", blood_group, "City", city).get();
				//Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_LONG).show();
				
				String[] split = reply.split("#");
				reply = split[0];
				
				if(reply.equals("0"))
				{
					Toast.makeText(getApplicationContext(), "This number is already registered!", Toast.LENGTH_SHORT).show();
					Intent intent_login = new Intent(this, LogIn.class);
					startActivity(intent_login);
				}
				else if(reply.equals("1"))
				{
					Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
					
					SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("Phone", phone);
					editor.putString("Name", name);
					editor.putString("Blood Group", blood_group);
					editor.putString("City", city);
					editor.commit();
								
					// starting the home activity.
					Intent intent_home = new Intent(this, Home.class);
					startActivity(intent_home);
				}
				else
					Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_SHORT).show();
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
	}

}
