package activities;

import services.ActivityDao;
import services.DatabaseHandler;
import services.ThemeDao;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ThemesActivity extends Activity {

	private Spinner spinnerThemes;
	private Button btnConfirmSettingThemes;
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitythemes);
		
		//register as active 
		ActivityDao.addActivity(this);
		
		//search for the active theme
		//used to preselect spinner
		db=new DatabaseHandler(this);
		String themeNameId=db.getActiveTheme();

		// info:http://www.androidengineer.com/2010/06/using-themes-in-android-applications.html
		// http://stackoverflow.com/questions/3241729/android-dynamically-change-style-at-runtime
		// http://www.androidseeker.blogspot.in/2012/09/how-to-apply-new-theme-to-whole.html

		spinnerThemes = (Spinner) findViewById(R.id.spinnerthemes);

		// set spinner for the themes
		String[] arrayLayouts=getResources().getStringArray(R.array.themes);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		         R.layout.layouts_spinner_row, R.id.layoutPossibleChoice, arrayLayouts);
		
		spinnerThemes.setAdapter(adapter);	
		int position = adapter.getPosition(themeNameId);
		spinnerThemes.setSelection(position, true);
		
		
		

		// set button confirm themes
		btnConfirmSettingThemes = (Button) findViewById(R.id.btnConfirmSettingThemes);

		// set listener confirm button
		btnConfirmSettingThemes.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				// get selected theme from the spinner
				String selectedTheme = spinnerThemes.getSelectedItem()
						.toString();

				ThemeDao.setUtilsTheme(selectedTheme);
				/*
				if (selectedTheme.equals("Grijs")) {
					Utils.THEME = "Gray";
					Utils.settingChanged = true;
					Utils.SIZE = "LARGE";
					
					// modify db
					db.updateTheme("Grijs");
				} else if (selectedTheme.equals("Blauw")) {
					Utils.THEME = "Radial";
					Utils.settingChanged = true;
					Utils.SIZE = "SMALL";
					
					// modify db
					db.updateTheme("Blauw");
				} else {
					//Rood
					Utils.THEME = "DEFAULT";
					Utils.settingChanged = true;
					Utils.SIZE = "DEFAULT";
					
					// modify db
					db.updateTheme("Rood");
				}
				*/
				// modify db
				db.updateTheme(selectedTheme);

				//finish all active activities
				ActivityDao.finishAllActivities();

				// after selection by the user,go back to the mainpage
				startActivity(new Intent(ThemesActivity.this,
						MainActivity.class));
			}

		});

	}
}