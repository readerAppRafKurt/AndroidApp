package services;

import java.util.List;
import activities.TabletHorizontalOverviewActivity;
import activities.TabletSingleArticleActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import classes.Channel;

public class ChannelScroller extends ScrollView {

	public ChannelScroller(Context context, AttributeSet attrs) {
		super(context, attrs);

		final Activity activity;

		if (context.toString().substring(0, 43)
				.equals("activities.TabletHorizontalOverviewActivity")) {
			activity = (TabletHorizontalOverviewActivity) context;
		} else {
			activity = (TabletSingleArticleActivity) context;
		}

		DatabaseHandler db = new DatabaseHandler(context);

		// get all active channels
		// info
		// http://stackoverflow.com/questions/5763366/how-to-create-multiple-buttons-at-runtime-android
		List<Channel> activeChannels = db.getAllSelectedChannels();

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setBackgroundColor(Color.GRAY);

		for (Channel channel : activeChannels) {
			// for each active channel create a clickable textview

			TextView tv = new TextView(context);
			tv.setText(channel.getDescription().substring(15));
			tv.setTag(channel.get_id());
			if (channel.get_id() % 2 == 0) {
				tv.setBackgroundColor(Color.WHITE);
			}
			tv.setPadding(3, 3, 3, 3);
			tv.setGravity(Gravity.CENTER);

			tv.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// only show the articles for the selected theme
					// start new intent and pass the id of the selected channel
					Intent intent = new Intent(activity,
							TabletHorizontalOverviewActivity.class);

					int sTag = (Integer) v.getTag();

					intent.putExtra("channel_id", Integer.toString(sTag));
					activity.startActivity(intent);
				}
			});

			linearLayout.addView(tv);
		}

		this.addView(linearLayout);

	}

}
