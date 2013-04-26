package dao;

import services.DatabaseHandler;
import android.content.Context;

public class LayoutDao {
	
	static DatabaseHandler db;
	
	
	public static String getActiveLayout(Context context){
		db = new DatabaseHandler(context);
		
		return db.getActiveLayout();
	}
	
	public static void updateLayout(String layoutType){
		db.updateLayout(layoutType);
	}

}
