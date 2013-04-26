package activities;


import classes.Utils;

import com.example.pocrss.R;

import dao.ActivityDao;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsActivity extends ListActivity {
	
	private TextView tvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitysettings);
		
		//register as active 
		ActivityDao.addActivity(this);
		
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.menu_settings);

		// fill the list with different settings
		// set spinner for the layouts
		String[] settingsList=getResources().getStringArray(R.array.settings);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item_1_mod, settingsList);
		setListAdapter(adapter);

		// selecting single item from settingsList
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					// fonts are clicked
					Intent iFonts = new Intent(SettingsActivity.this,
							FontsActivity.class);
					startActivity(iFonts);
					break;
				case 1:
					// themes are clicked
					Intent iThemes = new Intent(SettingsActivity.this,
							ThemesActivity.class);
					startActivity(iThemes);
					break;
				case 2:
					// layouts are clicked
					Intent iLayouts = new Intent(SettingsActivity.this,
							LayoutsActivity.class);
					startActivity(iLayouts);
					break;
				case 3:
					// Effects are clicked
					Intent iEffects = new Intent(SettingsActivity.this,
							PageTurnActivity.class);
					startActivity(iEffects);
					break;
				}
			}
		});
	}
}
