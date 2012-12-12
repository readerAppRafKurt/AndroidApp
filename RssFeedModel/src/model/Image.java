package model;

/**
 * Class who specifies a GIF, JPEG or PNG image that can be displayed with the channel, feed
 * Most of the time the logo of the feed, newspaper ...
 * @author Kurt
 *
 */

public class Image {

	/*** PROPERTIES ***/
	
	private String url; //URL of a GIF, JPEG or PNG image that represents the channel
	private String title; //describes the image, it's used in the ALT attribute of the HTML <img> tag
	private String link; //Link to the website of the feed, not the xml-feed
	private String description;
	
	
	
	/*** CONSTRUCTORS ***/
	
	/**
	 * Constructor met 4 parameters
	 * @param url
	 * @param title
	 * @param link
	 * @param description
	 */
	public Image (String url, String title, String link, String description){
		this.url = url;
		this.title = title;
		this.link = link;
		this.description = description;
	}


	/*** GETTERS & SETTERS ***/

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	
}
