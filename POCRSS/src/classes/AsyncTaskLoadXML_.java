package classes;

import java.util.List;
import services.DatabaseHandler;
import services.UploadXML;
import activities.ListTitlesAndBeginForSingleThemeActivity;
import activities.TabletHorizontalOverviewActivity;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class AsyncTaskLoadXML_ extends AsyncTask<String, Integer, Void> {
	// info progressbar
	// http://www.mysamplecode.com/2012/11/android-progress-bar-example.html
	private ProgressBar progress;
	// DatabaseHandler db;
	List<Channel> channels;
	private Context context;
	private Activity activity;

	public AsyncTaskLoadXML_(Context context, ProgressBar progress,
			DatabaseHandler db, Activity activity) {
		this.progress = progress;
		// get all the selected rss feeds from the db
		channels = db.getAllSelectedChannels();
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		// control if device has connection
		UploadXML.setDeviceHasConnection(context);

		// show progressbar and start at zero
		// control if device has connection and not busy
		if (UploadXML.isAlreadyBusy() == false
				&& UploadXML.isDeviceHasConnection() == true) {
			progress.setVisibility(View.VISIBLE);
			progress.setProgress(0);
		}
	}

	@Override
	protected Void doInBackground(String... arg0) {
		// TODO oude artikels eruit smijten bij het afsluiten van de app
		// steeds laatste 15 artikels van de channel laten staan

		try {
			UploadXML.canUploadStart(activity, this.context, this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// start test for foreground activity
		ActivityManager mActivityManager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
				.getRunningTasks(1);
		ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
		String activeActivity = ar.topActivity.getClassName().toString();

		Log.w("test", "voor Async: " + activeActivity);
		
		UploadXML.setRefreshScreenPause(false);
		//

		// if the user is currently in the overviewTabletScreen,
		// restart the activity programmatically
		if (activeActivity
				.equals("activities.TabletHorizontalOverviewActivity")) {
			TabletHorizontalOverviewActivity tabletHorizontalOverviewActivity = (TabletHorizontalOverviewActivity) activity;
			tabletHorizontalOverviewActivity.restart();
		} 
		else if (activeActivity
				.equals("activities.ListTitlesAndBeginForSingleThemeActivity")) {
			Log.w("test", "in Async");
			((ListTitlesAndBeginForSingleThemeActivity) activity).refreshScreen();
		}
		// todo the same for the phones
		// end test for foreground activity
		
		

		// thread has to run in main otherwise error
		activity.runOnUiThread(new Runnable() {
			public void run() {
				// set progressbar at 100% and remove progressbar
				progress.setProgress(100);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progress.setVisibility(View.GONE);
			}
		});

		// thread has to be idle for 900000 milliseconds/15 minutes
		for (int i = 0; i < 9; i++) {
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/*
		 * //als test een kortere periode nemen try { Thread.sleep(5000); }
		 * catch (InterruptedException e) { e.printStackTrace(); } //end test
		 * kortere periode
		 */
		// open static class for other demands
		UploadXML.setAlreadyBusy(false);

		return null;
	}

	public void doProgress(int value) {
		publishProgress(value);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		progress.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		//update the screens 
		
	}
	
	
	
}
