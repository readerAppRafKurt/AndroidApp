package classes;

import java.util.Comparator;

public class ArticleComparator implements Comparator<Article>{
	    public int compare(Article a1, Article a2) {
	        return a1._getPubDate().compareTo(a2._getPubDate());
	    }
	}

