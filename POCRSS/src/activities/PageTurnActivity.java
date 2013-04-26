package activities;


//not implemented
import services.DatabaseHandler;

import classes.Utils;

import com.example.pocrss.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PageTurnActivity extends Activity {
	
	
	private Spinner spinnerpageturner;
	@SuppressWarnings("unused")
	private Button btnConfirmSettingPageTurner;
	@SuppressWarnings("unused")
	private DatabaseHandler db;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitypageturn);
		
		
		spinnerpageturner = (Spinner) findViewById(R.id.spinnerpageturner);

		// set spinner for page turn effect
		ArrayAdapter adapter = (ArrayAdapter) spinnerpageturner.getAdapter();
		
		int position = adapter.getPosition(1);
		spinnerpageturner.setSelection(position, true);

		// set button confirm Turn page effects
		btnConfirmSettingPageTurner = (Button) findViewById(R.id.btnConfirmSettingPageTurner);
		
	}

}
