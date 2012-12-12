package model.newsItems;

import model.channels.RssFeed;

/**
 * Klasse die de verplichte elementen bevat van een nieuwsitem uit een RSS-feed 
 * Er moet minstens een titel OF een description aanwezig zijn.
 * 
 * RSS 2.0 Standaard voor een item, artikel
 * Meer info op: http://cyber.law.harvard.edu/rss/rss.html
 *  
 * title 			De titel van het nieuwsitem.   
 * description      De inhoud van het artikel
 * 
 */

public class NewsItemMinImpl implements NewsItem {

	/*** PROPERTIES ***/
	
	//rss properties
	private String title;
	private String description;
	
	//Reference naar channel, rss-feed
	private String feedCategory;
	private RssFeed rssFeed;
	
	
	/*** CONSTRUCTORS ***/
	
	public NewsItemMinImpl(){}
	
	public NewsItemMinImpl(String title, String description, String feedCategory){
		this.description = description;
		this.title = title;
		this.feedCategory = feedCategory;
	}
	
	
	/*** METHODS ***/
	
	@Override
	public Boolean validateNewsItem() {
		// TODO Auto-generated method stub
		return true;
	}

	
	/*** GETTERS & SETTERS ***/
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFeedCategory() {
		return feedCategory;
	}
	public void setFeed(String feedCategory) {
		this.feedCategory = feedCategory;
	}
	public RssFeed getRssFeed() {
		return rssFeed;
	}
	public void setRssFeed(RssFeed rssFeed) {
		this.rssFeed = rssFeed;
	}

	

}
