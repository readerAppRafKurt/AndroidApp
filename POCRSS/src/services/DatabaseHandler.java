package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import classes.Article;
import classes.ArticleComparator;
import classes.Channel;
import classes.ChannelType;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dbArticles";

	// table_article
	private static final String TABLE_ARTICLE = "article";
	// Columns for table_article
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_LINK = "link";
	private static final String KEY_DESC = "description";
	private static final String KEY_PUBDATE = "pubDate";
	private static final String KEY_ENCLOSURE = "enclosure";
	private static final String FOREIGN_KEY_CHANNEL = "id_channel";

	// table_Channel
	private static final String TABLE_CHANNEL = "channel";
	// Columns for table_channel (active)
	private static final String KEY_CHANNEL_ID = "id";
	private static final String KEY_CHANNEL_DESC = "description";
	private static final String KEY_CHANNEL_LINK = "link";
	private static final String KEY_SELECTED = "selected";

	// table font
	private static final String TABLE_FONT = "font";
	// Columns for table_font (active)
	private static final String KEY_FONT_ID = "id";
	private static final String KEY_FONT_SELECTED="selected";
	
	// table_theme
	private static final String TABLE_THEME="theme";
	// Columns for table_theme (active)
	private static final String KEY_THEME_ID = "id";
	private static final String KEY_THEME_SELECTED = "selected";
	
	// table_layout
	private static final String TABLE_LAYOUT="layout";
	// Columns for table_layout (active)
	private static final String KEY_LAYOUT_ID = "id";
	private static final String KEY_LAYOUT_SELECTED = "selected";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// create table article
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// create table for layout
		String createTableLayout = "CREATE TABLE " + TABLE_LAYOUT + "("
				+ KEY_LAYOUT_ID + " TEXT," + KEY_LAYOUT_SELECTED + " INTEGER)";
		db.execSQL(createTableLayout);

		// setup the record at first startup
		this.setLayout(db);

		// create table for different (selected) article channels
		String createTableArticleChannels = "CREATE TABLE " + TABLE_CHANNEL
				+ "(" + KEY_CHANNEL_ID + " INTEGER," + KEY_CHANNEL_DESC
				+ " TEXT," + KEY_CHANNEL_LINK + " TEXT," + KEY_SELECTED
				+ " INTEGER)";
		db.execSQL(createTableArticleChannels);

		// all the different Channels are checked the at first startup
		this.setChannels(db);
		
		// create table for different (selected) themes
		String createTableThemes = "CREATE TABLE " + TABLE_THEME
				+ "(" + KEY_THEME_ID + " TEXT," + KEY_THEME_SELECTED
				+ " INTEGER)";
		db.execSQL(createTableThemes);

		// the default theme is selected at startup
		this.setThemes(db);

		// create table for articles
		String createTableArticle = "CREATE TABLE " + TABLE_ARTICLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_LINK + " TEXT," + KEY_DESC + " TEXT," + KEY_PUBDATE
				+ " TEXT," + KEY_ENCLOSURE + " TEXT," + FOREIGN_KEY_CHANNEL
				+ " INTEGER)";
		db.execSQL(createTableArticle);

		// create table for fonts
		String createTableFont = "CREATE TABLE " + TABLE_FONT + "("
				+ KEY_FONT_ID + " TEXT," + KEY_FONT_SELECTED + " INTEGER)";
		db.execSQL(createTableFont);

		// setup the record at first startup
		this.setFonts(db);
			
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAYOUT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNEL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FONT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_THEME);

		// Create tables again
		onCreate(db);
	}

	// create new record article
	public void addArticle(Article article) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, article.getTitle());
		values.put(KEY_LINK, article.getLink());
		values.put(KEY_DESC, article.getDescription());
		values.put(KEY_ENCLOSURE, article.getEnclosure());
		values.put(KEY_PUBDATE, article.getPubDate());
		values.put(FOREIGN_KEY_CHANNEL, article.getChannel().get_id());

		db.insert(TABLE_ARTICLE, null, values);
		db.close();
	}

	// get a record by id
	public Article getArticle(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ARTICLE, new String[] { KEY_ID,
				KEY_TITLE, KEY_LINK, KEY_DESC, KEY_PUBDATE, KEY_ENCLOSURE,
				FOREIGN_KEY_CHANNEL }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		Article article = new Article(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), cursor.getString(4),
					cursor.getString(5), null);
		
		//set channel
		int channelId=Integer.parseInt(cursor.getString(6));
		Channel channel=this.getChannelById(channelId);	
		article.setChannel(channel);
			
		return article;

	}

	// get a record by article title/description
	public Article getArticle(Article article) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + TABLE_ARTICLE
				+ " where " + KEY_TITLE + " = ? AND " + KEY_DESC + " = ?",
				new String[] { article.getTitle(), article.getDescription() });
		if (cursor != null)
			cursor.moveToFirst();

		Article _article = null;

			_article = new Article(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), cursor.getString(4), 
					cursor.getString(5), null);
					
					
					/*this.getChannelById(Integer
							.parseInt(cursor.getString(6))));*/

		return _article;
	}

	// get all articles in db
	public List<Article> getAllArticles() {
		List<Article> articles = new ArrayList<Article>();

		String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Article article = new Article();
				article.set_id(Integer.parseInt(cursor.getString(0)));
				article.setTitle(cursor.getString((1)));
				article.setLink(cursor.getString((2)));
				article.setDescription(cursor.getString((3)));
				article.setPubDate(cursor.getString(4));
				article.setEnclosure(cursor.getString(5));
				//article.setChannel(this.getChannelById(Integer.parseInt(cursor
				//		.getString(6))));

				articles.add(article);
			} while (cursor.moveToNext());
		}

		return articles;
	}

	// get articles for search articles in screen
	public List<Article> getArticlesForSearch(String userInput) {

		List<Article> articles = new ArrayList<Article>();

		SQLiteDatabase db = this.getWritableDatabase();
		// kan dit met named parameters?
		Cursor cursor = db.query(true, TABLE_ARTICLE, new String[] { KEY_ID,
				KEY_TITLE, KEY_LINK, KEY_DESC, KEY_PUBDATE, KEY_ENCLOSURE,
				FOREIGN_KEY_CHANNEL }, KEY_TITLE + " LIKE '%" + userInput
				+ "%'", null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				//set article
				Article article = new Article();
				article.set_id(Integer.parseInt(cursor.getString(0)));
				article.setTitle(cursor.getString((1)));
				article.setLink(cursor.getString((2)));
				article.setDescription(cursor.getString((3)));
				article.setPubDate(cursor.getString(4));
				article.setEnclosure(cursor.getString(5));
				
				//set channel
				Channel channel=new Channel();
				channel.set_id(Integer.parseInt(cursor.getString(6)));					
				article.setChannel(channel);

				//add article to list
				articles.add(article);

			} while (cursor.moveToNext());

			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}

		}

		return articles;
		
	}

	// remove article from db
	public void removeArticle(Article article) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ARTICLE, KEY_ID + " = ?",
				new String[] { String.valueOf(article.get_id()) });
		db.close();
	}

	public boolean articleAlreadyExistInDb(Article article) {

		if (article.equals(this.getArticle(article)))
			return true;
		else
			return false;
	}

	public boolean isDbEmpty() {

		if (this.getAllArticles().size() == 0)
			return true;
		else
			return false;
	}

	// get all channels in db
	public List<Channel> getAllChannels() {
		List<Channel> channels = new ArrayList<Channel>();

		String selectQuery = "SELECT  * FROM " + TABLE_CHANNEL;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Channel channel = new Channel();
				channel.set_id(Integer.parseInt(cursor.getString(0)));
				channel.setDescription(cursor.getString((1)));
				channel.setLink(cursor.getString(2));
				channel.setSelected(cursor.getInt(3) == 1);

				channels.add(channel);
			} while (cursor.moveToNext());
		}

		return channels;
	}

	// get all Selected channels in db
	public List<Channel> getAllSelectedChannels() {
		List<Channel> channels = new ArrayList<Channel>();

		String selectQuery = "SELECT  * FROM " + TABLE_CHANNEL
				+ " WHERE selected = 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Channel channel = new Channel();
				channel.set_id(Integer.parseInt(cursor.getString(0)));
				channel.setDescription(cursor.getString((1)));
				channel.setLink(cursor.getString(2));
				channel.setSelected(cursor.getInt(3) == 1);

				channels.add(channel);
			} while (cursor.moveToNext());
		}

		return channels;
	}

	// insert a channel in db
	// used at the first startup of application
	// all channels will be selected
	public void setChannels(SQLiteDatabase db) {
		// SQLiteDatabase db = this.getWritableDatabase();

		for (ChannelType tt : ChannelType.values()) {
			// put channel in database

			ContentValues values = new ContentValues();
			values.put(KEY_CHANNEL_ID, tt.getId());
			values.put(KEY_CHANNEL_DESC, tt.getType());
			values.put(KEY_CHANNEL_LINK, tt.getUrl());
			values.put(KEY_SELECTED, 1);

			db.insert(TABLE_CHANNEL, null, values);
			// db.close();

		}
	}

	// get channel by id
	public Channel getChannelById(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CHANNEL, new String[] { KEY_CHANNEL_ID,
				KEY_CHANNEL_DESC, KEY_CHANNEL_LINK, KEY_SELECTED }, KEY_CHANNEL_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		Channel channel = new Channel();
		channel.set_id(Integer.parseInt(cursor.getString(0)));
		channel.setDescription(cursor.getString(1));
		channel.setLink(cursor.getString(2));
		channel.setSelected(cursor.getInt(3) == 1);

		return channel;	
	}

	// update channel
	public void updateChannel(Channel channel) {

		ContentValues values = new ContentValues();
		values.put(KEY_CHANNEL_ID, channel.get_id());
		values.put(KEY_CHANNEL_DESC, channel.getDescription());
		values.put(KEY_CHANNEL_LINK, channel.getLink());
		values.put(KEY_SELECTED, channel.isSelected());

		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE_CHANNEL, values,
				KEY_CHANNEL_ID + "=" + channel.get_id(), null);

	}

	// get all articles for a specific channel
	public List<Article> getArticlesForChannel(Channel channel) {

		List<Article> articles = new ArrayList<Article>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + TABLE_ARTICLE		
				+ " where " + FOREIGN_KEY_CHANNEL + " = ?" + " order by " + KEY_ID +" desc",
				new String[] { Integer.toString(channel.get_id()) });

		if (cursor.moveToFirst()) {
			do {
				Article _article = new Article(
							Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor
									.getString(4), cursor.getString(5),channel);
				articles.add(_article);
			} while (cursor.moveToNext());
		}

		return articles;
		
	}
	
	public int getTotalNumberOfArticlesInChannel(int channel_id){
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_ARTICLE
				+ " where " + FOREIGN_KEY_CHANNEL + " = ?",
				new String[] { Integer.toString(channel_id) });
		
		return cursor.getCount();	
	}
	
	public int getLowestIdArticleInChannel(int channel_id){
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select MIN("+KEY_ID+")" +
				" from " + TABLE_ARTICLE
				+ " where " + FOREIGN_KEY_CHANNEL + " = ?",
				new String[] { Integer.toString(channel_id) });
		

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	public int getHighestIdArticleInChannel(int channel_id){
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select MAX("+KEY_ID+")" +
				" from " + TABLE_ARTICLE
				+ " where " + FOREIGN_KEY_CHANNEL + " = ?",
				new String[] { Integer.toString(channel_id) });
		

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	

	// insert a font in db
	// used at the first startup of application
	public void setFonts(SQLiteDatabase db) {

		//light small
		ContentValues values1 = new ContentValues();
		values1.put(KEY_FONT_ID, "light small");
		values1.put(KEY_FONT_SELECTED,false);
		db.insert(TABLE_FONT, null, values1);
		
		//dark small
		ContentValues values2 = new ContentValues();
		values2.put(KEY_FONT_ID, "dark small");
		values2.put(KEY_FONT_SELECTED,true);
		db.insert(TABLE_FONT, null, values2);

		//light medium
		ContentValues values3 = new ContentValues();
		values3.put(KEY_FONT_ID, "light medium");
		values3.put(KEY_FONT_SELECTED,false);
		db.insert(TABLE_FONT, null, values3);
		
		//dark medium
		ContentValues values4 = new ContentValues();
		values4.put(KEY_FONT_ID, "dark medium");
		values4.put(KEY_FONT_SELECTED,false);
		db.insert(TABLE_FONT, null, values4);
		
		//light large
		ContentValues values5 = new ContentValues();
		values5.put(KEY_FONT_ID, "light large");
		values5.put(KEY_FONT_SELECTED,false);
		db.insert(TABLE_FONT, null, values5);
		
		//dark large
		ContentValues values6 = new ContentValues();
		values6.put(KEY_FONT_ID, "dark large");
		values6.put(KEY_FONT_SELECTED,false);
		db.insert(TABLE_FONT, null, values6);
	}

	// get active font
	public String getActiveFont() {
		
		String selectQuery = "SELECT "+KEY_FONT_ID+" FROM " + TABLE_FONT
				+ " WHERE selected = 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToNext();
		
		return cursor.getString(0);	
	}

	// update font
	public void updateFont(String newFontId) {

		//deactivate old active font
		String activeFont=getActiveFont();
		
		ContentValues values = new ContentValues();
		values.put(KEY_FONT_ID,activeFont);
		values.put(KEY_FONT_SELECTED, false);
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE_FONT, values,
				KEY_FONT_ID + "='" + activeFont+"'", null);	
		
		//activate new font
		ContentValues values1 = new ContentValues();
		values1.put(KEY_FONT_ID,newFontId);
		values1.put(KEY_FONT_SELECTED, true);
		db.update(TABLE_FONT, values1,
				KEY_FONT_ID + "='" + newFontId+"'", null);	

	}
	

	// insert a theme in db
	// used at the first startup of application
	public void setThemes(SQLiteDatabase db) {

		// put Themes in database
		// gray has to be selected, otherwise at first startup the colors don't fit androids default
		ContentValues values1 = new ContentValues();
		values1.put(KEY_THEME_ID, "Grijs");
		values1.put(KEY_THEME_SELECTED,true);
		db.insert(TABLE_THEME, null, values1);
		
		ContentValues values2 = new ContentValues();
		values2.put(KEY_THEME_ID, "Blauw");
		values2.put(KEY_THEME_SELECTED,false);
		db.insert(TABLE_THEME, null, values2);
		
		ContentValues values3= new ContentValues();
		values3.put(KEY_THEME_ID, "Rood");
		values3.put(KEY_THEME_SELECTED,false);
		db.insert(TABLE_THEME, null, values3);
	}
	
	//get the active theme
	public String getActiveTheme(){

		String selectQuery = "SELECT "+KEY_THEME_ID+" FROM " + TABLE_THEME
				+ " WHERE selected = 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToNext();
		
		return cursor.getString(0);	
	}
	
	public void updateTheme(String newThemeId){
		//deactivate old active theme
		String activeTheme=getActiveTheme();
		
		ContentValues values = new ContentValues();
		values.put(KEY_THEME_ID,activeTheme);
		values.put(KEY_SELECTED, false);
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE_THEME, values,
				KEY_THEME_ID + "='" + activeTheme+"'", null);	
		
		//activate new theme
		ContentValues values1 = new ContentValues();
		values1.put(KEY_THEME_ID,newThemeId);
		values1.put(KEY_SELECTED, true);
		db.update(TABLE_THEME, values1,
				KEY_THEME_ID + "='" + newThemeId+"'", null);	
	}	
	
	// insert a layout in db
	// used at the first startup of application
	public void setLayout(SQLiteDatabase db) {
		
		//Phone 1
		ContentValues values1 = new ContentValues();
		values1.put(KEY_LAYOUT_ID, "Phone 1");
		values1.put(KEY_LAYOUT_SELECTED,true);
		db.insert(TABLE_LAYOUT, null, values1);
		
		//Phone 2
		ContentValues values2 = new ContentValues();
		values2.put(KEY_LAYOUT_ID, "Phone 2");
		values2.put(KEY_LAYOUT_SELECTED,false);
		db.insert(TABLE_LAYOUT, null, values2);
		
		//Phone 3
		ContentValues values3 = new ContentValues();
		values3.put(KEY_LAYOUT_ID, "Phone 3");
		values3.put(KEY_LAYOUT_SELECTED,false);
		db.insert(TABLE_LAYOUT, null, values3);

		//Tablet 1
		ContentValues values4 = new ContentValues();
		values4.put(KEY_LAYOUT_ID, "Tablet 1");
		values4.put(KEY_LAYOUT_SELECTED,false);
		db.insert(TABLE_LAYOUT, null, values4);
		
		//Tablet 2
		ContentValues values5 = new ContentValues();
		values5.put(KEY_LAYOUT_ID, "Tablet 2");
		values5.put(KEY_LAYOUT_SELECTED,false);
		db.insert(TABLE_LAYOUT, null, values5);

	}
	
	// get active layout
	public String getActiveLayout() {
		
		String selectQuery = "SELECT "+KEY_LAYOUT_ID+" FROM " + TABLE_LAYOUT
				+ " WHERE selected = 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToNext();
		
		return cursor.getString(0);	
	}

	// update layout
	public void updateLayout(String newLayoutId) {

		//deactivate old active layout
		String activeLayout=getActiveLayout();
		
		ContentValues values = new ContentValues();
		values.put(KEY_LAYOUT_ID,activeLayout);
		values.put(KEY_LAYOUT_SELECTED, false);
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE_LAYOUT, values,
				KEY_LAYOUT_ID + "='" + activeLayout+"'", null);	
		
		//activate new layout
		ContentValues values1 = new ContentValues();
		values1.put(KEY_LAYOUT_ID,newLayoutId);
		values1.put(KEY_LAYOUT_SELECTED, true);
		db.update(TABLE_LAYOUT, values1,
				KEY_LAYOUT_ID + "='" + newLayoutId+"'", null);	
	}
}
