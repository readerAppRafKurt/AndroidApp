package activities;

import services.DatabaseHandler;

import classes.Utils;

import com.example.pocrss.R;
import com.example.pocrss.R.layout;
import com.example.pocrss.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PageTurnActivity extends Activity {
	
	
	private Spinner spinnerpageturner;
	private Button btnConfirmSettingPageTurner;
	private DatabaseHandler db;

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
