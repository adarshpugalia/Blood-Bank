package taadnairsshha.apps.bloodbank;

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

public class NewUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		
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
		getMenuInflater().inflate(R.menu.new_user, menu);
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
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_first_name).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_first_name).setLayoutParams(param);
		
		// setting width for last name field
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_last_name).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_last_name).setLayoutParams(param);
		
		// setting width for age field
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_age).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_age).setLayoutParams(param);
		
		// creating and setting width for blood group drop down
		Spinner spinner = (Spinner)findViewById(R.id.spinner_blood_groups);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.blood_groups, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.spinner_blood_groups).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.spinner_blood_groups).setLayoutParams(param);
		
		// setting width for primary contact number
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_primary_contact).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_primary_contact).setLayoutParams(param);
		
		// setting width for secondary contact number
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_secondary_contact).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_secondary_contact).setLayoutParams(param);
		
		// setting width for city and state fields.
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_address_city).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_address_city).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_address_state).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_address_state).setLayoutParams(param);
		
	}
	
	// function called when register button is pressed
	public void register(View view)
	{
		EditText edit = (EditText)findViewById(R.id.edit_first_name);
		String first_name = edit.getText().toString();
		
		edit = (EditText)findViewById(R.id.edit_last_name);
		String last_name = edit.getText().toString();
		
		edit = (EditText)findViewById(R.id.edit_primary_contact);
		String phone = edit.getText().toString();
		
		if(first_name.length()!=0 && last_name.length()!=0 && phone.length()!=0)
		{
			SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(getString(R.string.hint_first_name), first_name);
			editor.putString(getString(R.string.hint_last_name), last_name);
			editor.putString(getString(R.string.user_phone), phone);
			editor.commit();
						
			// starting the home activity.
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
