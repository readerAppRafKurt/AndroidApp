package activities;

import services.ActivityDao;
import services.ImageDao;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleArticleSearchActivity extends Activity {
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item);
		
		// register activity as active
		ActivityDao.addActivity(this);

		// set the custom theme
		Utils.setThemeToActivity(this);

		// getting intent data
		in = getIntent();

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
		}
	}
}
