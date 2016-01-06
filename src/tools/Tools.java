package tools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import activity.MainActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import database.Database;

public class Tools {

	/**
	 * <b>HASH</b></br>
	 * Value to split Database entries and values DateTime() + HASH + Entry()
	 */
	public static final String HASH = " qwerqwer ";

	/**
	 * <b>getFileUriDEBUG(Context c)</b></br>
	 * Returns a <b>PRESET (Debugging)</b> image for populating the image view.
	 * 
	 * @param c
	 * @return the uri for ImageView
	 */
	public static Uri getFileUriDEBUG(Context c) {
		File file = getAllPhotoFiles(c).get(0);
		Uri uri = Uri.fromFile(file);
		return uri;
	}

	/**
	 * <b>getFileUri(File file)</b></br>
	 * Provided the file, this will return the Uri used for populating the
	 * Imageview.
	 * 
	 * @param file
	 * @return
	 */
	public static Uri getFileUri(File file) {
		Uri uri = Uri.fromFile(file);
		return uri;
	}

	/**
	 * <b>getFileUri(String str, Context c)</b></br>
	 * Overided method. Currently <b>not operational</b>.
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static Uri getFileUri(String str, Context c) {
		File file = null;
		try {
			file = File.createTempFile(getContextWrapperDir(c).getAbsolutePath(), str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(file);
		return uri;
	}

	/**
	 * <b>getFileNameAudio()</b></br>
	 * Returns randomly generatated file name specific for <b>audio</b> files.
	 * 
	 * @return
	 */
	public static String getFileNameAudio() {
		return "AUDIO_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".3gp";
	}

	/**
	 * <b>getFileNamePhoto()</b></br>
	 * Returns randomly generatated file name specific for <b>photo</b> files.
	 * 
	 * @return
	 */
	public static String getFileNamePhoto() {
		return "PIC_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
	}

	/**
	 * <b>getFileNameDrawing()</b></br>
	 * Returns randomly generatated file name specific for <b>drawing</b> files.
	 * 
	 * @return
	 */
	public static String getFileNameDrawing() {
		return "DRAW_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
	}

	/**
	 * <b>getFileNameVideo()</b></br>
	 * Returns randomly generatated file name specific for <b>video</b> files.
	 * 
	 * @return
	 */
	public static String getFileNameVideo() {
		return "VID_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".mp4";
	}

	/**
	 * <b>getCurrentID()</b></br>
	 * Returns the current index used for inserting or reading from the databse.
	 * 
	 * @return current database index
	 */
	public static String getCurrentID() {
		return String.valueOf(MainActivity.CURRENT_ID);
	}

	/**
	 * <b>setCurrentID(int id)</b></br>
	 * Used to set the current ID inside the <i>MainActivity.java</i> activity.
	 * 
	 * @param id
	 *            Designated ID
	 */
	public static void setCurrentID(int id) {
		MainActivity.CURRENT_ID = id;
	}

	/**
	 * <b>setCurrentID(String id)</b></br>
	 * Used to set the current ID inside the <i>MainActivity.java</i> activity.
	 * 
	 * @param id
	 *            Designated ID
	 */
	public static void setCurrentID(String id) {
		MainActivity.CURRENT_ID = Integer.valueOf(id);
	}

	/**
	 * <b>getDateString()</b></br>
	 * Is in the format 'MONTH DAY, YEAR, HH:MM:SS AM/PM'
	 * 
	 * @return String representing the current time and date.
	 */
	public static String getDateString() {
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		return currentDateTimeString;
	}

	/**
	 * <b>getContextWrapperDir(Context c)</b></br>
	 * Obtains the root folder of the application.
	 * 
	 * @param c
	 * @return file directory of contextWrapper ""
	 */
	public static File getContextWrapperDir(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		return dir;
	}

	/**
	 * <b>getRandomFileNameAbsolute(Context c) - DEBUGGING</b></br>
	 * Used to return a random absolute filename.
	 * 
	 * @param c
	 * @return
	 */
	public static String getRandomFileNameAbsolute(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File storageDir = cw.getDir("", Context.MODE_PRIVATE);
		String temp = UUID.randomUUID().toString().replaceAll("-", "");
		File[] dir = storageDir.listFiles();
		for (int i = 0; i < dir.length; i++) {
			if (temp.equals(dir[i].getName())) {
				temp = UUID.randomUUID().toString().replaceAll("-", "");
				i = 0;
			}
		}
		return storageDir.getAbsolutePath().toString() + "/" + temp;
	}

	/**
	 * <b>getRandomFileName(Context c) - DEBUGGING</b></br>
	 * Used to return a random local filename.
	 * 
	 * @param c
	 * @return
	 */
	public static String getRandomFileName(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File storageDir = cw.getDir("", Context.MODE_PRIVATE);
		String temp = UUID.randomUUID().toString().replaceAll("-", "");
		File[] dir = storageDir.listFiles();
		for (int i = 0; i < dir.length; i++) {
			if (temp.equals(dir[i].getName())) {
				temp = UUID.randomUUID().toString().replaceAll("-", "");
				i = 0;
			}
		}
		return temp;
	}

	/**
	 * <b>listAllAudio(Context c) - DEBUGGING</b></br>
	 * Used to return a String list of all audio files. Most usefull for viewing
	 * in a Toast message for immmediate results.
	 * 
	 * @param c
	 * @return a formated string for Toast messages
	 */
	public static String listAllAudio(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		String[] temp = dir.list();
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].contains(".3gp")) {
				res.add(temp[i]);
			}
		}
		String result = "AUDIO\n";
		for (int i = 0; i < res.size(); i++) {
			result += res.get(i) + "\n";
		}
		return result;
	}

	/**
	 * <b>listAllPicture(Context c) - DEBUGGING</b></br>
	 * Used to return a String list of all photo/camera files. Most useful for
	 * viewing in a Toast message for immediate results.
	 * 
	 * @param c
	 * @return a formated string for Toast messages
	 */
	public static String listAllPicture(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		String[] temp = dir.list();
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].contains(".jpg")) {
				res.add(temp[i]);
			}
		}
		String result = "PICTURE\n";
		for (int i = 0; i < res.size(); i++) {
			result += res.get(i) + "\n";
		}
		return result;
	}

	/**
	 * <b>getAllAudioFiles(Context c) - DEBUGGING</b></br>
	 * Returns an Arraylist<File> of all of the <b>AUDIO</b> files in the
	 * directory
	 * 
	 * @param c
	 * @return Arraylist<File> of all audio files.
	 */
	public static ArrayList<File> getAllAudioFiles(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		File[] temp = dir.listFiles();
		ArrayList<File> res = new ArrayList<File>();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].toString().contains(".3gp")) {
				res.add(temp[i]);
			}
		}
		return res;
	}

	/**
	 * <b>getAllPhotoFiles(Context c) - DEBUGGING</b></br>
	 * Returns an Arraylist<File> of all of the <b>PHOTO</b> files in the
	 * directory
	 * 
	 * @param c
	 * @return Arraylist<File> of all photos files.
	 */
	public static ArrayList<File> getAllPhotoFiles(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		File[] temp = dir.listFiles();
		ArrayList<File> res = new ArrayList<File>();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].toString().contains(".jpg")) {
				res.add(temp[i]);
			}
		}
		return res;
	}

	/**
	 * <b>deleteDatabase(Context context)</b></br>
	 * This method will truncate the entire database and all of its values.
	 * Currently it will insert a new record to prevent errors
	 * 
	 * @param context
	 *            -Current application context
	 */
	public static void delteDatabase(Context context) {
		Database dbtemp = new Database(context);
		dbtemp.deleteDatabase();
		dbtemp.createRecords(getDateString(), "", "", "", "", "", "", "");
		Toast.makeText(context, "Database has been deleted", Toast.LENGTH_SHORT).show();
	}

	/**
	 * <b>deleteFiles(Context c)</b></br>
	 * This method will delete all files from the app. <i>This does not include
	 * the database values.</i> Only will remove files within the application
	 * main directory.
	 * 
	 * @param context
	 *            -Current application context
	 */
	public static void deleteFiles(Context context) {
		ContextWrapper cw = new ContextWrapper(context);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
		Toast.makeText(context, "File contents have been deleted", Toast.LENGTH_SHORT).show();
	}

	/**
	 * <b>backButton(View v)</b></br>
	 * Simulates a <i><b>Back Button KeyEvent</b></i> occurance. Currently used
	 * for returning to the main view inside of the notes main activity.
	 * 
	 * @param v
	 */
	public static void backButton(View v) {
		v.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
		v.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
	}

}
