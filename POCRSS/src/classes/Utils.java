package classes;

import com.example.pocrss.R;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

public class Utils {

	public static String SIZE = "";
	public static boolean settingChanged = false;
	public static String THEME = "";

	public static void setThemeToActivity(Activity act) {

		try {
			if (Utils.SIZE.equalsIgnoreCase("light small")) {
				act.setTheme(R.style.Theme_lightSmall);
			}
			if (Utils.SIZE.equalsIgnoreCase("dark small")) {
				act.setTheme(R.style.Theme_darkSmall);
			}
			if (Utils.SIZE.equalsIgnoreCase("light medium")) {
				act.setTheme(R.style.Theme_lightMedium);
			}
			if (Utils.SIZE.equalsIgnoreCase("dark medium")) {
				act.setTheme(R.style.Theme_darkMedium);
			}
			if (Utils.SIZE.equalsIgnoreCase("light large")) {
				act.setTheme(R.style.Theme_lightLarge);
			}
			if (Utils.SIZE.equalsIgnoreCase("dark large")) {
				act.setTheme(R.style.Theme_darkLarge);
			}

			if (Utils.THEME.equalsIgnoreCase("Default")) {
				act.setTheme(R.style.Theme_DefaultTheme);
				act.setTheme(R.style.Theme_ButtonRed);
				act.setTheme(R.style.Theme_SpinnerRed);
				act.setTheme(R.style.Theme_ListviewRed);
				
				// backgroundColor titleBar
				View title = act.getWindow().findViewById(android.R.id.title);
				View titleBar = (View) title.getParent();
				titleBar.setBackgroundColor(Color.parseColor("#FF0000"));			
			}
			if (Utils.THEME.equalsIgnoreCase("Gray")) {
				act.setTheme(R.style.Theme_Gray);
				act.setTheme(R.style.Theme_ButtonGray);
				act.setTheme(R.style.Theme_SpinnerGray);
				act.setTheme(R.style.Theme_ListviewGray);

				// backgroundColor titleBar
				View title = act.getWindow().findViewById(android.R.id.title);
				View titleBar = (View) title.getParent();
				titleBar.setBackgroundColor(Color.parseColor("#666666"));
				//act.setTitleColor(Color.parseColor("#111111"));
			}

			if (Utils.THEME.equalsIgnoreCase("Radial")) {
				act.setTheme(R.style.Theme_Radial);
				act.setTheme(R.style.Theme_ButtonBlue);
				act.setTheme(R.style.Theme_SpinnerBlue);
				act.setTheme(R.style.Theme_ListviewBlue);
				

				// backgroundColor titleBar
				View title = act.getWindow().findViewById(android.R.id.title);
				View titleBar = (View) title.getParent();
				titleBar.setBackgroundColor(Color.parseColor("#6D929B"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
