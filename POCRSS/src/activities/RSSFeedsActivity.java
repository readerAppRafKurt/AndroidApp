package activities;

import java.util.ArrayList;
import java.util.List;

import services.ActivityDao;
import services.DatabaseHandler;
import classes.Article;
import classes.Channel;
import classes.Utils;

import com.example.pocrss.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RSSFeedsActivity extends Activity {

	private CheckBox chkBinnenland, chkBuitenland, chkCultuur, chkEconomie,
			chkLife;
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choicefeedsactivity);
		
		//register as active 
		ActivityDao.addActivity(this);

		// set the checkboxes for the different channels by reading the database
		// setting the checkboxes and confirm button
		chkBinnenland = (CheckBox) findViewById(R.id.chkBinnenland);
		chkBuitenland = (CheckBox) findViewById(R.id.chkBuitenland);
		chkCultuur = (CheckBox) findViewById(R.id.chkCultuur);
		chkEconomie = (CheckBox) findViewById(R.id.chkEconomie);
		chkLife = (CheckBox) findViewById(R.id.chkLife);

		// set the db
		db = new DatabaseHandler(this);
		// get the selected channels from the database
		List<Channel> channels = db.getAllSelectedChannels();

		// toggle the selected checkboxes
		for (int i = 0; i < channels.size(); i++) {

			switch (channels.get(i).get_id()) {

			case 1:
				chkBinnenland.setChecked(true);
				break;
			case 2:
				chkBuitenland.setChecked(true);
				break;
			case 3:
				chkCultuur.setChecked(true);
				break;
			case 4:
				chkEconomie.setChecked(true);
				break;
			case 5:
				chkLife.setChecked(true);
				break;
			}
		}

		// onchangeClickListeners. So channels can be updated to active or non
		// active
		chkBinnenland.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = db.getChannelById(1);
					channel.setSelected(true);
					db.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = db.getChannelById(1);
					channel.setSelected(false);
					db.updateChannel(channel);

					
					//TODO put this in background
					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = db.getArticlesForChannel(channel);

					for (Article article : articles) {
						db.removeArticle(article);
					}
				}
			}
		});

		chkBuitenland.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = db.getChannelById(2);
					channel.setSelected(true);
					db.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = db.getChannelById(2);
					channel.setSelected(false);
					db.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = db.getArticlesForChannel(channel);

					for (Article article : articles) {
						db.removeArticle(article);
					}
				}
			}
		});

		chkCultuur.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = db.getChannelById(3);
					channel.setSelected(true);
					db.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = db.getChannelById(3);
					channel.setSelected(false);
					db.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = db.getArticlesForChannel(channel);

					for (Article article : articles) {
						db.removeArticle(article);
					}
				}
			}
		});

		chkEconomie.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = db.getChannelById(4);
					channel.setSelected(true);
					db.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = db.getChannelById(4);
					channel.setSelected(false);
					db.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = db.getArticlesForChannel(channel);

					for (Article article : articles) {
						db.removeArticle(article);
					}
				}
			}
		});

		chkLife.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = db.getChannelById(5);
					channel.setSelected(true);
					db.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = db.getChannelById(5);
					channel.setSelected(false);
					db.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = db.getArticlesForChannel(channel);

					for (Article article : articles) {
						db.removeArticle(article);
					}
				}
			}
		});
	}

	// catch the on backPressed event
	// if no channel selected error message
	@Override
	public void onBackPressed() {
		if (!chkBinnenland.isChecked() && !chkBuitenland.isChecked()
				&& !chkCultuur.isChecked() && !chkEconomie.isChecked()
				&& !chkLife.isChecked()) {
			this.alertMessageNoThemeSelected(RSSFeedsActivity.this,
					"Keuze feed", "Je moet minstens 1 feed kiezen", true);
		} else {
			super.onBackPressed();
		}
	}

	// method to show a messagebox when no channel is selected
	public void alertMessageNoThemeSelected(Context context, String title,
			String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);

		// set ok button
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}
	
	private class AsyncTaskRemoveArticlesFromDb extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			//remove articles from themes that are deselected
			
			//insert articles from themes that are selected
			return null;
		}
		
		
		
	}
	
}
