package taadnairsshha.apps.bloodbank;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfile extends Activity {

	
	
	TextView tv1,tv2,tv3;
	EditText et1,et2,et3;
	int year,month,day;
	private DatePicker dpResult;
	static final int DATE_DIALOG_ID = 999;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        tv1 = (TextView) findViewById(R.id.tv_update_phone);
        tv2 = (TextView) findViewById(R.id.tv_count_donate);
        tv3 = (TextView) findViewById(R.id.tv_last_donate);
        et1 = (EditText) findViewById(R.id.et_update_phone);
        et2 = (EditText) findViewById(R.id.et_count_donate);
        et3 = (EditText) findViewById(R.id.et_lastdate);
        
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
				
			}
		});
        
    }
    
    
    
    
    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
    	
    	switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year,month,day);
		}
		return null;
	}
    
    private DatePickerDialog.OnDateSetListener datePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
public void onDateSet(DatePicker view, int selectedYear,
	int selectedMonth, int selectedDay) {
year = selectedYear;
month = selectedMonth;
day = selectedDay;

// set selected date into textview
et3.setText(new StringBuilder().append(month + 1)
   .append("-").append(day).append("-").append(year)
   .append(" "));

// set selected date into datepicker also
dpResult.init(year, month, day, null);

}
};




	public void update(){
    	SharedPreferences update = getSharedPreferences("update",MODE_PRIVATE);
        Editor e = update.edit();
        e.remove("phone");
        e.putString("phone",et1.getText().toString());
        e.putString("count",et2.getText().toString());
        e.commit();
    }
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
