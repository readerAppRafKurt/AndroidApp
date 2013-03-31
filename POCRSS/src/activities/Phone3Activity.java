package activities;

import java.util.List;

import services.ActivityDao;
import services.ArticleAdapter;
import services.DatabaseHandler;
import services.ImageDao;
import classes.Article;
import classes.Channel;
import classes.Utils;

import com.example.pocrss.R;
import com.example.pocrss.R.layout;
import com.example.pocrss.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Phone3Activity extends Activity {
	
	ListView listView;
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone3);
		
		db=new DatabaseHandler(this);
		
		//get list of articles
		Channel channel=new Channel();
		channel.set_id(1);
		List<Article> articles=db.getArticlesForChannel(channel);
		
        
		//list of articles from 1 to end
        Article article_data[] = new Article[articles.size()];
        articles.toArray(article_data);
   
        ArticleAdapter adapter = new ArticleAdapter(Phone3Activity.this, 
                R.layout.listview_article_row, article_data);
        
        
        listView = (ListView)findViewById(R.id.listViewPhone3);
           
        listView.setAdapter(adapter);		
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
			Intent i = new Intent(Phone3Activity.this,
					RSSFeedsActivity.class);
			startActivity(i);

			break;
		case R.id.settingsMenu:
			// setup new activity and intent to setup the selected settings
			Intent ii = new Intent(Phone3Activity.this, SettingsActivity.class);
			startActivity(ii);

			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		
		//empty the active activities
		ActivityDao.finishAllActivities();
		
		super.onBackPressed();
	}
}
