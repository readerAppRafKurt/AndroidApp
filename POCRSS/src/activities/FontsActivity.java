package activities;

import classes.Utils;
import com.example.pocrss.R;

import dao.ActivityDao;
import dao.FontDao;
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

public class FontsActivity extends Activity {

	private Spinner spinnerFonts;
	private Button btnConfirmSettingsFonts;
	private TextView tvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityfonts);

		// register as active
		ActivityDao.addActivity(this);

		spinnerFonts = (Spinner) findViewById(R.id.spinnerfonts);
		btnConfirmSettingsFonts = FontDao.modifiedButton((Button) findViewById(R.id.btnConfirmSettingsFonts), getBaseContext());
			
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.choosefont);
				
		// set spinner for the fonts
		String[] arrayLayouts = getResources().getStringArray(R.array.fonts);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.layouts_spinner_row, R.id.layoutPossibleChoice,
				arrayLayouts);

		spinnerFonts.setAdapter(adapter);

		// search the values for the font in table font in the db
		String activeFont=FontDao.getActiveFont();

		int position = adapter.getPosition(activeFont);
		spinnerFonts.setSelection(position, true);

		// set listener confirm button
		btnConfirmSettingsFonts.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				
				// get selected font from the spinner
				String selectedFont = spinnerFonts.getSelectedItem().toString();
				
				FontDao.setUtilsFonts(selectedFont);
				FontDao.updateFont(selectedFont);
				
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
