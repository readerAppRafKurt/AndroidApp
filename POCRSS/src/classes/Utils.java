package classes;

import com.example.pocrss.R;
import android.app.Activity;
import android.graphics.Color;

public class Utils {

	public static String SIZE = "";
	public static boolean settingChanged = false;
	public static String THEME = "";

	public static void setThemeToActivity(Activity act) {

		try {
			if (Utils.SIZE.equalsIgnoreCase("light small")) {
				act.setTheme(R.style.Theme_lightSmall);
				act.setTitleColor(Color.WHITE);
			}
			else if (Utils.SIZE.equalsIgnoreCase("dark small")) {
				act.setTheme(R.style.Theme_darkSmall);
				act.setTitleColor(Color.BLACK);
			}
			else if (Utils.SIZE.equalsIgnoreCase("light medium")) {
				act.setTheme(R.style.Theme_lightMedium);
				act.setTitleColor(Color.WHITE);
			}
			else if (Utils.SIZE.equalsIgnoreCase("dark medium")) {
				act.setTheme(R.style.Theme_darkMedium);
				act.setTitleColor(Color.BLACK);
			}
			else if (Utils.SIZE.equalsIgnoreCase("light large")) {
				act.setTheme(R.style.Theme_lightLarge);
				act.setTitleColor(Color.WHITE);
			}
			else if (Utils.SIZE.equalsIgnoreCase("dark large")) {
				act.setTheme(R.style.Theme_darkLarge);
				act.setTitleColor(Color.BLACK);
			}
			if (Utils.THEME.equalsIgnoreCase("Default")) {
				act.setTheme(R.style.Theme_DefaultTheme);
				act.setTheme(R.style.Theme_ButtonRed);
				act.setTheme(R.style.Theme_SpinnerRed);
				act.setTheme(R.style.Theme_ListviewRed);
			}
			else if (Utils.THEME.equalsIgnoreCase("Gray")) {
				act.setTheme(R.style.Theme_Gray);
				act.setTheme(R.style.Theme_ButtonGray);
				act.setTheme(R.style.Theme_SpinnerGray);
				act.setTheme(R.style.Theme_ListviewGray);
			}
			else if (Utils.THEME.equalsIgnoreCase("Radial")) {
				act.setTheme(R.style.Theme_Radial);
				act.setTheme(R.style.Theme_ButtonBlue);
				act.setTheme(R.style.Theme_SpinnerBlue);
				act.setTheme(R.style.Theme_ListviewBlue);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
