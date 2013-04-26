package classes;

import java.util.List;
import services.DatabaseHandler;
import services.UploadXML;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import dao.ActivityDao;
import dao.ArticleDao;
import dao.ChannelDao;

public class AsyncTaskUpdateDbWithoutProgressBar extends
		AsyncTask<String, Integer, Void> {
	List<Channel> channels;
	private Context context;
	private Activity activity;

	public AsyncTaskUpdateDbWithoutProgressBar(Context context,
			Activity activity) {
		// get all the selected rss feeds from the db
		channels = ChannelDao.getAllSelectedChannels();
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... arg0) {

		try {
			UploadXML.setAlreadyBusy(true);
			UploadXML.setRefreshScreenPause(true);

			// verify if device has internet connection
			UploadXML.setDeviceHasConnection(context);

			if (UploadXML.isDeviceHasConnection()) {

				// set the database
				DatabaseHandler db = new DatabaseHandler(context);

				// get active channels
				List<Channel> channels = db.getAllSelectedChannels();

				for (int i = 0; i < channels.size(); i++) {
					// Call static class and read all the articles in the
					// channels
					ArticleDao.initialize(channels.get(i), context);
					// write those articles to the db
					ArticleDao.updateDb(ArticleDao.getArticles(), db);
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		UploadXML.setRefreshScreenPause(false);

		/*
		 * //TODO start update the current turning/visible activity // start
		 * test for foreground activity ActivityManager mActivityManager =
		 * (ActivityManager) activity
		 * .getSystemService(Context.ACTIVITY_SERVICE);
		 * List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
		 * .getRunningTasks(1); ActivityManager.RunningTaskInfo ar =
		 * RunningTask.get(0);
		 * 
		 * 
		 * String activeActivity = ar.topActivity.getClassName().toString();
		 */

		ActivityDao.RestartCurrentForegroundActivity(activity);

		/*
		 * // if the user is currently in the overviewTabletScreen, // restart
		 * the activity programmatically if (activeActivity
		 * .equals("activities.TabletHorizontalOverviewActivity")) {
		 * TabletHorizontalOverviewActivity tabletHorizontalOverviewActivity =
		 * (TabletHorizontalOverviewActivity) activity;
		 * tabletHorizontalOverviewActivity.restart(); } else if (activeActivity
		 * .equals("activities.ListTitlesAndBeginForSingleThemeActivity")) {
		 * Log.w("test", "in Async");
		 * ((ListTitlesAndBeginForSingleThemeActivity)
		 * activity).refreshScreen(); } // todo the same for the phones // end
		 * test for foreground activity
		 */

		// end update the current turning/visible activity

		// thread has to be idle for 900000 milliseconds/15 minutes
		// for test only 1,5 m
		for (int i = 0; i < 9; i++) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// open static class for other demands
		UploadXML.setAlreadyBusy(false);

		return null;
	}
}
