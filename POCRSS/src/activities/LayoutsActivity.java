package activities;

import classes.Utils;
import com.example.pocrss.R;

import dao.ActivityDao;
import dao.FontDao;
import dao.LayoutDao;
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

public class LayoutsActivity extends Activity {

	Spinner spinnerLayout;
	Button btnConfirmSettingLayouts;
	private TextView tvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layouts);

		// register as active
		ActivityDao.addActivity(this);

		// set new content titleBar
		tvTitleBar = (TextView) findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.chooselayouts);

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
		String activeLayout = LayoutDao.getActiveLayout(this);

		int position = adapter.getPosition(activeLayout);
		spinnerLayout.setSelection(position, true);

		// set button confirm layouts
		// btnConfirmSettingLayouts = (Button)
		// findViewById(R.id.btnConfirmSettingLayouts);

		btnConfirmSettingLayouts = FontDao.modifiedButton(
				(Button) findViewById(R.id.btnConfirmSettingLayouts),
				getBaseContext());

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
					LayoutDao.updateLayout("Phone 2");
				} else if (selectedLayout.equals("Phone 3")) {
					// modify db
					LayoutDao.updateLayout("Phone 3");
				} else if (selectedLayout.equals("Phone 4")) {
					// modify db
					LayoutDao.updateLayout("Phone 4");
				} else if (selectedLayout.equals("Tablet 1")) {
					// modify db
					LayoutDao.updateLayout("Tablet 1");
				} else if (selectedLayout.equals("Tablet 2")) {
					// modify db
					LayoutDao.updateLayout("Tablet 2");
				} else {
					// modify db
					LayoutDao.updateLayout("Phone 1");
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
