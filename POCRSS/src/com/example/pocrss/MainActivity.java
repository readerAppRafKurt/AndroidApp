package com.example.pocrss;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {

	// All static variables
	static final String URL = "http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=55178e67-15a8-4ddd-a3d8-bfe5708f8932";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_DESCRIPTION = "description";
	ArrayList<HashMap<String, String>> menuItems;

    //TextView txVLink;
    //TextView txVDescription;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
			map.put(KEY_DESCRIPTION, parser.getValue(e, KEY_DESCRIPTION));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				//new String[] { KEY_TITLE, KEY_DESCRIPTION, KEY_LINK }, new int[] {
				//		R.id.title, R.id.desciption, R.id.link });
				new String[] { KEY_TITLE, KEY_DESCRIPTION, KEY_LINK }, new int[] {
						R.id.title});


		setListAdapter(adapter);
		

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
				//String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
				//String description = ((TextView) view.findViewById(R.id.desciption)).getText().toString();
				String link="";
				String description="";
				
				//mogelijkheid om link en discription uit de eerste pagina te halen
				
				for (int i = 0; i < menuItems.size(); i++) {
				    if(menuItems.get(i).containsValue(title)){
				        link= menuItems.get(i).get("link");
				        description=menuItems.get(i).get("description");
				    }
				}
				
				
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_TITLE, title);
				in.putExtra(KEY_LINK, link);
				in.putExtra(KEY_DESCRIPTION, description);
				startActivity(in);

			}
		});
	}
	
}
