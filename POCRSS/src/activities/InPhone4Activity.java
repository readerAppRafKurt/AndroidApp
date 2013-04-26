package activities;

import java.util.List;

import viewElements.ImageListener;

import classes.Article;
import classes.AsyncTaskUpdateDbWithoutProgressBar;
import classes.Channel;
import classes.Utils;

import com.example.pocrss.R;

import dao.ActivityDao;
import dao.ChannelDao;
import dao.FontDao;
import dao.ImageDao;
import dao.ThemeDao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InPhone4Activity extends Activity {

	List<Article> articles;
	Channel channel;
	LinearLayout internLayout;
	String activeThemeColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(FontDao.getActiveFont());
		ThemeDao.setUtilsTheme(ThemeDao.getActiveTheme());

		Utils.setThemeToActivity(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone4);

		// register activity as active
		ActivityDao.addActivity(this);

		Intent intent = getIntent();

		// get articles for channel
		channel = new Channel();
		channel.set_id(intent.getIntExtra("channel", 1));

		articles = ChannelDao.getArticlesForChannel(channel);

		// get activeThemeColor
		activeThemeColor = ThemeDao.getActiveTheme();

		internLayout = (LinearLayout) findViewById(R.id.internLayoutPhone4);

		for (Article article : articles) {

			RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater()
					.inflate(R.layout.listview_article_row, null);

			if (article.get_id() % 3 == 0) {
				if (activeThemeColor.equalsIgnoreCase("blauw")) {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#6D929B"));
				} else if (activeThemeColor.equalsIgnoreCase("rood")) {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#FF0000"));
				} else {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#666666"));
				}
			}

			ImageView image = (ImageView) relativeLayout.getChildAt(0);
			String enclosure = article.getEnclosure();
			// fill imageView
			if (enclosure != null && enclosure != "") {
				Bitmap articleImage = ImageDao.getImage(getBaseContext(),
						enclosure);
				image.setImageBitmap(articleImage);
				image.setOnClickListener(new ImageListener(enclosure));
			}

			LinearLayout layoutInScrollView_ = (LinearLayout) relativeLayout
					.getChildAt(1);

			LinearLayout layoutInScrollView = (LinearLayout) layoutInScrollView_
					.getChildAt(0);

			TextView txtDate = (TextView) layoutInScrollView.getChildAt(0);
			txtDate.setText(article.getTimeAgo());
			TextView txtTitle = (TextView) layoutInScrollView.getChildAt(1);
			txtTitle.setText(article.getTitle());

			relativeLayout.setTag(article);

			relativeLayout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// start new intent and pass the data for the
					// article
					Intent in = new Intent(getApplicationContext(),
							PhoneSingleArticleActivity.class);

					Article a = (Article) v.getTag();

					in.putExtra("article_id", Integer.toString(a.get_id()));
					in.putExtra("title", a.getTitle());
					in.putExtra("enclosure", a.getEnclosure());
					in.putExtra("description", a.getDescription());
					in.putExtra("pubDate", a.getPubDate().toString());
					in.putExtra("channel_id",
							Integer.toString(a.getChannel().get_id()));
					startActivity(in);
				}
			});

			internLayout.addView(relativeLayout);
		}	
		new AsyncTaskUpdateDbWithoutProgressBar(getBaseContext(),InPhone4Activity.this);
	}

	// setup for the general preferences of the app.
	// users can modify their font, layout, theme and effects
	// users can choose out of different article channels
	// setup via a menu in the mainActvity
	// uses onOptionsItemSelected for the method implementation
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater menuInflator = getMenuInflater();
		menuInflator.inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.choiceFeedsMenu:
			// setup new activity and intent to setup the selected feeds
			Intent i = new Intent(InPhone4Activity.this, RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(InPhone4Activity.this,
					SettingsActivity.class);
			startActivity(ii);

			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {

		// empty the active activities
		ActivityDao.finishAllActivities();

		super.onBackPressed();
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		// remove all child views from internal layout
		internLayout.removeAllViews();

		// recreate new list of articles
		articles.clear();
		articles = ChannelDao.getArticlesForChannel(channel);

		for (Article article : articles) {

			RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater()
					.inflate(R.layout.listview_article_row, null);

			if (article.get_id() % 3 == 0) {
				if (activeThemeColor.equalsIgnoreCase("blauw")) {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#6D929B"));
				} else if (activeThemeColor.equalsIgnoreCase("rood")) {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#FF0000"));
				} else {
					relativeLayout.setBackgroundColor(Color
							.parseColor("#666666"));
				}
			}

			ImageView image = (ImageView) relativeLayout.getChildAt(0);
			String enclosure = article.getEnclosure();
			// fill imageView
			if (enclosure != null && enclosure != "") {
				Bitmap articleImage = ImageDao.getImage(getBaseContext(),
						enclosure);
				image.setImageBitmap(articleImage);
				image.setOnClickListener(new ImageListener(enclosure));
			}

			LinearLayout layoutInScrollView_ = (LinearLayout) relativeLayout
					.getChildAt(1);

			LinearLayout layoutInScrollView = (LinearLayout) layoutInScrollView_
					.getChildAt(0);

			TextView txtDate = (TextView) layoutInScrollView.getChildAt(0);
			txtDate.setText(article.getTimeAgo());
			TextView txtTitle = (TextView) layoutInScrollView.getChildAt(1);
			txtTitle.setText(article.getTitle());

			relativeLayout.setTag(article);

			relativeLayout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// start new intent and pass the data for the
					// article
					Intent in = new Intent(getApplicationContext(),
							PhoneSingleArticleActivity.class);

					Article a = (Article) v.getTag();

					in.putExtra("article_id", Integer.toString(a.get_id()));
					in.putExtra("title", a.getTitle());
					in.putExtra("enclosure", a.getEnclosure());
					in.putExtra("description", a.getDescription());
					in.putExtra("pubDate", a.getPubDate().toString());
					in.putExtra("channel_id",
							Integer.toString(a.getChannel().get_id()));
					startActivity(in);
				}
			});
			internLayout.addView(relativeLayout);
		}
		new AsyncTaskUpdateDbWithoutProgressBar(getBaseContext(),InPhone4Activity.this);
	}
}
