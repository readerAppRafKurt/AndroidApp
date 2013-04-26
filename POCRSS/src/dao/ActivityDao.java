package dao;

import java.util.ArrayList;
import java.util.List;
import activities.ListTitlesAndBeginForSingleThemeActivity;
import activities.Phone1Activity;
import activities.Phone2Activity;
import activities.Phone3Activity;
import activities.Phone4Activity;
import activities.TabletHorizontalOverviewActivity;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

//class to keep all the active activities
//needed to finish all active activities when the settings are changed by the user
//and the app goes back to the startpage
public class ActivityDao {

	private static ArrayList<Activity> listActiveActivities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		listActiveActivities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		listActiveActivities.remove(activity);
	}

	public static void finishAllActivities() {
		// finish all the activities
		for (Activity a : listActiveActivities) {
			a.finish();
		}

		// empty the list of active activities
		listActiveActivities.clear();
	}

	public static void RestartCurrentForegroundActivity(Activity activity) {

		// start test for foreground activity
		ActivityManager mActivityManager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
				.getRunningTasks(1);
		ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
		String activeActivity = ar.topActivity.getClassName().toString();
		
		
		//restart activities that contain a sort of overview for the articles
		//Phone1Activity, Phone2Activity, Phone3Activity, Phone4Activity, InPhone4Activity, ListTitlesAndBeginForSingleThemeActivity, TabletHorizontalOverviewActivity
		

		// if the user is currently in the overviewTabletScreen,
		// restart the activity programmatically
		if (activeActivity
				.equals("activities.TabletHorizontalOverviewActivity")) {
			TabletHorizontalOverviewActivity tabletHorizontalOverviewActivity = (TabletHorizontalOverviewActivity) activity;
			tabletHorizontalOverviewActivity.restart();
		} 
		else if (activeActivity
				.equals("activities.ListTitlesAndBeginForSingleThemeActivity")) {
			((ListTitlesAndBeginForSingleThemeActivity) activity).refreshScreen();
		}
		else if (activeActivity
				.equals("activities.Phone1Activity")) {		
			Intent intent=new Intent(activity,Phone1Activity.class);
			((Phone1Activity) activity).startActivity(intent);
		}
		else if (activeActivity
				.equals("activities.Phone2Activity")) {		
			Intent intent=new Intent(activity,Phone2Activity.class);
			((Phone2Activity) activity).startActivity(intent);
		}
		
		else if (activeActivity
				.equals("activities.Phone3Activity")) {		
			Intent intent=new Intent(activity,Phone3Activity.class);
			((Phone3Activity) activity).startActivity(intent);
		}
		
		else if (activeActivity
				.equals("activities.Phone4Activity")) {		
			Intent intent=new Intent(activity,Phone4Activity.class);
			((Phone4Activity) activity).startActivity(intent);
		}
		
		/*
		 *probleem zoeken van intent extra info
		else if (activeActivity
				.equals("activities.InPhone4Activity")) {		
			Intent intent=new Intent();
			((InPhone4Activity) activity).startActivity(intent);
		}*/
		
		// todo the same for the phones
		// end test for foreground activity
	
	
	
	}



}
