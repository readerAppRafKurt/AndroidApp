package activities;

import services.ActivityDao;
import services.DatabaseHandler;
import services.FontDao;
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

public class FontsActivity extends Activity {

	private Spinner spinnerFonts;
	private Button btnConfirmSettingsFonts;
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityfonts);

		// register as active
		ActivityDao.addActivity(this);

		spinnerFonts = (Spinner) findViewById(R.id.spinnerfonts);
		btnConfirmSettingsFonts = (Button) findViewById(R.id.btnConfirmSettingsFonts);

		// set spinner for the fonts
		String[] arrayLayouts = getResources().getStringArray(R.array.fonts);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.layouts_spinner_row, R.id.layoutPossibleChoice,
				arrayLayouts);

		spinnerFonts.setAdapter(adapter);

		// search the values for the font in table font in the db
		db = new DatabaseHandler(this);
		String activeFont = db.getActiveFont();

		int position = adapter.getPosition(activeFont);
		spinnerFonts.setSelection(position, true);

		// set listener confirm button
		btnConfirmSettingsFonts.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				
				// get selected font from the spinner
				String selectedFont = spinnerFonts.getSelectedItem().toString();
				
				FontDao.setUtilsFonts(selectedFont);
				db.updateFont(selectedFont);
				
				//set the static list in FontDao
				FontDao.setActiveFont(getBaseContext());

				//finish all active activities
				ActivityDao.finishAllActivities();

				// after selection by the user,go back to the mainpage
				startActivity(new Intent(FontsActivity.this,
						MainActivity.class));
			}

		});

	}
}
