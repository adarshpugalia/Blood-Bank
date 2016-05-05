package taadnairsshha.apps.bloodbank;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

@SuppressLint("NewApi")
public class AskBlood extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_blood);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setLayout();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar(){

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ask_blood, menu);
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
		int height = metrics.heightPixels;
		
		// setting width for first name field
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)findViewById(R.id.text_blood_groups).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_blood_groups).setLayoutParams(param);
		
		// creating and setting width for blood group drop down
		Spinner spinner = (Spinner)findViewById(R.id.spinner_blood_groups);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.blood_groups, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.spinner_blood_groups).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.spinner_blood_groups).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_contact).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.text_contact).setLayoutParams(param);
		
		// setting width for city and state fields.
		param = (RelativeLayout.LayoutParams)findViewById(R.id.edit_contact).getLayoutParams();
		param.width = (int)(0.45*width);
		findViewById(R.id.edit_contact).setLayoutParams(param);
		
		SharedPreferences shared_pref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
		String phone = shared_pref.getString("Phone", "");
		phone = phone.substring(3);
		EditText edit = (EditText)findViewById(R.id.edit_contact);
		edit.setText(phone);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_space1).getLayoutParams();
		param.height = (int)(0.07*height);
		findViewById(R.id.text_space1).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_space2).getLayoutParams();
		param.height = (int)(0.07*height);
		findViewById(R.id.text_space2).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_space3).getLayoutParams();
		param.height = (int)(0.07*height);
		findViewById(R.id.text_space3).setLayoutParams(param);
		
	}
	
	public void askBlood(View view)
	{
		Spinner group = (Spinner)findViewById(R.id.spinner_blood_groups);
		String blood_group = group.getSelectedItem().toString();
		
		EditText edit_address = (EditText)findViewById(R.id.edit_address);
		String address = edit_address.getText().toString();
		
		EditText edit_secondary_contact = (EditText)findViewById(R.id.edit_contact);
		String contact = "+91" + edit_secondary_contact.getText().toString();
		
		if(contact.length()==13)
		{
			try
			{
				SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
				String name = prefs.getString("Name", "");
				String id = prefs.getString("Id", "");
				String city = prefs.getString("City", "");
				
				String message = name+"$"+address+"$"+contact;
				String reply = "";
				reply = new SendRequest().execute("ask_blood.php", "4", "Id", id, "BloodGroup", blood_group, "City", city, "Message", message).get();
				
				//Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_SHORT).show();
				String[] split = reply.split("#");
				reply = split[0];
				
				if(reply.equals("0"))
					Toast.makeText(getApplicationContext(), "No users found with the required Blood Group!", Toast.LENGTH_SHORT).show();
				else if(reply.equals("1"))
					Toast.makeText(getApplicationContext(), "Users Notified!", Toast.LENGTH_SHORT).show();
				else if(reply.equals("2"))
					Toast.makeText(getApplicationContext(), "Server Error! Please Try Later!", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), "Please enter a valid number!", Toast.LENGTH_SHORT).show();
	}

}
