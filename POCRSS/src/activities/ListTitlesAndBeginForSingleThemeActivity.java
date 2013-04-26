package activities;

import java.util.List;
import services.UploadXML;
import classes.Article;
import classes.AsyncTaskLoadXML_;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.ChannelDao;
import dao.FontDao;
import dao.ThemeDao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ListTitlesAndBeginForSingleThemeActivity extends Activity {

	ProgressBar progress;
	List<Article> articlesForThemeAtCreate;
	List<Article> articlesForThemeAtUpdate;
	Channel channel;
	private TextView tvTitleBar;

	// layout parameters
	LinearLayout layout;
	LayoutParams lp;
	LayoutParams lpTitle;
	int[] activeFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_titles_and_begin_for_single_theme);

		// register activity as active
		ActivityDao.addActivity(this);

		// set scrollView
		layout = (LinearLayout) findViewById(R.id.layoutListTitlesAndBeginForSingleTheme);

		// get intent data
		Intent in = getIntent();

		// get the channel from db
		channel = ChannelDao.getChannelById(Integer.parseInt(in
				.getStringExtra("channelId")));
		
		//get active font
		activeFont=FontDao.getLayoutXML();

		// get the articles for this channel
		articlesForThemeAtCreate = ChannelDao.getArticlesForChannel(channel);
			
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(channel.getDescription());

		progress = (ProgressBar) findViewById(R.id.progressBarPhone1);
		progress.setVisibility(View.GONE);

		// start background service
		new AsyncTaskLoadXML_(getBaseContext(), progress,
				ListTitlesAndBeginForSingleThemeActivity.this).execute();

		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
			new AsyncTaskLoadXML_(getBaseContext(), progress,
					ListTitlesAndBeginForSingleThemeActivity.this).execute();
			}
		}
	}

	public void refreshScreen() {

		articlesForThemeAtUpdate = ChannelDao.getArticlesForChannel(channel);

		runOnUiThread(new Runnable() {
			public void run() {
				// remove all textviews
				layout.removeAllViews();

				//set new content titleBar
				tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
				tvTitleBar.setText(channel.getDescription());

				fillInFields(articlesForThemeAtUpdate);
			}
		});

	}

	private void fillInFields(List<Article> articlesForTheme) {
		
		// get activeThemeColor
		String activeThemeColor = ThemeDao.getActiveTheme();
		String readOn;
		
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

			if (activeThemeColor.equalsIgnoreCase("blauw")) {
				tvTitle.setBackgroundColor(Color
						.parseColor("#6D929B"));
				readOn = "<FONT COLOR='#6D929B'> ... LEES MEER</FONT>";
			}
			else if (activeThemeColor.equalsIgnoreCase("rood")) {
				tvTitle.setBackgroundColor(Color
						.parseColor("#FF0000"));
				readOn = "<FONT COLOR='#FF0000'> ... LEES MEER</FONT>";
			} else {
				tvTitle.setBackgroundColor(Color
						.parseColor("#666666"));
				readOn = "<FONT COLOR='#666666'> ... LEES MEER</FONT>";
			}
				
			TextView tvArticle = (TextView)getLayoutInflater().inflate(activeFont[1], null);
			tvArticle.setText(Html.fromHtml(a.getDescriptionShortWithoutTags()+readOn));
			

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
