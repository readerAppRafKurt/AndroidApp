package tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import model.newsItems.NewsItem;
import model.newsItems.NewsItemNieuwsbladImpl;
import annotations.Rss;

public class Annotationtest {

	/**
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		
		NewsItem nieuwsItem = new NewsItemNieuwsbladImpl();
		Class clazz = nieuwsItem.getClass();
	    Field[] fields = clazz.getDeclaredFields();
	    ArrayList<String> properties = new ArrayList();
   
	    for (Field f : fields) {
	      
	      Annotation a = f.getAnnotation(Rss.class);

	      if (a != null) {
	        //System.out.println(f.getName());
	        properties.add(f.getName());
	      }	      
	    }

	    for (String property : properties){
	    	  System.out.println(property.toString());
	    }
	}

}
