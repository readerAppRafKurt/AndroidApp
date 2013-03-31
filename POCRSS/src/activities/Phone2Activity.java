package activities;

import java.util.List;
import services.ActivityDao;
import services.DatabaseHandler;
import services.FontDao;
import services.ThemeDao;
import services.UploadXML;
import classes.Article;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Phone2Activity extends Activity {

	DatabaseHandler db;
	LinearLayout linearLayout;
	List<Channel> activeChannels;

	int[] activeFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// set the database
		db = new DatabaseHandler(Phone2Activity.this);
		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(db.getActiveFont());
		ThemeDao.setUtilsTheme(db.getActiveTheme());

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_articles_per_theme);

		// register activity as active
		ActivityDao.addActivity(this);

		linearLayout = (LinearLayout) findViewById(R.id.layoutTwoArticlesPerTheme);

		// get list of active themes
		activeChannels = db.getAllSelectedChannels();

		fillInFields();
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
			Intent i = new Intent(Phone2Activity.this, RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(Phone2Activity.this, SettingsActivity.class);
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

		if (UploadXML.isRefreshScreenPause() == false) {
			fillInFields();
		}
	}

	public void fillInFields() {

		// remove all fields from layout
		linearLayout.removeAllViews();

		// get active font
		activeFont = FontDao.getLayoutXML();

		runOnUiThread(new Runnable() {
			public void run() {

				// get activeThemeColor
				String activeThemeColor = db.getActiveTheme();
				String readOn;

				// creae layout parameters for textview channels
				LayoutParams llp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				llp.setMargins(0, 15, 0, 15);

				for (Channel channel : activeChannels) {

					// get 2 articles per channel

					// set title for the channel
					TextView txvChannel = (TextView) getLayoutInflater()
							.inflate(activeFont[0], null);
					txvChannel.setText(Html.fromHtml(channel.getDescription()
							.substring(15)));
					txvChannel.setGravity(Gravity.CENTER);
					txvChannel.setTypeface(null, Typeface.BOLD);
					txvChannel.setTag(Integer.toString(channel.get_id()));
					txvChannel.setPadding(10, 10, 10, 10);

					if (activeThemeColor.equalsIgnoreCase("blauw")) {
						txvChannel.setBackgroundColor(Color
								.parseColor("#6D929B"));
						readOn = "<FONT COLOR='#6D929B'> ... LEES MEER ...</FONT>";
					}
					if (activeThemeColor.equalsIgnoreCase("rood")) {
						// theme is rood
						txvChannel.setBackgroundColor(Color
								.parseColor("#FF0000"));
						readOn = "<FONT COLOR='#FF0000'> ... LEES MEER ...</FONT>";
					} else {
						txvChannel.setBackgroundColor(Color
								.parseColor("#666666"));
						readOn = "<FONT COLOR='#666666'> ... LEES MEER ...</FONT>";
					}

					// txvChannel.setTextSize(25);

					txvChannel.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {

							// only show the articles for the selected theme
							// start new intent and pass the id of the selected
							// channel
							// show only title screen for the specific channel
							Intent intent = new Intent(
									Phone2Activity.this,
									ListTitlesArticleForSingleThemeActivity.class);

							String sTag = (String) v.getTag();

							intent.putExtra("channelId", sTag);
							Phone2Activity.this.startActivity(intent);
						}
					});

					linearLayout.addView(txvChannel);

					txvChannel.setLayoutParams(llp);

					// get the articles for this channel
					List<Article> articlesForTheme = db
							.getArticlesForChannel(channel);

					// Article 1
					Article a1 = articlesForTheme.get(0);
					// title
					TextView txvTitle1 = (TextView) getLayoutInflater()
							.inflate(activeFont[1], null);
					txvTitle1.setGravity(Gravity.CENTER);
					txvTitle1.setText(a1.getTitle());
					txvTitle1.setTypeface(null, Typeface.BOLD);
					// txvTitle1.setTextSize(15);
					txvTitle1.setPadding(3, 3, 3, 3);

					// description
					TextView txvDescription1 = (TextView) getLayoutInflater()
							.inflate(activeFont[1], null);
					// the next &lt;/b>&lt;br>&lt;p> have to be replaced by
					// nothing
					String description1 = a1.getDescription()
							.replace("&lt;", "").replace("/b>", "")
							.replace("br>", "").replace("<P>", "")
							.replace("p>", "").replace("<b>", "")
							.replace("<I>", "").replace("</I>", "")
							.replace("/I>", "").replace("I>", "");

					if (description1.length() < 200) {
						txvDescription1.setText(Html.fromHtml("<P>"
								+ description1 + readOn + "</P>"));
					} else {
						txvDescription1.setText(Html.fromHtml("<P>"
								+ description1.substring(0, 200) + readOn
								+ "</P>"));
					}

					txvDescription1.setPadding(3, 0, 3, 0);
					txvDescription1.setTag(a1);
					// set onclicklistener for the description of the article
					txvDescription1.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {

							// start new intent and pass the data for the
							// article
							Intent in = new Intent(getApplicationContext(),
									PhoneSingleArticleActivity.class);

							Article a = (Article) v.getTag();

							in.putExtra("article_id",
									Integer.toString(a.get_id()));
							in.putExtra("title", a.getTitle());
							in.putExtra("enclosure", a.getEnclosure());
							in.putExtra("description", a.getDescription());
							in.putExtra("pubDate", a.getPubDate().toString());
							in.putExtra("channel_id",
									Integer.toString(a.getChannel().get_id()));
							startActivity(in);
						}
					});

					linearLayout.addView(txvTitle1);
					linearLayout.addView(txvDescription1);

					// Article 2
					Article a2 = articlesForTheme.get(1);
					// title
					TextView txvTitle2 = (TextView) getLayoutInflater()
							.inflate(activeFont[1], null);
					txvTitle2.setGravity(Gravity.CENTER);
					txvTitle2.setText(a2.getTitle());
					txvTitle2.setTypeface(null, Typeface.BOLD);
					// txvTitle2.setTextSize(15);
					txvTitle2.setPadding(3, 3, 3, 3);

					// description
					TextView txvDescription2 = (TextView) getLayoutInflater()
							.inflate(activeFont[1], null);
					// the next &lt;/b>&lt;br>&lt;p> have to be replaced by
					// nothing
					String description2 = a2.getDescription()
							.replace("&lt;", "").replace("/b>", "")
							.replace("br>", "").replace("p>", "")
							.replace("<b>", "").replace("<P>", "")
							.replace("<I>", "").replace("</I>", "")
							.replace("/I>", "").replace("I>", "");

					if (description2.length() < 200) {
						txvDescription2.setText(Html.fromHtml("<P>"
								+ description2 + readOn + "</P>"));
					} else {
						txvDescription2.setText(Html.fromHtml("<P>"
								+ description2.substring(0, 200) + readOn
								+ "</P>"));
					}

					txvDescription2.setPadding(3, 0, 3, 0);
					txvDescription2.setTag(a2);
					// set onclicklistener for the description of the article
					txvDescription2.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {

							// start new intent and pass the data for the
							// article
							Intent in = new Intent(getApplicationContext(),
									PhoneSingleArticleActivity.class);

							Article a = (Article) v.getTag();

							in.putExtra("article_id",
									Integer.toString(a.get_id()));
							in.putExtra("title", a.getTitle());
							in.putExtra("enclosure", a.getEnclosure());
							in.putExtra("description", a.getDescription());
							in.putExtra("pubDate", a.getPubDate().toString());
							in.putExtra("channel_id",
									Integer.toString(a.getChannel().get_id()));
							startActivity(in);
						}
					});

					linearLayout.addView(txvTitle2);
					linearLayout.addView(txvDescription2);
				}
			}
		});
	}
}
