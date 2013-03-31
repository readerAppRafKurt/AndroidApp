package services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

public class ImageDao {
	
	private static String fileName;

	public static void saveImage(Context context, String linkImage) {
		try {
			//TODO control if file already exists (? do this in in exception handler)
			//already controlled for the article
			
			//create fileName
			fileName=getFileName(linkImage);
			
			URL urlImage = new URL(linkImage);
			// open stream
			FileOutputStream out = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			Bitmap bmp = BitmapFactory.decodeStream(urlImage.openConnection()
					.getInputStream());
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			// close stream
			out.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Bitmap getImage(Context context, String linkImage) {

		Bitmap bitmap = null;
		try {
			//create fileName
			fileName=getFileName(linkImage);
			
			FileInputStream in = context.openFileInput(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			bitmap = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			bitmap = null;
		}

		return bitmap;
	}

	public static void removeImage(Context context, String fileName) {
		
		//control is image exists
		context.deleteFile(fileName);
	}

	public static boolean availableSpaceStorage() {

		StatFs statFs = new StatFs(Environment.getRootDirectory()
				.getAbsolutePath());
		long blockSize = statFs.getBlockSize();
		long availableSize = statFs.getAvailableBlocks() * blockSize;

		if (availableSize > 400000) {
			return true;
		} else {
			return false;
		}

	}
	
	private static String getFileName(String urlImage){

		//get last part of the imageUrl, after the last / to create the fileName
		return urlImage.substring(urlImage.lastIndexOf("/")+1);
	}

}