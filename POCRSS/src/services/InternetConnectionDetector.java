package services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectionDetector {
	
    private Context context;
    
    public InternetConnectionDetector(Context context){
        this.context = context;
    }
 
    public boolean isConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (cm != null)
          {
        	 NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        	 if(networkInfo==null){
        		return false;
        	 }
        	 else{
        		 return true;
        	 }
          }
          return false;
    }

}