package activities;


import classes.Utils;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.FontDao;
import dao.ThemeDao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ThemesActivity extends Activity {

	private Spinner spinnerThemes;
	private Button btnConfirmSettingThemes;
	private TextView tvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitythemes);
		
		//register as active 
		ActivityDao.addActivity(this);
		
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.choosetheme);
		
		//search for the active theme
		//used to preselect spinner
		String themeNameId=ThemeDao.getActiveTheme();

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
		//btnConfirmSettingThemes = (Button) findViewById(R.id.btnConfirmSettingThemes);
		btnConfirmSettingThemes = FontDao.modifiedButton((Button) findViewById(R.id.btnConfirmSettingThemes), getBaseContext());

		// set listener confirm button
		btnConfirmSettingThemes.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				// get selected theme from the spinner
				String selectedTheme = spinnerThemes.getSelectedItem()
						.toString();

				ThemeDao.setUtilsTheme(selectedTheme);

				// modify db
				ThemeDao.updateTheme(selectedTheme);

				//finish all active activities
				ActivityDao.finishAllActivities();

				// after selection by the user,go back to the mainpage
				startActivity(new Intent(ThemesActivity.this,
						MainActivity.class));
			}

		});

	}
}