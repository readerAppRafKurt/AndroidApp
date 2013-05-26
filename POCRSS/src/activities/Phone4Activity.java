package activities;

import java.util.List;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.ChannelDao;
import dao.FontDao;
import dao.ThemeDao;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Phone4Activity extends TabActivity {

	// info http://www.androidhive.info/2011/08/android-tab-layout-tutorial/
	List<Channel> oldActiveChannels;

	static TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(FontDao.getActiveFont());
		ThemeDao.setUtilsTheme(ThemeDao.getActiveTheme());

		Utils.setThemeToActivity(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);

		// only portrait layout
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// register activity as active
		ActivityDao.addActivity(this);

		tabHost = getTabHost();

		// get active channels
		oldActiveChannels = ChannelDao.getAllSelectedChannels();

		for (Channel channel : oldActiveChannels) {

			TabSpec tabspec = tabHost.newTabSpec(channel.getDescription());
			// setting Title and Icon for the Tab
			tabspec.setIndicator(channel.getDescription(), getResources()
					.getDrawable(R.drawable.icon_image));
			Intent intent = new Intent(this, InPhone4Activity.class);
			intent.putExtra("channel", channel.get_id());

			tabspec.setContent(intent);
			tabHost.addTab(tabspec);
		}

		setTabColor();

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String arg0) {
				setTabColor();
			}
		});
	}

	public static void setTabColor() {
		String activeThemeColor = ThemeDao.getActiveTheme();

		int darkColor;
		int lightColor;

		if (activeThemeColor.equalsIgnoreCase("blauw")) {
			darkColor = Color.parseColor("#6D929B");
			lightColor = Color.parseColor("#ACD1E9");
		} else if (activeThemeColor.equalsIgnoreCase("rood")) {
			darkColor = Color.parseColor("#FF0000");
			lightColor = Color.parseColor("#ef5c40");
		} else {
			darkColor = Color.parseColor("#666666");
			lightColor = Color.parseColor("#C0C0C0");
		}

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			// if tab(s) are not selected
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(lightColor);
		}
		// if tab is selected
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundColor(darkColor);

	}

	@Override
	public void onBackPressed() {

		// empty the active activities
		ActivityDao.finishAllActivities();

		super.onBackPressed();
	}

	public void clickHeader(View v) {
		this.openOptionsMenu();
	}
	/*
	@Override
	protected void onRestart() {
		super.onRestart();

		// control old activeChannel

		//tabHost.getTabWidget().removeView(tabHost.getTabWidget().getChildTabViewAt(3));
		// tabHost.removeAllViews();
		tabHost.getTabWidget().removeAllViews();

		// get active channels
		List<Channel> newActiveChannels = ChannelDao.getAllSelectedChannels();

		for (Channel channel : newActiveChannels) {

			TabSpec tabspec = tabHost.newTabSpec(channel.getDescription());
			// setting Title and Icon for the Tab
			tabspec.setIndicator(channel.getDescription(), getResources()
					.getDrawable(R.drawable.icon_image));
			Intent intent = new Intent(this, InPhone4Activity.class);
			intent.putExtra("channel", channel.get_id());

			tabspec.setContent(intent);
			tabHost.addTab(tabspec);
		}
		
		setTabColor();

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String arg0) {
				setTabColor();
			}
		});
	
	}*/
}