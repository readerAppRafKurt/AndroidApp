package activities;

import java.util.List;
import services.ActivityDao;
import services.DatabaseHandler;
import services.FontDao;
import services.ImageDao;
import services.UploadXML;
import classes.Article;
import classes.AsyncTaskLoadXML_;
import classes.Channel;
import classes.ImageListener;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ListTitlesAndBeginForSingleThemeActivity extends Activity {

	DatabaseHandler db;
	ProgressBar progress;
	List<Article> articlesForThemeAtCreate;
	List<Article> articlesForThemeAtUpdate;
	Channel channel;

	// layout parameters
	LinearLayout layout;
	LayoutParams lp;
	LayoutParams lpTitle;
	int[] activeFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_titles_and_begin_for_single_theme);

		// register activity as active
		ActivityDao.addActivity(this);

		// set scrollView
		layout = (LinearLayout) findViewById(R.id.layoutListTitlesAndBeginForSingleTheme);

		// get intent data
		Intent in = getIntent();

		// set the db
		db = new DatabaseHandler(ListTitlesAndBeginForSingleThemeActivity.this);
		// get the channel from db
		channel = db.getChannelById(Integer.parseInt(in
				.getStringExtra("channelId")));
		
		//get active font
		activeFont=FontDao.getLayoutXML();


		// get the articles for this channel
		articlesForThemeAtCreate = db.getArticlesForChannel(channel);
		// set a new title for the screen/activity
		ListTitlesAndBeginForSingleThemeActivity.this.setTitle(channel
				.getDescription());

		progress = (ProgressBar) findViewById(R.id.progressBarPhone1);
		progress.setVisibility(View.GONE);

		// start background service
		new AsyncTaskLoadXML_(getBaseContext(), progress, db,
				ListTitlesAndBeginForSingleThemeActivity.this).execute();

		lp = new LayoutParams(LayoutParams.MATCH_PARENT, 55);
		lpTitle = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		this.fillInFields(articlesForThemeAtCreate);

		Log.w("test", "in onCreate");

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		// if user goes back to overview screen.
		// articles have to be updated
		super.onRestart();

		Log.w("test",
				"in onRestart met reeds bezig " + UploadXML.isRefreshScreenPause());
		if (UploadXML.isRefreshScreenPause() == false) {
			Log.w("test", "in onRestart");
			refreshScreen();

			Log.w("test",
					"in onRestart met reeds bezig " + UploadXML.isAlreadyBusy());
			if(UploadXML.isAlreadyBusy()!=true)
			{
			// start background service
			new AsyncTaskLoadXML_(getBaseContext(), progress, db,
					ListTitlesAndBeginForSingleThemeActivity.this).execute();
			}
		}
	}

	public void refreshScreen() {

		//articlesForTheme.clear();

		Log.w("test", "in refreshScreen");
		articlesForThemeAtUpdate = db.getArticlesForChannel(channel);

		runOnUiThread(new Runnable() {
			public void run() {
				Log.w("test", "in runnable van refreshScreen");
				// remove all textviews
				layout.removeAllViews();

				// set a new title for the screen/activity
				ListTitlesAndBeginForSingleThemeActivity.this.setTitle(channel
						.getDescription());

				fillInFields(articlesForThemeAtUpdate);
			}
		});

	}

	private void fillInFields(List<Article> articlesForTheme) {
		// loop over all the articles from the selected channel
		for (Article a : articlesForTheme) {
			//info set style at runtime
			//http://stackoverflow.com/questions/3142067/android-set-style-in-code

			// textView for the title of the article
			TextView tvTitle = (TextView)getLayoutInflater().inflate(activeFont[0], null);
			tvTitle.setText(a.getTitle());
			tvTitle.setPadding(3, 3, 3, 3);
			tvTitle.setGravity(Gravity.CENTER);
			tvTitle.setTypeface(null, Typeface.BOLD);

			// the next &lt;/b>&lt;br>&lt;p> have to be replaced by nothing
			String test = a.getDescription().replace("&lt;", "")
					.replace("/b>", "").replace("br>", "").replace("p>", "")
					.replace("<b>", "").replace("<P>", "").replace("I>", "")
					.replace("/I>", "").replace("<I>", "");

			// set textview article with the first 200 characters
			TextView tvArticle = (TextView)getLayoutInflater().inflate(activeFont[1], null);
			if (test.length() > 200) {
				tvArticle.setText(test.substring(0, 200));
			} else {
				tvArticle.setText(test);
			}
			tvArticle.setPadding(3, 0, 3, 0);
			tvArticle.setTag(a);

			// set onclicklistener for the description of the article
			tvArticle.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// start new intent and pass the data for the article
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
			
			layout.addView(tvTitle);
			layout.addView(tvArticle);

			tvTitle.setLayoutParams(lpTitle);
			tvArticle.setLayoutParams(lp);
		}
	}
}
