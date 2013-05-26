package activities;

import java.util.List;
import services.UploadXML;
import viewElements.ArticleListener;
import viewElements.ChannelScroller;
import classes.Article;
import classes.AsyncTaskLoadXML_;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TabletHorizontalOverviewActivity extends Activity {

	TextView article1title, article1description, article2title,
			article2description, article3title, article3description,
			article4title, article4description, article5title,
			article5description, article6title, article6description,
			article7title, article7description, article8title,
			article8description, article9title, article9description,
			article10title, article10description, article11title,
			article11description, article12title, article12description,
			article13title, article13description, article14title,
			article14description, article15title, article15description,
			article1timeAgo, article2timeAgo, article3timeAgo, article4timeAgo,
			article5timeAgo, article6timeAgo, article7timeAgo, article8timeAgo,
			article9timeAgo, article10timeAgo, article11timeAgo,
			article12timeAgo, article13timeAgo, article14timeAgo,
			article15timeAgo;

	TextView tvTitleBar;

	ChannelScroller channelTabletOverviewChannelScroller;
	ProgressBar progress;

	List<Article> articles;
	Channel channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(FontDao.getActiveFont());
		ThemeDao.setUtilsTheme(ThemeDao.getActiveTheme());

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activitytablethorizontaloverview);

		// register as active
		ActivityDao.addActivity(this);

		// get list of articles for selected feed on the right

		// get selected channel
		Intent in = getIntent();

		int channelId = Integer.parseInt(in.getStringExtra("channel_id"));

		channel = ChannelDao.getChannelById(channelId);

		// get articles for this channel
		articles = ChannelDao.getArticlesForChannel(channel);

		// if less than 15 articles in db e.g. when new feed was selected in
		// RSSFeedsActivity
		// and articles are not yet loaded
		if (articles == null || articles.size() < 15) {
			//message to reader and go back to phone1Activity
			this.alertMessageNotEnoughArticlesInDB(TabletHorizontalOverviewActivity.this, "Upload artikels is nog bezig", "Nog even wachten\nEr zijn nog niet genoeg artikels geuploaded", true);
			
		} else {

			// fill in the textfields
			this.fillInFields();

			// set progressBar
			progress = (ProgressBar) findViewById(R.id.progressTablet);
			progress.setVisibility(View.GONE);

			// start background service
			new AsyncTaskLoadXML_(getBaseContext(), progress,
					TabletHorizontalOverviewActivity.this).execute();
		}
	}

	// method to programmatically restart activity
	public void restart() {
		Log.w("testTablet", "in restart");
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@Override
	protected void onRestart() {
		// if user goes back to overview screen.
		// articles have to be updated
		super.onRestart();
		Log.w("testTablet", "in onRestart");

		// get articles for this channel
		articles = ChannelDao.getArticlesForChannel(channel);

		// fill in the textfields
		this.fillInFields();

		// set progressBar
		if (UploadXML.isAlreadyBusy() == true) {
			// keep the same progressbar
			// get position of progressbar
			int posProgress = progress.getProgress();
			progress = (ProgressBar) findViewById(R.id.progressTablet);
			progress.setProgress(posProgress);
			progress.setVisibility(View.VISIBLE);
		} else {
			progress = (ProgressBar) findViewById(R.id.progressTablet);
			progress.setVisibility(View.GONE);
			// start background service
			new AsyncTaskLoadXML_(getBaseContext(), progress,
					TabletHorizontalOverviewActivity.this).execute();
		}
	}

	// setup for the general preferences of the app.
	// users can modify their font, layout, theme and effects
	// users can choose out of different article channels
	// setup via a menu in the TabletHorizontalOverviewActivity
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
			Intent i = new Intent(TabletHorizontalOverviewActivity.this,
					RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(TabletHorizontalOverviewActivity.this,
					SettingsActivity.class);
			startActivity(ii);

			break;
		}
		return false;
	}

	private void fillInFields() {

		LayoutParams lpTitle = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		// set new content titleBar
		tvTitleBar = (TextView) findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.app_name);

		// article 1
		Article a1 = articles.get(0);
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.article1);
		linearLayout1.setOnClickListener(new ArticleListener(a1));
		article1title = (TextView) findViewById(R.id.article1title);
		article1description = (TextView) findViewById(R.id.article1description);
		article1timeAgo = (TextView) findViewById(R.id.article1timeAgo);
		article1title.setText(Html.fromHtml("<P><B>" + a1.getTitle()
				+ "</B></P>"));
		article1description.setText(Html.fromHtml(a1.getDescription()));
		article1timeAgo.setText(a1.getTimeAgo());

		// article 2
		Article a2 = articles.get(1);
		LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.article2);
		linearLayout2.setOnClickListener(new ArticleListener(a2));
		article2title = (TextView) findViewById(R.id.article2title);
		article2description = (TextView) findViewById(R.id.article2description);
		article2timeAgo = (TextView) findViewById(R.id.article2timeAgo);
		article2title.setText(Html.fromHtml("<P><B>" + a2.getTitle()
				+ "</B></P>"));
		article2description.setText(Html.fromHtml(a2.getDescription()));
		article2timeAgo.setText(a2.getTimeAgo());
		// article 3
		Article a3 = articles.get(2);
		LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.article3);
		linearLayout3.setOnClickListener(new ArticleListener(a3));
		article3title = (TextView) findViewById(R.id.article3title);
		article3description = (TextView) findViewById(R.id.article3description);
		article3timeAgo = (TextView) findViewById(R.id.article3timeAgo);
		article3title.setText(Html.fromHtml("<P><B>" + a3.getTitle()
				+ "</B></P>"));
		article3description.setText(Html.fromHtml(a3.getDescription()));
		article3timeAgo.setText(a3.getTimeAgo());
		// article 4
		Article a4 = articles.get(3);
		LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.article4);
		linearLayout4.setOnClickListener(new ArticleListener(a4));
		article4title = (TextView) findViewById(R.id.article4title);
		article4description = (TextView) findViewById(R.id.article4description);
		article4timeAgo = (TextView) findViewById(R.id.article4timeAgo);
		article4title.setText(Html.fromHtml("<P><B>" + a4.getTitle()
				+ "</B></P>"));
		article4description.setText(Html.fromHtml(a4.getDescription()));
		article4timeAgo.setText(a4.getTimeAgo());
		// article 5
		Article a5 = articles.get(4);
		LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.article5);
		linearLayout5.setOnClickListener(new ArticleListener(a5));
		article5title = (TextView) findViewById(R.id.article5title);
		// article5description=(TextView)
		// findViewById(R.id.article5description);
		article5timeAgo = (TextView) findViewById(R.id.article5timeAgo);
		article5title.setText(Html.fromHtml("<P><B>" + a5.getTitle()
				+ "</B></P>"));
		// article5description.setText(Html.fromHtml(a5.getDescription()));
		article5timeAgo.setText(a5.getTimeAgo());

		LinearLayout innerLinearLayoutarticle5 = (LinearLayout) findViewById(R.id.innerLinearLayoutarticle5);

		// insert image
		if (a5.getEnclosure() != null && a5.getEnclosure() != "") {
			// ImageView imageView5=new ImageView(this);
			ImageView imageView5 = (ImageView) getLayoutInflater().inflate(
					R.layout.image_tablet, null);
			Bitmap articleImage5 = ImageDao.getImage(getBaseContext(),
					a5.getEnclosure());
			imageView5.setImageBitmap(articleImage5);
			innerLinearLayoutarticle5.addView(imageView5);
			imageView5.setLayoutParams(lpTitle);
		} else {
			TextView txvDescription5 = (TextView) getLayoutInflater().inflate(
					R.layout.description_tablet, null);
			txvDescription5.setText(Html.fromHtml(a5.getDescription()));
			innerLinearLayoutarticle5.addView(txvDescription5);
		}

		// article 6
		Article a6 = articles.get(5);
		LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.article6);
		linearLayout6.setOnClickListener(new ArticleListener(a6));
		article6title = (TextView) findViewById(R.id.article6title);
		article6description = (TextView) findViewById(R.id.article6description);
		article6timeAgo = (TextView) findViewById(R.id.article6timeAgo);
		article6title.setText(Html.fromHtml("<P><B>" + a6.getTitle()
				+ "</B></P>"));
		article6description.setText(Html.fromHtml(a6.getDescription()));
		article6timeAgo.setText(a6.getTimeAgo());
		// article 7
		Article a7 = articles.get(6);
		LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.article7);
		linearLayout7.setOnClickListener(new ArticleListener(a7));
		article7title = (TextView) findViewById(R.id.article7title);
		article7description = (TextView) findViewById(R.id.article7description);
		article7timeAgo = (TextView) findViewById(R.id.article7timeAgo);
		article7title.setText(Html.fromHtml("<P><B>" + a7.getTitle()
				+ "</B></P>"));
		article7description.setText(Html.fromHtml(a7.getDescription()));
		article7timeAgo.setText(a7.getTimeAgo());
		// article 8
		Article a8 = articles.get(7);
		LinearLayout linearLayout8 = (LinearLayout) findViewById(R.id.article8);
		linearLayout8.setOnClickListener(new ArticleListener(a8));
		article8title = (TextView) findViewById(R.id.article8title);
		// article8description=(TextView)
		// findViewById(R.id.article8description);
		article8timeAgo = (TextView) findViewById(R.id.article8timeAgo);
		article8title.setText(Html.fromHtml("<P><B>" + a8.getTitle()
				+ "</B></P>"));
		// article8description.setText(Html.fromHtml(a8.getDescription()));
		article8timeAgo.setText(a8.getTimeAgo());

		// insert image
		if (a8.getEnclosure() != null && a8.getEnclosure() != "") {
			ImageView imageView8 = new ImageView(this);
			Bitmap articleImage8 = ImageDao.getImage(getBaseContext(),
					a8.getEnclosure());
			imageView8.setImageBitmap(articleImage8);
			linearLayout8.addView(imageView8);
			// imageView8.setLayoutParams(lpTitle);
		} else {
			TextView txvDescription8 = (TextView) getLayoutInflater().inflate(
					R.layout.description_tablet, null);
			txvDescription8.setText(Html.fromHtml(a8.getDescription()));
			linearLayout8.addView(txvDescription8);
		}

		// article 9
		Article a9 = articles.get(8);
		LinearLayout linearLayout9 = (LinearLayout) findViewById(R.id.article9);
		linearLayout9.setOnClickListener(new ArticleListener(a9));
		article9title = (TextView) findViewById(R.id.article9title);
		article9description = (TextView) findViewById(R.id.article9description);
		article9timeAgo = (TextView) findViewById(R.id.article9timeAgo);
		article9title.setText(Html.fromHtml("<P><B>" + a9.getTitle()
				+ "</B></P>"));
		article9description.setText(Html.fromHtml(a9.getDescription()));
		article9timeAgo.setText(a9.getTimeAgo());
		// article 10
		Article a10 = articles.get(9);
		LinearLayout linearLayout10 = (LinearLayout) findViewById(R.id.article10);
		linearLayout10.setOnClickListener(new ArticleListener(a10));
		article10title = (TextView) findViewById(R.id.article10title);
		article10description = (TextView) findViewById(R.id.article10description);
		article10timeAgo = (TextView) findViewById(R.id.article10timeAgo);
		article10title.setText(Html.fromHtml("<P><B>" + a10.getTitle()
				+ "</B></P>"));
		article10description.setText(Html.fromHtml(a10.getDescription()));
		article10timeAgo.setText(a10.getTimeAgo());
		// article 11
		Article a11 = articles.get(10);
		LinearLayout linearLayout11 = (LinearLayout) findViewById(R.id.article11);
		linearLayout11.setOnClickListener(new ArticleListener(a11));
		article11title = (TextView) findViewById(R.id.article11title);
		// article11description=(TextView)
		// findViewById(R.id.article11description);
		article11timeAgo = (TextView) findViewById(R.id.article11timeAgo);
		article11title.setText(Html.fromHtml("<P><B>" + a11.getTitle()
				+ "</B></P>"));
		// article11description.setText(Html.fromHtml(a11.getDescription()));
		article11timeAgo.setText(a11.getTimeAgo());

		// insert image
		if (a11.getEnclosure() != null && a11.getEnclosure() != "") {
			ImageView imageView11 = new ImageView(this);
			Bitmap articleImage11 = ImageDao.getImage(getBaseContext(),
					a11.getEnclosure());
			imageView11.setImageBitmap(articleImage11);
			linearLayout11.addView(imageView11);
		} else {
			TextView txvDescription11 = (TextView) getLayoutInflater().inflate(
					R.layout.description_tablet, null);
			txvDescription11.setText(Html.fromHtml(a11.getDescription()));
			linearLayout8.addView(txvDescription11);
		}

		// article 12
		Article a12 = articles.get(11);
		LinearLayout linearLayout12 = (LinearLayout) findViewById(R.id.article12);
		linearLayout12.setOnClickListener(new ArticleListener(a12));
		article12title = (TextView) findViewById(R.id.article12title);
		article12description = (TextView) findViewById(R.id.article12description);
		article12timeAgo = (TextView) findViewById(R.id.article12timeAgo);
		article12title.setText(Html.fromHtml("<P><B>" + a12.getTitle()
				+ "</B></P>"));
		article12description.setText(Html.fromHtml(a12.getDescription()));
		article12timeAgo.setText(a12.getTimeAgo());
		// article 13
		Article a13 = articles.get(12);
		LinearLayout linearLayout13 = (LinearLayout) findViewById(R.id.article13);
		linearLayout13.setOnClickListener(new ArticleListener(a13));
		article13title = (TextView) findViewById(R.id.article13title);
		article13description = (TextView) findViewById(R.id.article13description);
		article13timeAgo = (TextView) findViewById(R.id.article13timeAgo);
		article13title.setText(Html.fromHtml("<P><B>" + a13.getTitle()
				+ "</B></P>"));
		article13description.setText(Html.fromHtml(a13.getDescription()));
		article13timeAgo.setText(a13.getTimeAgo());
		// article 14
		Article a14 = articles.get(13);
		LinearLayout linearLayout14 = (LinearLayout) findViewById(R.id.article14);
		linearLayout14.setOnClickListener(new ArticleListener(a14));
		article14title = (TextView) findViewById(R.id.article14title);
		// article14description=(TextView)
		// findViewById(R.id.article14description);
		article14timeAgo = (TextView) findViewById(R.id.article14timeAgo);
		article14title.setText(Html.fromHtml("<P><B>" + a14.getTitle()
				+ "</B></P>"));
		// article14description.setText(Html.fromHtml(a14.getDescription()));
		article14timeAgo.setText(a14.getTimeAgo());

		// insert image
		if (a14.getEnclosure() != null && a14.getEnclosure() != "") {
			ImageView imageView14 = new ImageView(this);
			Bitmap articleImage14 = ImageDao.getImage(getBaseContext(),
					a14.getEnclosure());
			imageView14.setImageBitmap(articleImage14);
			linearLayout14.addView(imageView14);
		} else {
			TextView txvDescription14 = (TextView) getLayoutInflater().inflate(
					R.layout.description_tablet, null);
			txvDescription14.setText(Html.fromHtml(a14.getDescription()));
			linearLayout14.addView(txvDescription14);
		}

		// article 15
		Article a15 = articles.get(14);
		LinearLayout linearLayout15 = (LinearLayout) findViewById(R.id.article15);
		linearLayout15.setOnClickListener(new ArticleListener(a15));
		article15title = (TextView) findViewById(R.id.article15title);
		article15description = (TextView) findViewById(R.id.article15description);
		article15timeAgo = (TextView) findViewById(R.id.article15timeAgo);
		article15title.setText(Html.fromHtml("<P><B>" + a15.getTitle()
				+ "</B></P>"));
		article15description.setText(Html.fromHtml(a15.getDescription()));
		article15timeAgo.setText(a15.getTimeAgo());

		// get activeThemeColor
		String activeThemeColor = ThemeDao.getActiveTheme();

		if (activeThemeColor.equalsIgnoreCase("blauw")) {

			article1title.setBackgroundColor(Color.parseColor("#6D929B"));
			article1description.setBackgroundColor(Color.parseColor("#6D929B"));

			article7title.setBackgroundColor(Color.parseColor("#6D929B"));
			article7description.setBackgroundColor(Color.parseColor("#6D929B"));

			article10title.setBackgroundColor(Color.parseColor("#6D929B"));
			article10description
					.setBackgroundColor(Color.parseColor("#6D929B"));

			article15title.setBackgroundColor(Color.parseColor("#6D929B"));
			article15description
					.setBackgroundColor(Color.parseColor("#6D929B"));
		} else if (activeThemeColor.equalsIgnoreCase("rood")) {
			article1title.setBackgroundColor(Color.parseColor("#FF0000"));
			article1description.setBackgroundColor(Color.parseColor("#FF0000"));

			article7title.setBackgroundColor(Color.parseColor("#FF0000"));
			article7description.setBackgroundColor(Color.parseColor("#FF0000"));

			article10title.setBackgroundColor(Color.parseColor("#FF0000"));
			article10description
					.setBackgroundColor(Color.parseColor("#FF0000"));

			article15title.setBackgroundColor(Color.parseColor("#FF0000"));
			article15description
					.setBackgroundColor(Color.parseColor("#FF0000"));
		} else {
			article1title.setBackgroundColor(Color.parseColor("#666666"));
			article1description.setBackgroundColor(Color.parseColor("#666666"));

			article7title.setBackgroundColor(Color.parseColor("#666666"));
			article7description.setBackgroundColor(Color.parseColor("#666666"));

			article10title.setBackgroundColor(Color.parseColor("#666666"));
			article10description
					.setBackgroundColor(Color.parseColor("#666666"));

			article15title.setBackgroundColor(Color.parseColor("#666666"));
			article15description
					.setBackgroundColor(Color.parseColor("#666666"));
		}

		// ScrollView channels
		channelTabletOverviewChannelScroller = (ChannelScroller) findViewById(R.id.channelTabletOverviewChannelScroller);
	}


	// method to show a messagebox when no channel is selected
	public void alertMessageNotEnoughArticlesInDB(Context context, String title,
			String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);

		// set ok button
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(TabletHorizontalOverviewActivity.this,
								Phone1Activity.class);
						TabletHorizontalOverviewActivity.this.startActivity(intent);
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public void clickHeader(View v) {
		this.openOptionsMenu();
	}
}
