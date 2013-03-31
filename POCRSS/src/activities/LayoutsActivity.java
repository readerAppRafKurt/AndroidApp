package activities;

import services.ActivityDao;
import services.DatabaseHandler;
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

public class LayoutsActivity extends Activity {

	Spinner spinnerLayout;
	Button btnConfirmSettingLayouts;
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layouts);

		// register as active
		ActivityDao.addActivity(this);

		// info
		// http://adanware.blogspot.be/2012/03/android-custom-spinner-with-custom.html
		spinnerLayout = (Spinner) findViewById(R.id.spinnerlayouts);

		// set spinner for the layouts
		String[] arrayLayouts = getResources().getStringArray(R.array.layouts);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.layouts_spinner_row, R.id.layoutPossibleChoice,
				arrayLayouts);

		spinnerLayout.setAdapter(adapter);

		// search the values for the layout in table font in the db
		db = new DatabaseHandler(this);
		String activeLayout = db.getActiveLayout();

		int position = adapter.getPosition(activeLayout);
		spinnerLayout.setSelection(position, true);

		// set button confirm layouts
		btnConfirmSettingLayouts = (Button) findViewById(R.id.btnConfirmSettingLayouts);

		// set listener confirm button
		btnConfirmSettingLayouts.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				// get selected layout from the spinner
				String selectedLayout = spinnerLayout.getSelectedItem()
						.toString();

				// for tablets, the first screen is for both the layouts the
				// same
				// 15 articles are shown
				// in the second screen, the screen for one article, there're 2
				// possibilities
				// tablet 1: whole screen is a scrollview
				// tablet 2: only description is a scrollview

				if (selectedLayout.equals("Phone 2")) {
					// modify db
					db.updateLayout("Phone 2");
				} else if (selectedLayout.equals("Phone 3")) {
					// modify db
					db.updateLayout("Phone 3");
				} else if (selectedLayout.equals("Tablet 1")) {
					// modify db
					db.updateLayout("Tablet 1");
				} else if (selectedLayout.equals("Tablet 2")) {
					// modify db
					db.updateLayout("Tablet 2");
				} else {
					// modify db
					db.updateLayout("Phone 1");
				}

				// finish all active activities
				ActivityDao.finishAllActivities();

				// after selection by the user,go back to the mainpage
				startActivity(new Intent(LayoutsActivity.this,
						MainActivity.class));
			}
		});
	}

}
