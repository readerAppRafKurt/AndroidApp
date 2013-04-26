package dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import services.DatabaseHandler;
import services.XMLParser;
import android.content.Context;
import android.util.SparseArray;
import classes.Article;
import classes.ArticleComparator;
import classes.Channel;
import classes.DateFormatter;

public class ArticleDao {

	// XML to read
	static String URL;
	// XML node keys
	static final String KEY_ITEM = "item";
	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_PUBDATE = "pubDate";
	static final String KEY_ENCLOSURE = "enclosure";
	static List<Article> articles;

	static DatabaseHandler db;
	static List<Article> articlesForChannelPresentInDatabase;

	private ArticleDao(String url) {

	}
	
	public static void openDb(Context context){
		db=new DatabaseHandler(context);
	}

	public static void initialize(Channel channel, Context context) throws Exception{

		// set database and list of articles for channel already present in the
		// database
		// used later per article to verify if article is already present in
		// database
		db = new DatabaseHandler(context);
		articlesForChannelPresentInDatabase = db.getArticlesForChannel(channel);

		// set the url of XML to read
		URL = channel.getLink();
		// initialize new list of articles that are included in the XML
		articles = new ArrayList<Article>();

		// DateFormat df =new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz",
		// Locale.US);
		DateFormat df = DateFormatter.getDf();

		XMLParser parser = new XMLParser();
		// getting XML
		String xml = parser.getXmlFromUrl(URL);
		// getting DOM element
		Document doc = parser.getDomElement(xml);

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// initialize new Article
			Article article = new Article();
			Element e = (Element) nl.item(i);
			// set fields of article
			article.setDescription(parser.getValue(e, KEY_DESCRIPTION));
			article.setTitle(parser.getValue(e, KEY_TITLE));
			article.setLink(parser.getValue(e, KEY_LINK));

			Date DefaultStartPubDate = null;
			try {
				Date _pubDate = df.parse(parser.getValue(e, KEY_PUBDATE));
				article._setPubDate(_pubDate);

				// create default start date
				final String startDate = "Tue, 01 Jan 2013 01:01:01 GMT";
				DefaultStartPubDate = df.parse(startDate);
			} catch (ParseException e2) {
				// set default startDate as pubDate article
				article._setPubDate(DefaultStartPubDate);
				// e2.printStackTrace();
			}

			// set enclosure
			// enclosure is not always present in the xml
			// if not present => null
			NodeList nll = e.getElementsByTagName(KEY_ENCLOSURE);
			if (nll.getLength() > 0) {
				Element ee = (Element) nll.item(0);
				NamedNodeMap attribute = ee.getAttributes();
				if (attribute.getLength() > 0) {
					String type = attribute.item(2).getNodeValue();
					// control if it is a photo
					if (type.equals("image/jpeg")) {
						String url = attribute.item(0).getNodeValue();
						article.setEnclosure(url);
					}
				}
			}

			// download image to internal storage
			// context given as parameter with method initialize
			// only save the image if there's an enclosure in the article
			if (article.getEnclosure() != null && article.getEnclosure() != "") {
				ImageDao.saveImage(context, article.getEnclosure());
			}

			article.setChannel(channel);

			// test if article is already present in the database
			// control on basis of title and pubDate
			// get list of articles already in database
			// loop over the articles and control if already present
			
			
			if (articlesForChannelPresentInDatabase.size()!= 0) {
				boolean articleIsAlreadyInDb = false;
				for (Article a : articlesForChannelPresentInDatabase) {
					if (a.equals(article)) {
						articleIsAlreadyInDb = true;
					}
				}

				// if this is a new article
				if (articleIsAlreadyInDb == false) {
					// add article to list of articles
					articles.add(article);
				}
			} 
			// if no articles in database for the channel
			else {
				articles.add(article);
			}
		}
		
		//sort articles by date
		//use a custom comparator
		Collections.sort(articles,new ArticleComparator());
	}

	public static void updateDb(List<Article> articles, DatabaseHandler db) {
		// put each article in db
		for (int i = 0; i < articles.size(); i++) {
			db.addArticle(articles.get(i));
		}

		// clear the articles list
		// list of new articles
		articles.clear();
		// list of old articles (already present in database)
		articlesForChannelPresentInDatabase.clear();
	}

	public static List<Article> getArticles() {
		return articles;
	}

	public static void setArticles(List<Article> articles) {
		ArticleDao.articles = articles;
	}

	public static Article getArticleById(int id) {
		return db.getArticle(id);
	}
	
	public static void removeArticle(Article article){
		db.removeArticle(article);
	}
	
	public static List<Article> getArticlesForSearch(String search){
		return db.getArticlesForSearch(search);
	}
	
	public static void removeOldArticlesPerChannelFromDatabase(Channel channel, Context context){

		// set database and list of articles for channel already present in the
		// database
		db = new DatabaseHandler(context);
		
		List<Article> articlesInDb=db.getArticlesForChannel(channel);
		
		SparseArray<Article> sparseArrayArticles=new SparseArray<Article>();
		
		for(int i=0;i<articlesInDb.size();i++){
			sparseArrayArticles.put(i,articlesInDb.get(i));
		}
		
		// date today at midnight 
		// needed to compare the article's date
		Calendar calTodayMid=Calendar.getInstance();
		calTodayMid.set(Calendar.HOUR_OF_DAY, 0);
		calTodayMid.set(Calendar.MINUTE, 0);
		calTodayMid.set(Calendar.SECOND, 0);
		calTodayMid.set(Calendar.MILLISECOND, 0);	
				
		//loop over sparseArrayArticles		
		int key = 0;
		for(int i = 0; i < sparseArrayArticles.size(); i++) {
		   key = sparseArrayArticles.keyAt(i);
		   // get the object by the key.
		   Article a = sparseArrayArticles.get(key);
		   
			//convert article date to calendar object  
			Calendar calArticle=Calendar.getInstance();
			calArticle.setTime(a._getPubDate());
			
			//date of the article has to be earlier than midnight of today
			//and there're need to be at least 15 articles per Channel in database
			if(calTodayMid.after(calArticle)&&(key>16)){				
				db.removeArticle(a);
			}	
		}
	}

public static List<Article> getAllArticles(){
	return db.getAllArticles();
}

}
