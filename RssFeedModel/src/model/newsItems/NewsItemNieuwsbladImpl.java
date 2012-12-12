package model.newsItems;

import java.util.Date;

import annotations.Rss;

import model.channels.RssFeed;

/**
 * Klasse die beantwoordt aan het Rss-feed model van het Nieuwsblad
 * 
 * @author Kurt
 * 
 * RSS 2.0 Standaard voor een item, artikel
 * Meer info op: http://cyber.law.harvard.edu/rss/rss.html
 *  
 * Het nieuwsblad gebruikt onderstaande elementen
 * 
 * title 			De titel van het nieuwsitem.  
 * link 			De url naar een online versie van het artikel. 
 * description      De inhoud van het artikel 
 * enclosure 		Url naar een media-object (image, video, audio) dat bij het artikel hoort  
 * guid 			Unieke identifier voor het artikel, meestal een url. 
 * pubDate 			Publicatie datum van het artikel 
 *
 */

public class NewsItemNieuwsbladImpl implements NewsItem{

	/*** PROPERTIES ***/
	
	//Rss properties
	@Rss
	private String title;
	@Rss
	private String link;
	@Rss
	private String description;
	@Rss
	private String enclosure;
	@Rss
	private String guid;
	@Rss
	private Date pubDate;
	
	//Reference naar channel, feed
	private String feedCategory;
	private RssFeed rssFeed;
	
	
	/*** CONSTRUCTORS ***/
	
	
	/*** METHODS ***/
	
	@Override
	public Boolean validateNewsItem() {
		return true;
	}

	/*** GETTERS & SETTERS ***/
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getFeedCategory() {
		return feedCategory;
	}
	public void setFeedCategory(String feedCategory) {
		this.feedCategory = feedCategory;
	}
	public RssFeed getRssFeed() {
		return rssFeed;
	}
	public void setRssFeed(RssFeed rssFeed) {
		this.rssFeed = rssFeed;
	}


}
