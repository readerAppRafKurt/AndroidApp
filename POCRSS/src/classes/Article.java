package classes;

import java.text.DateFormat;
import java.util.Date;

import android.net.ParseException;

public class Article {

	int _id;
	String title;
	String link;
	String description;
	Date pubDate;
	String enclosure;
	Channel channel;

	public Article() {

	}

	public Article(int id, String title, String link, String description,
			String pubDate, String enclosure, Channel channel) {
		this._id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		//needed to use the setPubDate for the formatter
		this.setPubDate(pubDate);
		this.enclosure = enclosure;
		this.channel = channel;
	}

	public Article(String title, String link, String description, String pubDate,
			String enclosure, Channel channel) {
		this.title = title;
		this.link = link;
		this.description = description;
		//needed to use the setPubDate for the formatter
		this.setPubDate(pubDate);
		this.enclosure = enclosure;
		this.channel = channel;
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

	public String getPubDate() {

		Date d = this.pubDate;
		//DateFormat dateFormat = new SimpleDateFormat(
		//		"EEE MMM d HH:mm:ss z yyyy", Locale.US);
		DateFormat dateFormat=DateFormatter.getDf();

		String date = dateFormat.format(d);
		return date;
		// return pubDate;
	}

	public void setPubDate(String pubDate) {
		
		//Tue Feb 12 18:13:00 GMT+01:00 2013
		//DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy",
		//		Locale.US);
		DateFormat df=DateFormatter.getDf();
		Date defaultStartPubDate = new Date();
		try {
			Date _pubDate = (Date) df.parse(pubDate);
			// create default start date
			final String startDate = "Tue, 01 Jan 2013 00:00:00 GMT";
			defaultStartPubDate = (Date) df.parse(startDate);
			this.pubDate = _pubDate;
		} catch (ParseException e2) {
			this.pubDate = defaultStartPubDate;
		} catch (java.text.ParseException e) {
			this.pubDate = defaultStartPubDate;
		}
	}

	public Date _getPubDate() {
		return this.pubDate;
	}

	public void _setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	


}
