package activities;

import java.util.List;
import services.DatabaseHandler;
import services.FontDao;
import services.UploadXML;
import classes.Article;
import classes.AsyncTaskLoadXML_;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ListTitlesArticleForSingleThemeActivity extends Activity {

	ListView listView;
	ProgressBar progress;
	int[] activeFont;
	
	DatabaseHandler db;
	Intent in;
	List<Article> articlesForThemeAtCreate;
	List<Article> articlesForThemeAtUpdate;
	Channel channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_titles_article_for_single_theme);

		// set the listview
		listView = (ListView) findViewById(R.id.listArticlesForTheme);

		// get intent data
		in = getIntent();

		// set the db
		db = new DatabaseHandler(ListTitlesArticleForSingleThemeActivity.this);
		// get the channel from db
		channel = db.getChannelById(Integer.parseInt(in
				.getStringExtra("channelId")));

		// get the articles for this channel
		articlesForThemeAtCreate = db.getArticlesForChannel(channel);
		
		// set a new title for the screen/activity
		ListTitlesArticleForSingleThemeActivity.this.setTitle(channel
				.getDescription());
		
		progress = (ProgressBar) findViewById(R.id.progressBarPhone2);
		progress.setVisibility(View.GONE);

		// start background service
		new AsyncTaskLoadXML_(getBaseContext(), progress, db,
				ListTitlesArticleForSingleThemeActivity.this).execute();
		
		this.fillInFields(articlesForThemeAtCreate);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		// if user goes back to overview screen.
		// articles have to be updated
		super.onRestart();

		Log.w("test",
				"in onRestart met reeds bezig "
						+ UploadXML.isRefreshScreenPause());
		if (UploadXML.isRefreshScreenPause() == false) {
			Log.w("test", "in onRestart");
			refreshScreen();

			Log.w("test",
					"in onRestart met reeds bezig " + UploadXML.isAlreadyBusy());
			if (UploadXML.isAlreadyBusy() != true) {
				// start background service
				new AsyncTaskLoadXML_(getBaseContext(), progress, db,
						ListTitlesArticleForSingleThemeActivity.this)
						.execute();
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
				
				//listView.removeAllViews();
				
				// set the listview
				listView = (ListView) findViewById(R.id.listArticlesForTheme);
				
				// set a new title for the screen/activity
				ListTitlesArticleForSingleThemeActivity.this.setTitle(channel
						.getDescription());

				fillInFields(articlesForThemeAtUpdate);
			}
		});

	}

	private void fillInFields(List<Article> articlesIn) {
		
		//get active font
		activeFont=FontDao.getLayoutXML();

		Article[] articles = new Article[articlesIn.size()];

		for (int i = 0; i < articlesIn.size(); i++) {
			articles[i] = articlesIn.get(i);
		}
		
		ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(
				ListTitlesArticleForSingleThemeActivity.this,
				activeFont[1], articles);

		/*ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(
				ListTitlesArticleForSingleThemeActivity.this,
				android.R.layout.simple_list_item_1, articles);*/

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent in = new Intent(getApplicationContext(),
						PhoneSingleArticleActivity.class);
				Article listItem = (Article) listView
						.getItemAtPosition(position);
				in.putExtra("article_id", Integer.toString(listItem.get_id()));
				in.putExtra("title", listItem.getTitle());
				in.putExtra("enclosure", listItem.getEnclosure());
				in.putExtra("description", listItem.getDescription());
				in.putExtra("pubDate", listItem.getPubDate().toString());
				in.putExtra("channel_id",
						Integer.toString(listItem.getChannel().get_id()));
				startActivity(in);

			}
		});

		listView.setAdapter(adapter);

	}

}
