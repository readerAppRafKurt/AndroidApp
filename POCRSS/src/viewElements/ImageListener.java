package viewElements;

import activities.FullScreenImageActivity;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ImageListener implements OnClickListener{
	
	private String pathToImage;
	
	public ImageListener(String pathToImage){
		this.pathToImage=pathToImage;
	}

	public void onClick(View v) {
		
		Activity currentActivity=(Activity)v.getContext();

		Intent intent = new Intent(currentActivity,FullScreenImageActivity.class);
		
		Log.w("test","in onClickListener");
		//intent put extras
		//tried to pass the image but error FAILED BINDER TRANSACTION
		//if found no workaround, therefore, I passed the path to the image
		intent.putExtra("pathToImage", pathToImage);
	
		currentActivity.startActivity(intent);		
	}
}
