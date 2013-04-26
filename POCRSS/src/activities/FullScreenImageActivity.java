package activities;

import classes.Utils;
import com.example.pocrss.R;

import dao.ActivityDao;
import dao.ImageDao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.Window;
import android.widget.ImageView;

public class FullScreenImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_screen_image);

		// register activity as active
		ActivityDao.addActivity(this);

		Intent in = getIntent();

		ImageView imageView = (ImageView) findViewById(R.id.imageFullScreen);
		String enclosure = in.getStringExtra("pathToImage");

		Bitmap articleImage = ImageDao.getImage(getBaseContext(), enclosure);

		//fix the orientation of screen on the basis of the size of the bitmap
		// bitmap
		int bitmapHeight = articleImage.getHeight();
		int bitmapWidth = articleImage.getWidth();

		if (bitmapHeight < bitmapWidth) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}// square + portrait
		else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		imageView.setImageBitmap(articleImage);
	}
}
