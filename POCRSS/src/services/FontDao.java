package services;

import classes.Utils;

import com.example.pocrss.R;
import android.content.Context;

public class FontDao {

	static DatabaseHandler db;

	static int[] layoutXML;

	public static int[] getLayoutXML() {
		return layoutXML;
	}

	public static int[] setActiveFont(Context context) {

		db = new DatabaseHandler(context);

		// create an array with id for layout xml
		// /the first is textTitle, the second textBody, the third buttonText
		// layoutXML=null;

		layoutXML = new int[3];

		String activeFont = db.getActiveFont();

		if (activeFont.equals("light small")) {
			layoutXML[0] = R.layout.z_runtime_light_small_title;
			layoutXML[1] = R.layout.z_runtime_light_small_body;
			layoutXML[2] = R.layout.z_runtime_light_small_button;

			return layoutXML;
		} else if (activeFont.equals("dark small")) {
			layoutXML[0] = R.layout.z_runtime_dark_small_title;
			layoutXML[1] = R.layout.z_runtime_dark_small_body;
			layoutXML[2] = R.layout.z_runtime_dark_small_button;

			return layoutXML;
		} else if (activeFont.equals("light medium")) {
			layoutXML[0] = R.layout.z_runtime_light_medium_title;
			layoutXML[1] = R.layout.z_runtime_light_medium_body;
			layoutXML[2] = R.layout.z_runtime_light_medium_button;
			return layoutXML;
		} else if (activeFont.equals("dark medium")) {
			layoutXML[0] = R.layout.z_runtime_dark_medium_title;
			layoutXML[1] = R.layout.z_runtime_dark_medium_body;
			layoutXML[2] = R.layout.z_runtime_dark_medium_button;
			return layoutXML;
		} else if (activeFont.equals("light large")) {
			layoutXML[0] = R.layout.z_runtime_light_large_title;
			layoutXML[1] = R.layout.z_runtime_light_large_body;
			layoutXML[2] = R.layout.z_runtime_light_large_button;
			return layoutXML;
		} else if (activeFont.equals("dark large")) {

			layoutXML[0] = R.layout.z_runtime_dark_large_title;
			layoutXML[1] = R.layout.z_runtime_dark_large_body;
			layoutXML[2] = R.layout.z_runtime_dark_large_button;
			return layoutXML;
		} else {
			layoutXML[0] = R.layout.z_runtime_dark_small_title;
			layoutXML[1] = R.layout.z_runtime_dark_small_body;
			layoutXML[2] = R.layout.z_runtime_dark_small_button;
			return layoutXML;
		}
	}

	public static void setUtilsFonts(String selectedFont) {
		
		if (selectedFont.equals("light small")) {
			Utils.settingChanged = true;
			Utils.SIZE = "light small";								
		} 
		else if (selectedFont.equals("light medium")) {
			Utils.settingChanged = true;
			Utils.SIZE = "light medium";								
		} 
		else if (selectedFont.equals("dark medium")) {
			Utils.settingChanged = true;
			Utils.SIZE = "dark medium";							
		} 
		else if (selectedFont.equals("light large")) {
			Utils.settingChanged = true;
			Utils.SIZE = "light large";									
		} 
		else if (selectedFont.equals("dark large")) {
			Utils.settingChanged = true;
			Utils.SIZE = "dark large";							
		} 
		else {
			Utils.settingChanged = true;
			Utils.SIZE = "dark small";			
		} 	
	}

}
