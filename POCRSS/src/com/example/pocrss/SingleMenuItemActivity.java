package com.example.pocrss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;


public class SingleMenuItemActivity extends Activity{

	
	// XML node keys
	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_DESCRIPTION = "description";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get XML values from previous intent
        String title = in.getStringExtra(KEY_TITLE);
        String link = in.getStringExtra(KEY_LINK);
        String description = in.getStringExtra(KEY_DESCRIPTION);
        
        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.title_label);
        TextView lblLink = (TextView) findViewById(R.id.link_label);
        TextView lblDescription = (TextView) findViewById(R.id.description_label);
        
        lblTitle.setText(Html.fromHtml(title.toString()));
        lblLink.setText(Html.fromHtml(link.toString()));
        lblDescription.setText(Html.fromHtml(description.toString()));
    }
}
