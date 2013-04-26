package activities;

import java.util.List;
import viewElements.ImageListener;
import viewElements.SwipeGesture;
import classes.Article;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import dao.ArticleDao;
import dao.ChannelDao;
import dao.ImageDao;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhoneSingleArticleActivity extends Activity {

	// XML node keys
	static final String KEY_ID = "article_id";
	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_PUBDATE = "pubDate";
	static final String KEY_ENCLOSURE = "enclosure";
	static final String FOREIGN_KEY_CHANNEL = "channel_id";

	Intent in;

	ImageView imageArticle;

	// gesture detector
	private GestureDetector gestureDetector;

	// the id of the previous and next article
	private Article previousArticle, nextArticle;
	
	private TextView tvTitleBar;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item);
		
		// getting intent data
		in = getIntent();

		Channel channel = ChannelDao.getChannelById(Integer.parseInt(in
				.getStringExtra(FOREIGN_KEY_CHANNEL)));
		
		
		//set titleBar
		//PhoneSingleArticleActivity.this.setTitle(channel.getDescription());
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(channel.getDescription());
		
		Article articleNow = ArticleDao.getArticleById(Integer.parseInt(in
				.getStringExtra(KEY_ID)));
				
		List<Article> articlesInDb = ChannelDao.getArticlesForChannel(channel);
				
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
		ScrollView root = (ScrollView) findViewById(R.id.scroll_view_start_test_for_all);

		root.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return false;
				}
				return false;
			}
		});
		// end swipe listener

		// set imageView article
		imageArticle = (ImageView) findViewById(R.id.articleImage);

		// Get XML values from previous intent
		String title = in.getStringExtra(KEY_TITLE);
		String enclosure = in.getStringExtra(KEY_ENCLOSURE);
		String description = in.getStringExtra(KEY_DESCRIPTION);
		String date = in.getStringExtra(KEY_PUBDATE);

		// Displaying all values on the screen
		TextView lblTitle = (TextView) findViewById(R.id.title_label);
		TextView lblLink = (TextView) findViewById(R.id.link_label);
		TextView lblDescription = (TextView) findViewById(R.id.description_label);

		lblTitle.setText(Html.fromHtml(title.toString()));
		lblLink.setText(Html.fromHtml(date.toString()));
		lblDescription.setText(Html.fromHtml(description.toString()));

		// fill imageView
		if (enclosure != null && enclosure != "") {
			Bitmap articleImage = ImageDao
					.getImage(getBaseContext(), enclosure);
			imageArticle.setImageBitmap(articleImage);
			imageArticle.setOnClickListener(new ImageListener(enclosure));
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
