package classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

//this class is needed to speed up the showing of the articles
public class DateFormatter {
		
	private static DateFormat df;
	
	public static void setDf() {
		//old
		//Tue Feb 12 18:13:00 GMT+01:00 2013
		//df = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy",
		//		Locale.US);
		
		//new from 13/03
		
		//Wed, 13 Mar 2013 19:28:00 GMT
		df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",
				Locale.US);

	}

	public static DateFormat getDf() {
		return df;
	}
}
