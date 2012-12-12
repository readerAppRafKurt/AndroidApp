package model.newsItems;

import java.util.ArrayList;
import java.util.Date;

import model.channels.RssFeed;

/**
 * Klasse die alle mogelijke elementen bevat van een nieuwsitem uit een RSS-feed 
 * volgens de 2.0 standaard. Alle Elementen zijn optioneel, maar er moet 
 * minstens een titel of een description aanwezig zijn.
 * 
 * @author Kurt
 * 
 * RSS 2.0 Standaard voor een item, artikel
 * Meer info op: http://cyber.law.harvard.edu/rss/rss.html
 *  
 * title 			De titel van het nieuwsitem.  
 * link 			De url naar een online versie van het artikel. 
 * description      De inhoud van het artikel 
 * author 			Email adres van de auteur
 * category 		Lijst van categorieën waartoe het artikel kan behoren 
 * comments 		URL naar een pagina met commentaren op het artikel 
 * enclosure 		Url naar een media-object (image, video, audio) dat bij het artikel hoort  
 * guid 			Unieke identifier voor het artikel, meestal een url. 
 * pubDate 			Publicatie datum van het artikel 
 * source 			de RSS-feed of channel waarbij het artikel hoort  
 *
 */

public class NewsItemFullImpl implements NewsItem{

	
	/*** PROPERTIES ***/
	
	//rss properties
	private String title;
	private String link;
	private String description;
	private String author;
	private ArrayList<String> category;
	private String comment;
	private String enclosure;
	private String guid;
	private Date pubDate;
	private String source;
	
	//Reference naar de channel, feed
	private String feedCategory;
	private RssFeed rssFeed;
	
	
	/*** CONSTRUCTORS ***/
	
	public NewsItemFullImpl(){
		
	}
	
	public NewsItemFullImpl (String title, String link, String description, 
			String author, ArrayList<String> category, String comment, String enclosure,
			String guid, Date pubDate, String source){
		
	}
	
	
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public ArrayList<String> getCategory() {
		return category;
	}
	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
