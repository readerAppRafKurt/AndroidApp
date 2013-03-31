package activities;

import java.util.List;
import services.ChannelScroller;
import services.DatabaseHandler;
import services.FontDao;
import services.ThemeDao;
import services.UploadXML;
import classes.Article;
import classes.AsyncTaskLoadXML_;
import classes.Channel;
import classes.Utils;
import classes.articleListener;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TabletHorizontalOverviewActivity extends Activity {

	DatabaseHandler db;

	TextView article1title,article1description,article2title,article2description,article3title,article3description,
	article4title,article4description,article5title,article5description,article6title,article6description,
	article7title,article7description,article8title,article8description,article9title,article9description,
	article10title,article10description,article11title,article11description,article12title,article12description,
	article13title,article13description,article14title,article14description,article15title,article15description;
	
	ChannelScroller channelTabletOverviewChannelScroller;
	ProgressBar progress;
	
	List<Article> articles;	
	Channel channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// set the database
		db = new DatabaseHandler(TabletHorizontalOverviewActivity.this);
		FontDao.setActiveFont(getBaseContext());
		FontDao.setUtilsFonts(db.getActiveFont());
		ThemeDao.setUtilsTheme(db.getActiveTheme());
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activitytablethorizontaloverview);
		
		//get list of articles for selected feed on the right
		
		//get selected channel
		Intent in = getIntent();

		int channelId = Integer.parseInt(in.getStringExtra("channel_id"));
		
		channel=db.getChannelById(channelId);
			
		//get articles for this channel
		articles=db.getArticlesForChannel(channel);
		
		//fill in the textfields
		this.fillInFields();
		
		//set progressBar	
		progress = (ProgressBar) findViewById(R.id.progressTablet);
		progress.setVisibility(View.GONE);
		
		//start background service
		new AsyncTaskLoadXML_(getBaseContext(), progress,db,TabletHorizontalOverviewActivity.this).execute();
	}
	
	
	//method to programmatically restart activity
	public void restart(){
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
		//if user goes back to overview screen.
		//articles have to be updated
		super.onRestart();
		Log.w("testTablet", "in onRestart");
		
		//get articles for this channel
		articles=db.getArticlesForChannel(channel);
		
		//fill in the textfields
		this.fillInFields();
		
		//set progressBar
		if(UploadXML.isAlreadyBusy()==true){
			//keep the same progressbar
			//get position of progressbar
			int posProgress=progress.getProgress();
			progress = (ProgressBar) findViewById(R.id.progressTablet);
			progress.setProgress(posProgress);
			progress.setVisibility(View.VISIBLE);
		}
		else {
		progress = (ProgressBar) findViewById(R.id.progressTablet);
		progress.setVisibility(View.GONE);
		//start background service
		new AsyncTaskLoadXML_(getBaseContext(), progress,db,TabletHorizontalOverviewActivity.this).execute();
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
			Intent i = new Intent(TabletHorizontalOverviewActivity.this, RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(TabletHorizontalOverviewActivity.this, SettingsActivity.class);
			startActivity(ii);

			break;
		}
		return false;
	}
	
	
	private void fillInFields(){
		
		//article 1
		Article a1= articles.get(0);
		LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.article1);
		linearLayout1.setOnClickListener(new articleListener(a1));		
		article1title=(TextView) findViewById(R.id.article1title);
		article1description=(TextView) findViewById(R.id.article1description);
		article1title.setText(Html.fromHtml("<P><B>"+a1.getTitle()+"</B></P>"));
		article1description.setText(Html.fromHtml(a1.getDescription()));
		//article 2
		Article a2= articles.get(1);
		LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.article2);
		linearLayout2.setOnClickListener(new articleListener(a2));
		article2title=(TextView) findViewById(R.id.article2title);
		article2description=(TextView) findViewById(R.id.article2description);
		article2title.setText(Html.fromHtml("<P><B>"+a2.getTitle()+"</B></P>"));
		article2description.setText(Html.fromHtml(a2.getDescription()));
		//article 3
		Article a3= articles.get(2);
		LinearLayout linearLayout3=(LinearLayout)findViewById(R.id.article3);
		linearLayout3.setOnClickListener(new articleListener(a3));
		article3title=(TextView) findViewById(R.id.article3title);
		article3description=(TextView) findViewById(R.id.article3description);
		article3title.setText(Html.fromHtml("<P><B>"+a3.getTitle()+"</B></P>"));
		article3description.setText(Html.fromHtml(a3.getDescription()));		
		//article 4
		Article a4= articles.get(3);
		LinearLayout linearLayout4=(LinearLayout)findViewById(R.id.article4);
		linearLayout4.setOnClickListener(new articleListener(a4));
		article4title=(TextView) findViewById(R.id.article4title);
		article4description=(TextView) findViewById(R.id.article4description);
		article4title.setText(Html.fromHtml("<P><B>"+a4.getTitle()+"</B></P>"));
		article4description.setText(Html.fromHtml(a4.getDescription()));
		//article 5
		Article a5= articles.get(4);
		LinearLayout linearLayout5=(LinearLayout)findViewById(R.id.article5);
		linearLayout5.setOnClickListener(new articleListener(a5));
		article5title=(TextView) findViewById(R.id.article5title);
		article5description=(TextView) findViewById(R.id.article5description);
		article5title.setText(Html.fromHtml("<P><B>"+a5.getTitle()+"</B></P>"));
		article5description.setText(Html.fromHtml(a5.getDescription()));
		//article 6
		Article a6= articles.get(5);
		LinearLayout linearLayout6=(LinearLayout)findViewById(R.id.article6);
		linearLayout6.setOnClickListener(new articleListener(a6));
		article6title=(TextView) findViewById(R.id.article6title);
		article6description=(TextView) findViewById(R.id.article6description);
		article6title.setText(Html.fromHtml("<P><B>"+a6.getTitle()+"</B></P>"));
		article6description.setText(Html.fromHtml(a6.getDescription()));
		//article 7
		Article a7= articles.get(6);
		LinearLayout linearLayout7=(LinearLayout)findViewById(R.id.article7);
		linearLayout7.setOnClickListener(new articleListener(a7));
		article7title=(TextView) findViewById(R.id.article7title);
		article7description=(TextView) findViewById(R.id.article7description);
		article7title.setText(Html.fromHtml("<P><B>"+a7.getTitle()+"</B></P>"));
		article7description.setText(Html.fromHtml(a7.getDescription()));		
		//article 8
		Article a8= articles.get(7);
		LinearLayout linearLayout8=(LinearLayout)findViewById(R.id.article8);
		linearLayout8.setOnClickListener(new articleListener(a8));
		article8title=(TextView) findViewById(R.id.article8title);
		article8description=(TextView) findViewById(R.id.article8description);
		article8title.setText(Html.fromHtml("<P><B>"+a8.getTitle()+"</B></P>"));
		article8description.setText(Html.fromHtml(a8.getDescription()));
		//article 9
		Article a9= articles.get(8);
		LinearLayout linearLayout9=(LinearLayout)findViewById(R.id.article9);
		linearLayout9.setOnClickListener(new articleListener(a9));
		article9title=(TextView) findViewById(R.id.article9title);
		article9description=(TextView) findViewById(R.id.article9description);
		article9title.setText(Html.fromHtml("<P><B>"+a9.getTitle()+"</B></P>"));
		article9description.setText(Html.fromHtml(a9.getDescription()));
		//article 10
		Article a10= articles.get(9);
		LinearLayout linearLayout10=(LinearLayout)findViewById(R.id.article10);
		linearLayout10.setOnClickListener(new articleListener(a10));
		article10title=(TextView) findViewById(R.id.article10title);
		article10description=(TextView) findViewById(R.id.article10description);
		article10title.setText(Html.fromHtml("<P><B>"+a10.getTitle()+"</B></P>"));
		article10description.setText(Html.fromHtml(a10.getDescription()));
		//article 11
		Article a11= articles.get(10);
		LinearLayout linearLayout11=(LinearLayout)findViewById(R.id.article11);
		linearLayout11.setOnClickListener(new articleListener(a11));
		article11title=(TextView) findViewById(R.id.article11title);
		article11description=(TextView) findViewById(R.id.article11description);
		article11title.setText(Html.fromHtml("<P><B>"+a11.getTitle()+"</B></P>"));
		article11description.setText(Html.fromHtml(a11.getDescription()));
		//article 12
		Article a12= articles.get(11);
		LinearLayout linearLayout12=(LinearLayout)findViewById(R.id.article12);
		linearLayout12.setOnClickListener(new articleListener(a12));
		article12title=(TextView) findViewById(R.id.article12title);
		article12description=(TextView) findViewById(R.id.article12description);
		article12title.setText(Html.fromHtml("<P><B>"+a12.getTitle()+"</B></P>"));
		article12description.setText(Html.fromHtml(a12.getDescription()));
		//article 13
		Article a13= articles.get(12);
		LinearLayout linearLayout13=(LinearLayout)findViewById(R.id.article13);
		linearLayout13.setOnClickListener(new articleListener(a13));
		article13title=(TextView) findViewById(R.id.article13title);
		article13description=(TextView) findViewById(R.id.article13description);
		article13title.setText(Html.fromHtml("<P><B>"+a13.getTitle()+"</B></P>"));
		article13description.setText(Html.fromHtml(a13.getDescription()));
		//article 14
		Article a14= articles.get(13);
		LinearLayout linearLayout14=(LinearLayout)findViewById(R.id.article14);
		linearLayout14.setOnClickListener(new articleListener(a14));
		article14title=(TextView) findViewById(R.id.article14title);
		article14description=(TextView) findViewById(R.id.article14description);
		article14title.setText(Html.fromHtml("<P><B>"+a14.getTitle()+"</B></P>"));
		article14description.setText(Html.fromHtml(a14.getDescription()));
		//article 15
		Article a15= articles.get(14);
		LinearLayout linearLayout15=(LinearLayout)findViewById(R.id.article15);
		linearLayout15.setOnClickListener(new articleListener(a15));
		article15title=(TextView) findViewById(R.id.article15title);
		article15description=(TextView) findViewById(R.id.article15description);
		article15title.setText(Html.fromHtml("<P><B>"+a15.getTitle()+"</B></P>"));
		article15description.setText(Html.fromHtml(a15.getDescription()));
		
		
		//ScrollView channels
		channelTabletOverviewChannelScroller=(ChannelScroller)findViewById(R.id.channelTabletOverviewChannelScroller);
	}
}
