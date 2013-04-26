package activities;

import java.util.ArrayList;
import java.util.List;
import classes.Article;
import classes.AsyncTaskUpdateDbWithoutProgressBar;
import classes.Channel;
import classes.Utils;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.ArticleDao;
import dao.ChannelDao;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RSSFeedsActivity extends Activity {

	private CheckBox chkBinnenland, chkBuitenland, chkCultuur, chkEconomie,
			chkLife;
	private TextView tvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choicefeedsactivity);

		// register as active
		ActivityDao.addActivity(this);

		// set the checkboxes for the different channels by reading the database
		// setting the checkboxes and confirm button
		chkBinnenland = (CheckBox) findViewById(R.id.chkBinnenland);
		chkBuitenland = (CheckBox) findViewById(R.id.chkBuitenland);
		chkCultuur = (CheckBox) findViewById(R.id.chkCultuur);
		chkEconomie = (CheckBox) findViewById(R.id.chkEconomie);
		chkLife = (CheckBox) findViewById(R.id.chkLife);

		// set new content titleBar
		tvTitleBar = (TextView) findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.selectionRSSFeeds);

		// get the selected channels from the database
		List<Channel> channels = ChannelDao.getAllSelectedChannels();

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
					Channel channel = ChannelDao.getChannelById(1);
					channel.setSelected(true);
					ChannelDao.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = ChannelDao.getChannelById(1);
					channel.setSelected(false);
					ChannelDao.updateChannel(channel);

					// TODO put this in background
					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = ChannelDao.getArticlesForChannel(channel);
					// only remove the articles if there're articles in the db
					// for that rss feed
					if (articles != null && articles.size() > 1) {
						for (Article article : articles) {
							ArticleDao.removeArticle(article);
						}
					}
				}
			}
		});

		chkBuitenland.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = ChannelDao.getChannelById(2);
					channel.setSelected(true);
					ChannelDao.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = ChannelDao.getChannelById(2);
					channel.setSelected(false);
					ChannelDao.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = ChannelDao.getArticlesForChannel(channel);
					// only remove the articles if there're articles in the db
					// for that rss feed
					if (articles != null && articles.size() > 1) {
						for (Article article : articles) {
							ArticleDao.removeArticle(article);
						}
					}
				}
			}
		});

		chkCultuur.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = ChannelDao.getChannelById(3);
					channel.setSelected(true);
					ChannelDao.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = ChannelDao.getChannelById(3);
					channel.setSelected(false);
					ChannelDao.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = ChannelDao.getArticlesForChannel(channel);
					// only remove the articles if there're articles in the db
					// for that rss feed
					if (articles != null && articles.size() > 1) {

						for (Article article : articles) {
							ArticleDao.removeArticle(article);
						}
					}
				}
			}
		});

		chkEconomie.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = ChannelDao.getChannelById(4);
					channel.setSelected(true);
					ChannelDao.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = ChannelDao.getChannelById(4);
					channel.setSelected(false);
					ChannelDao.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = ChannelDao.getArticlesForChannel(channel);
					// only remove the articles if there're articles in the db
					// for that rss feed
					if (articles != null && articles.size() > 1) {
						for (Article article : articles) {
							ArticleDao.removeArticle(article);
						}
					}
				}
			}
		});

		chkLife.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// activate channel in db
					Channel channel = ChannelDao.getChannelById(5);
					channel.setSelected(true);
					ChannelDao.updateChannel(channel);
				} else {
					// deactivate channel in db
					Channel channel = ChannelDao.getChannelById(5);
					channel.setSelected(false);
					ChannelDao.updateChannel(channel);

					// remove all the articles for this channel in the database
					List<Article> articles = new ArrayList<Article>();
					articles = ChannelDao.getArticlesForChannel(channel);
					// only remove the articles if there're articles in the db
					// for that rss feed
					if (articles != null && articles.size() > 1) {
						for (Article article : articles) {
							ArticleDao.removeArticle(article);
						}
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
					"Keuze feed", "Je moet minstens 1 RSS feed kiezen", true);
		} else {
			// start backgroundActivity to update db
			// start background service
			new AsyncTaskUpdateDbWithoutProgressBar(getBaseContext(),RSSFeedsActivity.this).execute();
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
}
