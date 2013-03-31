package classes;

import java.util.Date;

public class Channel {
	
	int _id;
	String title;
	String link;
	String description;
	String language;
	Date pubDate;
	boolean selected;
	
	public Channel(){
		
	}
	
	public Channel(int id, String title, String link, String description, String language, Date pubDate,boolean selected){
		this._id=id;
		this.title=title;
		this.link=link;
		this.description=description;
		this.language=language;
		this.pubDate=pubDate;
	}
	
	public Channel(String title, String link, String description, String language, Date pubDate,boolean selected){
		this.title=title;
		this.link=link;
		this.description=description;
		this.language=language;
		this.pubDate=pubDate;
	}
		
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
