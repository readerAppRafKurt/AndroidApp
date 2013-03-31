package services;

import java.util.ArrayList;

import android.app.Activity;

//class to keep all the active activities
//needed to finish all active activities when the settings are changed by the user
//and the app goes back to the startpage
public class ActivityDao {

	private static ArrayList<Activity> listActiveActivities=new ArrayList<Activity>();
	
	
	public static void addActivity(Activity activity){
		listActiveActivities.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		listActiveActivities.remove(activity);
	}
	
	public static void finishAllActivities(){
		//finish all the activities
		for(Activity a:listActiveActivities){
			a.finish();
		}
		
		//empty the list of active activities
		listActiveActivities.clear();
	}
}
