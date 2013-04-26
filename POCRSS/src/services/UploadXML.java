package services;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import classes.AsyncTaskLoadXML_;
import classes.Channel;
import dao.ArticleDao;

public class UploadXML {

	private static boolean alreadyBusy,deviceHasConnection,refreshScreenPause;
	
	//private static Activity sActivity;

	static DatabaseHandler db;

	public static void canUploadStart(Activity activity, Context context,
			AsyncTaskLoadXML_ asyncTaskLoadXML) throws Exception{

		//sActivity = activity;

		// verify timer
		// after 15 minutes look for new xml file
		if (alreadyBusy == false) {

			// the start of the upload
			// set alreadyBusy to true so no other upload can be done for 15
			// minutes
			alreadyBusy = true;
			refreshScreenPause=true;
					
			// verify if device has internet connection
			setDeviceHasConnection(context);

			if (deviceHasConnection) {

				// set the database
				db = new DatabaseHandler(context);

				// get active channels
				List<Channel> channels = db.getAllSelectedChannels();

				//progressbar in elke activity zetten
				//enkel in de acitvities van de artikels zetten
				//daar zit de gebruiker het langst
				// set progressbar to see progress download XML files, but do
				// not show if device has no internet connection

				for (int i = 0; i < channels.size(); i++) {
					// Call static class and read all the articles in the
					// channels
					ArticleDao.initialize(channels.get(i), context);
					// write those articles to the db
					ArticleDao.updateDb(ArticleDao.getArticles(), db);
					asyncTaskLoadXML.doProgress((int) ((i + 1) * 100 / channels
							.size()));
				}
				/*
				// thread has to be idle for 900000 milliseconds/15 minutes
				for (int i = 0; i < 9; i++) {
					try {
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				alreadyBusy = false;*/
			}
		}

	}

	public static boolean isAlreadyBusy() {
		return alreadyBusy;
	}

	public static void setAlreadyBusy(boolean alreadyBusy) {
		UploadXML.alreadyBusy = alreadyBusy;
	}

	public static boolean isDeviceHasConnection() {
		return deviceHasConnection;
	}

	public static void setDeviceHasConnection(Context context) {
		// verify if device has internet connection
		// http://www.vogella.com/articles/AndroidBackgroundProcessing/article.html
		InternetConnectionDetector internetConnectionDetector = new InternetConnectionDetector(
				context);
		UploadXML.deviceHasConnection = internetConnectionDetector
				.isConnectedToInternet();
	}

	public static boolean isRefreshScreenPause() {
		return refreshScreenPause;
	}

	public static void setRefreshScreenPause(boolean refreshScreenPause) {
		UploadXML.refreshScreenPause = refreshScreenPause;
	}
	
	
	
}
