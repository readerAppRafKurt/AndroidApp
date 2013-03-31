package services;

import java.util.Date;

import com.example.pocrss.R;

import classes.Article;
import classes.articleListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleAdapter extends ArrayAdapter<Article> {

	Context context;
	int layoutResourceId;
	Article data[] = null;

	public ArticleAdapter(Context context, int layoutResourceId, Article[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		//ArticleHolder holder = null;

		if (row == null) {
			// different layout for the first article
			if (position == 0) {
				LayoutInflater inflaterArticle1 = ((Activity) context)
						.getLayoutInflater();
				row = inflaterArticle1.inflate(R.layout.first_article_phone3, parent,false);

				ArticleHolder holder = new ArticleHolder();
				holder.imgIcon = (ImageView) row.findViewById(R.id.imgIconArticle1);
				holder.txtTitle = (TextView) row.findViewById(R.id.txtTitleArticle1);
				holder.txtDate=new TextView(getContext());

				row.setTag(holder);
				
				//this.insertFirstArticle(row,holder, parent, position);
				Article article = data[position];

				holder.txtTitle.setText(article.getTitle());

				// fill imageView
				if (article.getEnclosure() != null && article.getEnclosure() != "") {
					Bitmap articleImage = ImageDao.getImage(getContext(),
							article.getEnclosure());
					holder.imgIcon.setImageBitmap(articleImage);
				}
				else{
					holder.imgIcon.setImageBitmap(null);
				}

				row.setOnClickListener(new articleListener(article));

			} else {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				ArticleHolder holder = new ArticleHolder();
				holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
				holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
				holder.txtDate = (TextView) row.findViewById(R.id.txtDate);

				row.setTag(holder);
				
				//this.insertOtherArticles(row,holder, parent, position);
				Article article = data[position];

				holder.txtTitle.setText(article.getTitle());

				// create times ago
				Date d = new Date();
				String timeAgo = (String) DateUtils.getRelativeTimeSpanString(article
						._getPubDate().getTime(), d.getTime(), 0L,
						DateUtils.FORMAT_ABBREV_ALL);
				
				holder.txtDate.setText(timeAgo);

				// fill imageView
				if (article.getEnclosure() != null && article.getEnclosure() != "") {
					Bitmap articleImage = ImageDao.getImage(getContext(),
							article.getEnclosure());
					holder.imgIcon.setImageBitmap(articleImage);
				}
				else{
					holder.imgIcon.setImageBitmap(null);
				}
				
				row.setOnClickListener(new articleListener(article));
			}
		} else {
			ArticleHolder holder = (ArticleHolder) row.getTag();
			if (position == 0) {
				//this.insertFirstArticle(row,holder, parent, position);
				Article article = data[position];

				holder.txtTitle.setText(article.getTitle());

				// fill imageView
				if (article.getEnclosure() != null && article.getEnclosure() != "") {
					Bitmap articleImage = ImageDao.getImage(getContext(),
							article.getEnclosure());
					holder.imgIcon.setImageBitmap(articleImage);
				}
				else{
					holder.imgIcon.setImageBitmap(null);
				}

				row.setOnClickListener(new articleListener(article));
				

			} else {
				//this.insertOtherArticles(row,holder, parent, position);
				Article article = data[position];

				holder.txtTitle.setText(article.getTitle());

				// create times ago
				Date d = new Date();
				String timeAgo = (String) DateUtils.getRelativeTimeSpanString(article
						._getPubDate().getTime(), d.getTime(), 0L,
						DateUtils.FORMAT_ABBREV_ALL);
				
				holder.txtDate.setText(timeAgo);

				// fill imageView
				if (article.getEnclosure() != null && article.getEnclosure() != "") {
					Bitmap articleImage = ImageDao.getImage(getContext(),
							article.getEnclosure());
					holder.imgIcon.setImageBitmap(articleImage);
				}
				else{
					holder.imgIcon.setImageBitmap(null);
				}
				
				row.setOnClickListener(new articleListener(article));
			}
		}
		return row;
	}

	static class ArticleHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView txtDate;
	}
}
