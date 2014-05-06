package com.example.android.navigationdrawerexample;




import com.example.database.DatabaseAdapter;
import com.example.model.DoctorProfile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	public void checkPersonnelNumber(View view){
		DoctorProfile doctor = new DoctorProfile();
		EditText personnel_number_text = (EditText) findViewById(R.id.textbox_personnel_number);
		String personnel_number = personnel_number_text.getText().toString();
		//Toast.makeText(this, personnel_number, Toast.LENGTH_SHORT).show();
		
		DatabaseAdapter db = new DatabaseAdapter(this);
		try{
			if(db.ifExists(personnel_number)){
				doctor = db.getDoctor(personnel_number);
				String display = doctor.getDoctorFirstName() + " " + doctor.getDoctorLastName();
				//Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, ValidateDoctor.class);
				Bundle extras = new Bundle();
				extras.putString("PERSONNEL_NUMBER", personnel_number);
				extras.putString("FIRSTNAME", doctor.getDoctorFirstName());
				extras.putString("LASTNAME", doctor.getDoctorLastName());
				intent.putExtras(extras);
				startActivity(intent);
			}
			else{
				//placeholder text
				Toast.makeText(this, "Doctor does not exist", Toast.LENGTH_SHORT).show();
			}
		}
		catch(Exception e){
			System.out.println(e);
			Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void checkClick(View view){
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			return rootView;
		}
	}

}
