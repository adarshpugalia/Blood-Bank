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
import android.widget.RelativeLayout;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// Show the Up button in the action bar.
		setupActionBar();
		
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
		String name = sharedPref.getString("Name", "");
		setTitle(name);
		setLayout();
		
	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
	
	public void askBlood(View view)
	{
		Intent intent = new Intent(this, AskBlood.class);
		startActivity(intent);
	}
	
	public void donateBlood(View view)
	{
		Intent intent = new Intent(this, DonateBlood.class);
		startActivity(intent);
	}
	
	public void editProfile(View view)
	{
		Intent intent = new Intent(this, EditProfile.class);
		startActivity(intent);
	}
	
	public void setLayout()
	{
		// getting screen width
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int height = metrics.heightPixels;
				
		// setting width for first name field
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)findViewById(R.id.text_ask).getLayoutParams();
		param.height = (int)(height/12);
		findViewById(R.id.text_ask).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_donate).getLayoutParams();
		param.height = (int)(height/12);
		findViewById(R.id.text_donate).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_profile).getLayoutParams();
		param.height = (int)(height/12);
		findViewById(R.id.text_profile).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_blood_banks).getLayoutParams();
		param.height = (int)(height/12);
		findViewById(R.id.text_blood_banks).setLayoutParams(param);
		
		param = (RelativeLayout.LayoutParams)findViewById(R.id.text_settings).getLayoutParams();
		param.height = (int)(height/12);
		findViewById(R.id.text_settings).setLayoutParams(param);
	}

}
