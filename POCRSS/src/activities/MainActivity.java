package activities;

import classes.DateFormatter;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.ArticleDao;
import dao.ChannelDao;
import dao.LayoutDao;
import dao.ThemeDao;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity<currentActivity> extends Activity {

	ProgressBar progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		//requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		ImageView image = (ImageView) findViewById(R.id.iconMain);
		image.setImageResource(R.drawable.icon_image);

		// register activity as active
		ActivityDao.addActivity(this);

		// set the dateFormatter
		// static class, so locale has to be initialized only once
		// removed it from article setPubDate because took to much time to load
		// and unload articles
		DateFormatter.setDf();

		new AsyncTaskLoadXML(this).execute();
		
		//this.finish();
	}

	public class AsyncTaskLoadXML extends AsyncTask<Void, Void, Void> {

		private Context context;

		public AsyncTaskLoadXML(Context context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			// run update
			ChannelDao.openDb(context);
			ThemeDao.openDb(context);
			ArticleDao.openDb(context);

			// specify layout
			// get active layout from the database
			String activeLayout = LayoutDao.getActiveLayout(context);

			// layouts for the tablets		
			// the first screen always shows 15 articles.
			if (activeLayout.equals("Tablet 1")
					|| activeLayout.equals("Tablet 2")) {
				Intent intent = new Intent(MainActivity.this,
						TabletHorizontalOverviewActivity.class);
				intent.putExtra("channel_id", "1");
				MainActivity.this.startActivity(intent);
			} else if (activeLayout.equals("Phone 2")) {
				Intent intent = new Intent(MainActivity.this,
						Phone2Activity.class);
				MainActivity.this.startActivity(intent);
			} else if (activeLayout.equals("Phone 3")) {
				Intent intent = new Intent(MainActivity.this,
						Phone3Activity.class);
				MainActivity.this.startActivity(intent);
			} else if (activeLayout.equals("Phone 4")) {
				Intent intent = new Intent(MainActivity.this,
						Phone4Activity.class);
				MainActivity.this.startActivity(intent);
			}
			// else layout is phone 1
			else {
				Intent intent = new Intent(MainActivity.this,
						Phone1Activity.class);
				MainActivity.this.startActivity(intent);
			}
			return null;
		}

	}

	@Override
	public void onBackPressed() {
		
		ActivityDao.finishAllActivities();
	}


}
