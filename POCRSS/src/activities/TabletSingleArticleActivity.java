package activities;

import java.util.List;
import services.ChannelScroller;
import services.DatabaseHandler;
import services.ArticleScroller;
import services.ImageDao;
import services.SwipeGesture;
import classes.Article;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TabletSingleArticleActivity extends Activity {

	TextView articleTitleTablet, articleDescriptionTablet;
	ImageView articleImageTablet, articleImageTabletTest;
	LinearLayout rssfeedschoice;

	ArticleScroller articleTabletScroller;
	ChannelScroller channelTabletScroller;
	DatabaseHandler db;

	// the id of the previous and next article
	private Article previousArticle, nextArticle;

	// gesture detector
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);

		// set db
		db = new DatabaseHandler(this);

		// get active layout
		String activeLayout = db.getActiveLayout();

		// set different xml dependent on the active/selected layout
		if (activeLayout.equals("Tablet 2")) {
			setContentView(R.layout.tablet_single_article_scrollview_description);
			articleTabletScroller = (ArticleScroller) findViewById(R.id.articleTabletDescScroll);	
			channelTabletScroller = (ChannelScroller) findViewById(R.id.channelTabletDescScroll);
			
		} else {
			// default layout for tablets
			setContentView(R.layout.activity_tablet_single_article);
			articleTabletScroller = (ArticleScroller) findViewById(R.id.articleTabletInSingleArticleScroller);
			channelTabletScroller = (ChannelScroller) findViewById(R.id.channelTabletInSingleArticleScroller);
		}

		// set intent
		Intent in = getIntent();

		// start test swipen

		// get article and channel id from intent
		Channel channel = db.getChannelById(Integer.parseInt(in
				.getStringExtra("channel_id")));
		Article articleNow = db.getArticle(Integer.parseInt(in
				.getStringExtra("article_id")));

		List<Article> articlesInDb = db.getArticlesForChannel(channel);

		int positie = articlesInDb.indexOf(articleNow);

		// set the previous and next article
		if (positie == articlesInDb.size() - 1) {
			previousArticle = articlesInDb.get(articlesInDb.size() - 2);
			nextArticle = articlesInDb.get(0);
		}
		// if first article
		else if (positie == 0) {
			previousArticle = articlesInDb.get(articlesInDb.size() - 1);
			nextArticle = articlesInDb.get(1);
		} else {
			previousArticle = articlesInDb.get(positie - 1);
			nextArticle = articlesInDb.get(positie + 1);
		}

		// swipe listener
		gestureDetector = new GestureDetector(new SwipeGesture(this));
			
		if (activeLayout.equals("Tablet 2")) {
			//LinearLayout root=(LinearLayout)findViewById(R.id.linearLayoutTabletSingleArticleScroll);
			
			ScrollView root = (ScrollView) findViewById(R.id.scrollDescriptionTablet);
			
			root.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (gestureDetector.onTouchEvent(event)) {
						return false;
					}
					return false;
				}
			});
			
		} else {
			// default layout for tablets
			ScrollView root = (ScrollView) findViewById(R.id.scrollViewTabletSingleArticleScroll);
			root.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (gestureDetector.onTouchEvent(event)) {
						return false;
					}
					return false;
				}
			});
		}
		// end swipe listener
		// end test swipen

		// Get XML values from previous intent
		String title = in.getStringExtra("title");
		String enclosure = in.getStringExtra("enclosure");
		String description = in.getStringExtra("description");
		String date = in.getStringExtra("pubDate");

		// set the components from the view
		articleTitleTablet = (TextView) findViewById(R.id.articleTitleTablet);
		articleTitleTablet
				.setText(Html.fromHtml("<P><B>" + title + "</B></P>"));
		articleDescriptionTablet = (TextView) findViewById(R.id.articleDescriptionTablet);
		articleDescriptionTablet.setText(Html.fromHtml(description));
		articleImageTablet = (ImageView) findViewById(R.id.articleImageTablet);
		

		// fill imageView
		if (enclosure != null && enclosure != "") {
			Bitmap articleImage = ImageDao
					.getImage(getBaseContext(), enclosure);
			articleImageTablet.setImageBitmap(articleImage);
		}
	}

	// getter and setters for the of the previous and next article
	public Article getPreviousArticle() {
		return previousArticle;
	}

	public void setPreviousArticle(Article previousArticle) {
		this.previousArticle = previousArticle;
	}

	public Article getNextArticle() {
		return nextArticle;
	}

	public void setNextArticle(Article nextArticle) {
		this.nextArticle = nextArticle;
	}
}
