package services;

import classes.Article;
import activities.PhoneSingleArticleActivity;
import activities.TabletSingleArticleActivity;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class SwipeGesture extends SimpleOnGestureListener {
	private static final int SWIPE_MIN_DISTANCE = 50;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	// the previous and next article
	private Article previousArticle, nextArticle;
	private Activity oldActivity;
	
	private Intent in;

	//constructor for phones
	public SwipeGesture(PhoneSingleArticleActivity phoneSingleActivity) {
		super();
		previousArticle = phoneSingleActivity.getPreviousArticle();
		nextArticle = phoneSingleActivity.getNextArticle();
		oldActivity = phoneSingleActivity;
		
		//set intent
		in = new Intent(oldActivity,
				PhoneSingleArticleActivity.class);
	}
	
	//constructor for tablets
	public SwipeGesture(TabletSingleArticleActivity tabletSingleArticleActivity) {
		super();
		previousArticle = tabletSingleArticleActivity.getPreviousArticle();
		nextArticle = tabletSingleArticleActivity.getNextArticle();
		oldActivity = tabletSingleArticleActivity;
		
		//set intent
		in = new Intent(oldActivity,
				TabletSingleArticleActivity.class);
	}

	// http://stackoverflow.com/questions/10008053/android-trouble-with-swipe-gesture
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

				// go to the previous article
				//Intent in = new Intent(oldActivity,
				//		SingleMenuItemActivity.class);
				Article listItem = previousArticle;
				in.putExtra("article_id", Integer.toString(listItem.get_id()));
				in.putExtra("title", listItem.getTitle());
				in.putExtra("enclosure", listItem.getEnclosure());
				in.putExtra("description", listItem.getDescription());
				in.putExtra("pubDate", listItem.getPubDate().toString());
				in.putExtra("channel_id", Integer.toString(listItem.getChannel().get_id()));
				oldActivity.startActivity(in);

				// close old activity
				oldActivity.finish();

				Log.e("", "Swipe left");

			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

				//Intent in = new Intent(
				//		oldActivity.getApplicationContext(),
				//		SingleMenuItemActivity.class);
				Article listItem = nextArticle;
				in.putExtra("article_id", Integer.toString(listItem.get_id()));
				in.putExtra("title", listItem.getTitle());
				in.putExtra("enclosure", listItem.getEnclosure());
				in.putExtra("description", listItem.getDescription());
				in.putExtra("pubDate", listItem.getPubDate().toString());
				in.putExtra("channel_id", Integer.toString(listItem.getChannel().get_id()));
				oldActivity.startActivity(in);

				// close old activity
				oldActivity.finish();
				// go to the next article
				Log.e("", "Swipe right");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

}