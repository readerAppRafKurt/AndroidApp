package classes;

import activities.PhoneSingleArticleActivity;
import activities.TabletHorizontalOverviewActivity;
import activities.TabletSingleArticleActivity;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class articleListener implements OnClickListener{
	
	private Article article;
	
	public articleListener(Article article){
		this.article=article;
	}

	public void onClick(View v) {
		
		Activity currentActivity=(Activity)v.getContext();

		Intent intent;
		
		if (currentActivity.toString().substring(0, 25)
				.equals("activities.Phone3Activity")) {
			
			intent = new Intent(currentActivity,PhoneSingleArticleActivity.class);
			
		} else {
			intent = new Intent(currentActivity,TabletSingleArticleActivity.class);
		}
		
		//intent put extras
		intent.putExtra("article_id", Integer.toString(article.get_id()));
		intent.putExtra("title", article.getTitle());
		intent.putExtra("enclosure", article.getEnclosure());
		intent.putExtra("description", article.getDescription());
		intent.putExtra("pubDate", article.getPubDate().toString());
		intent.putExtra("channel_id", Integer.toString(article.getChannel().get_id()));
		
		currentActivity.startActivity(intent);
		
	}

}
