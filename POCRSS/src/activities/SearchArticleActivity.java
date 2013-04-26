package activities;


import java.util.List;
import classes.Article;
import classes.Utils;
import com.example.pocrss.R;
import dao.ActivityDao;
import dao.ArticleDao;
import dao.FontDao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchArticleActivity extends Activity {
		
	Button btnSearchArticleById;
	EditText editTextSearchArticleById;
	List<Article> listArticles;
	ListView listView;
	private TextView tvTitleBar;
	
	//swipe mogelijkheid helemaal uit zetten
    @Override
    public void onCreate(Bundle savedInstanceState) {	
    	
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    		
    	Utils.setThemeToActivity(this);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);      
        
        
		// register activity as active
		ActivityDao.addActivity(this);
		
		//set new content titleBar
		tvTitleBar=(TextView)findViewById(R.id.tvTitleBar);
		tvTitleBar.setText(R.string.title_activity_search_article);
        
		btnSearchArticleById = FontDao.modifiedButton((Button) findViewById(R.id.btnSearchArticleById), getBaseContext());
        
        editTextSearchArticleById=(EditText)findViewById(R.id.editTextSearchArticleById);
        listView =(ListView)findViewById(R.id.listFoundArticles);
        
        btnSearchArticleById.setOnClickListener(new OnClickListener(
        		
        		){
					public void onClick(View v) {
						//voorlopig enkel zoeken in titel. Ook in omschrijving?
						
						String userInput=editTextSearchArticleById.getText().toString();
						listArticles=ArticleDao.getArticlesForSearch(userInput);
						
						
					      Article[] articles=new Article[listArticles.size()];
					      
					        for (int i=0;i<listArticles.size();i++){
					        articles[i]=listArticles.get(i);
					        }	
					      
					        ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(SearchArticleActivity.this,
							          R.layout.simple_list_item_1_mod, articles);
		 
					        listView.setOnItemClickListener(new OnItemClickListener(){

								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									Intent in = new Intent(getApplicationContext(), SingleArticleSearchActivity.class);
									Article listItem = (Article)listView.getItemAtPosition(position);
									
									in.putExtra("article_id", Integer.toString(listItem.get_id()));
									in.putExtra("title", listItem.getTitle());
									in.putExtra("enclosure", listItem.getEnclosure());
									in.putExtra("description", listItem.getDescription());
									in.putExtra("pubDate", listItem.getPubDate().toString());
									in.putExtra("channel_id", Integer.toString(listItem.getChannel().get_id()));
									startActivity(in);	
								}});

					        listView.setAdapter(adapter);
					}}); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_article, menu);
        return true;
    }
}
