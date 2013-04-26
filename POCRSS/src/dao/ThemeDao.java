package dao;

import services.DatabaseHandler;
import android.content.Context;
import classes.Utils;

public class ThemeDao {
		
	static DatabaseHandler db;
	
	public static void openDb(Context context){
		db=new DatabaseHandler(context);
	}

	public static void setUtilsTheme(String selectedTheme) {

		if (selectedTheme.equals("Grijs")) {
			Utils.THEME = "Gray";
			Utils.settingChanged = true;
		} else if (selectedTheme.equals("Blauw")) {
			Utils.THEME = "Radial";
			Utils.settingChanged = true;
		} else {
			// Rood
			Utils.THEME = "DEFAULT";
			Utils.settingChanged = true;
		}
	}

	public static String getActiveTheme(){
		return db.getActiveTheme();
	}
	
	public static void updateTheme(String selectedTheme){
		db.updateTheme(selectedTheme);
	}
}
