package services;

import classes.Utils;

public class ThemeDao {

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
}
