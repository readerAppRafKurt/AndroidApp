package activities;

import java.util.List;
import services.ActivityDao;
import services.DatabaseHandler;
import services.FontDao;
import services.ThemeDao;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Phone1Activity extends Activity {

	DatabaseHandler db;
	
	int[] activeFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// set the database
		db = new DatabaseHandler(Phone1Activity.this);
		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(db.getActiveFont());
		ThemeDao.setUtilsTheme(db.getActiveTheme());

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone1);
		
		//get active font
		activeFont=FontDao.getLayoutXML();

		// register activity as active
		ActivityDao.addActivity(this);
		
		 // setup for button search articles and button overview articles
		 Button btnSearch = (Button) findViewById(R.id.btnSearchArticles);
		 btnSearch.setOnClickListener(new OnClickListener() {
		 
		 public void onClick(View arg0) {
		 Intent intent = new Intent(Phone1Activity.this,
		 SearchArticleActivity.class);
		 Phone1Activity.this.startActivity(intent); } });
		 

		// get all active channels
		// info
		// http://stackoverflow.com/questions/5763366/how-to-create-multiple-buttons-at-runtime-android
		List<Channel> activeChannels = db.getAllSelectedChannels();

		LinearLayout internLayout = (LinearLayout) findViewById(R.id.layoutInternPhone1);
		// get the position of the screen 1=portrait 2=landscape
		LayoutParams lp;
		int orientation = getResources().getConfiguration().orientation;

		if (orientation == 1) {
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, 30);
		} else {
			lp = new LayoutParams(150, LayoutParams.MATCH_PARENT);
		}

		lp.setMargins(5, 5, 5, 5);

		for (Channel channel : activeChannels) {
			// for each active channel create a clickable textview
			TextView tv = (TextView)getLayoutInflater().inflate(activeFont[0], null);
			tv.setText(channel.getDescription().substring(15));
			tv.setTag(Integer.toString(channel.get_id()));

			String activeThemeColor = db.getActiveTheme();

			if (activeThemeColor.equalsIgnoreCase("grijs")) {
				tv.setBackgroundColor(Color.parseColor("#666666"));
			} else if (activeThemeColor.equalsIgnoreCase("blauw")) {
				tv.setBackgroundColor(Color.parseColor("#6D929B"));
			} else if (activeThemeColor.equalsIgnoreCase("rood")) {
				// theme is rood
				tv.setBackgroundColor(Color.parseColor("#FF0000"));
			}
			// else theme is default

			tv.setPadding(3, 3, 3, 3);
			tv.setGravity(Gravity.CENTER);

			tv.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// only show the articles for the selected theme
					// start new intent and pass the id of the selected channel
					// Intent intent = new Intent(MainActivity.this,
					// ListTitlesArticleForSingleThemeActivity.class);

					Intent intent = new Intent(Phone1Activity.this,
							ListTitlesAndBeginForSingleThemeActivity.class);

					String sTag=(String) v.getTag();

					intent.putExtra("channelId", sTag);
					Phone1Activity.this.startActivity(intent);
				}
			});

			internLayout.addView(tv);
			tv.setLayoutParams(lp);

		}
		// end textviews at runtime
	}	
		
	// setup for the general preferences of the app.
	// users can modify their font, layout, theme and effects
	// users can choose out of different article channels
	// setup via a menu in the mainActvity
	// uses onOptionsItemSelected for the method implementation
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater menuInflator = getMenuInflater();
		menuInflator.inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.choiceFeedsMenu:
			// setup new activity and intent to setup the selected feeds
			Intent i = new Intent(Phone1Activity.this,
					RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(Phone1Activity.this, SettingsActivity.class);
			startActivity(ii);

			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		
		//empty the active activities
		ActivityDao.finishAllActivities();
		
		super.onBackPressed();
	}
}
