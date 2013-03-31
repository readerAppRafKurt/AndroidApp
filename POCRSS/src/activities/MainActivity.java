package activities;

import services.ActivityDao;
import services.DatabaseHandler;
import services.FontDao;
import services.ThemeDao;
import classes.DateFormatter;
import classes.Utils;
import com.example.pocrss.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity<currentActivity> extends Activity {

	ProgressBar progress;
	DatabaseHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set the database
		db = new DatabaseHandler(MainActivity.this);

		// register activity as active
		ActivityDao.addActivity(this);

		// set the dateFormatter
		// static class, so locale has to be initialized only once
		// removed it from article setPubDate because took to much time to load
		// and unload articles
		DateFormatter.setDf();

		// start background service
		// new AsyncTaskLoadXML(getBaseContext()).execute();

		// specify layout
		// get active layout from the database
		String activeLayout = db.getActiveLayout();

		/*
		 * //run update for the fonts FontDao.setActiveFont(getBaseContext());
		 * FontDao.setUtilsFonts(db.getActiveFont());
		 * ThemeDao.setUtilsTheme(selectedTheme);
		 */

		// layouts for the tablets
		// the first screen always shows 15 articles.
		if (activeLayout.equals("Tablet 1") || activeLayout.equals("Tablet 2")) {
			Intent intent = new Intent(MainActivity.this,
					TabletHorizontalOverviewActivity.class);
			intent.putExtra("channel_id", "1");
			MainActivity.this.startActivity(intent);
		} else if (activeLayout.equals("Phone 2")) {
			Intent intent = new Intent(MainActivity.this, Phone2Activity.class);
			MainActivity.this.startActivity(intent);
		} else if (activeLayout.equals("Phone 3")) {
			Intent intent = new Intent(MainActivity.this, Phone3Activity.class);
			MainActivity.this.startActivity(intent);
		}
		// else layout is phone 1
		else {
			// Intent intent = new Intent(MainActivity.this,
			// Phone1Activity.class);
			Intent intent = new Intent(MainActivity.this, Phone1Activity.class);
			MainActivity.this.startActivity(intent);
		}

		// test available space imageDao
		// ImageDao.availableSpaceStorage();
		//

		Log.w("test1", "ik zit in main");
		MainActivity.this.finish();
	}
	/*
	 * public class AsyncTaskLoadXML extends AsyncTask<Void, Void, Void> {
	 * 
	 * private Context context;
	 * 
	 * public AsyncTaskLoadXML(Context context) { this.context=context; }
	 * 
	 * @Override protected Void doInBackground(Void... params) { //run update
	 * for the fonts FontDao.setActiveFont(context);
	 * Log.w("test1","ik zit in backing van main"); return null; }
	 * 
	 * }
	 */
}
