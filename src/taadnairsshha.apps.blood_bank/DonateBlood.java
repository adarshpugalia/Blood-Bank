package taadnairsshha.apps.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DonateBlood extends Activity {

	
	LinearLayout l1;
	TextView tv,tv_line;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate_blood);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		l1 = (LinearLayout) findViewById(R.id.layout_donate);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		SharedPreferences prefs = getSharedPreferences("DonateBlood", Context.MODE_PRIVATE);
		if(prefs.contains("Count"))
		{
			int count = prefs.getInt("Count", 0);
			String message = prefs.getString("donate"+String.valueOf(count), "");
			String[] info = message.split("\\$");
			tv = new TextView(this);
			tv.setText(info[0]+" needs blood!!");
			tv.setId(1);
			tv.setLayoutParams(param);
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int index = tv.getId();
					//Intent i = new Intent(DonateBlood.this, AskerInfo.class);
					//i.putExtra("index", index);
					//startActivity(i);
					
					
				}
			});
			l1.addView(tv);
			//tv_line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,3));
			//tv_line.setBackgroundColor(Color.BLACK);
			//l1.addView(tv_line);
		
		
		
		
		
		
		}
		else{
			tv = new TextView(this);
			tv.setText("No Blood Required!");
			tv.setLayoutParams(param);
			l1.addView(tv);
		}
			
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
		getMenuInflater().inflate(R.menu.donate_blood, menu);
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

}
