package viewElements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import classes.Article;

public class TitleAdapter extends ArrayAdapter<Article>{
	
	Context context;
	int layoutResourceId;
	Article data[] = null;

	public TitleAdapter(Context context, int layoutResourceId,Article[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		
		
		return row;
	}

}
