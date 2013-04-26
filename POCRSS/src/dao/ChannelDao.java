package dao;

import java.util.List;

import android.content.Context;
import classes.Article;
import classes.Channel;
import services.DatabaseHandler;

public class ChannelDao {
	
	static DatabaseHandler db;
	
	public static void openDb(Context context){
		db=new DatabaseHandler(context);
	}
	
	public static Channel getChannelById(int channelId){
		return db.getChannelById(channelId);
	}
	
	public static List<Article> getArticlesForChannel(Channel channel){		
		return db.getArticlesForChannel(channel);	
	}
	
	public static List<Channel> getAllSelectedChannels(){
		return db.getAllSelectedChannels();
	}
	
	public static void updateChannel(Channel channel){
		db.updateChannel(channel);
	}
	
}
