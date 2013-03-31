package services;

import java.util.List;
import classes.Article;
import classes.Channel;
import classes.articleListener;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArticleScroller extends HorizontalScrollView {

	DatabaseHandler db;

	public ArticleScroller(Context context, AttributeSet attrs) {
		super(context, attrs);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setBackgroundColor(Color.GRAY);

		db = new DatabaseHandler(context);
		Channel c = new Channel();
		c.set_id(1);
		// get all the articles for the selected channel
		List<Article> articles = db.getArticlesForChannel(c);
		
		for (Article article : articles) {
			TextView tv = new TextView(context);
			tv.setText(" "+ article.getTitle()+" ");
			tv.setOnClickListener(new articleListener(article));

			if (article.get_id() % 2 == 0) {
				tv.setBackgroundColor(Color.WHITE);
			}

			linearLayout.addView(tv);
		}
		this.addView(linearLayout);
	}
}
