package tools;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import activity.MainActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import database.Database;

public class Tools {

	public static final String HASH = " qwerqwer ";

	public static String getFileNameAudio() {
		return "AUDIO_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".3gp";
	}

	public static String getFileNamePhoto() {
		return "PIC_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
	}

	public static String getFileNameDrawing() {
		return "DRAW_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
	}

	public static String getFileNameVideo() {
		return "VID_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".mp4";
	}

	public static String getCurrentID() {
		return String.valueOf(MainActivity.CURRENT_ID);
	}

	public static void setCurrentID(int id) {
		MainActivity.CURRENT_ID = id;
	}

	public static void setCurrentID(String id) {
		MainActivity.CURRENT_ID = Integer.valueOf(id);
	}

	public static String getDateString() {
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		return currentDateTimeString;
	}

	public static File getContextWrapperDir(Context c) {
		ContextWrapper cw = new ContextWrapper(c);
		File dir = cw.getDir("", Context.MODE_PRIVATE);
		return dir;
	}

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
	 * deleteDatabase(Context context) This method will truncate the entire
	 * database and all of its values. Currently it will insert a new record to
	 * prevent errors
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
	 * deleteDatabase(Context c) This method will truncate the entire database
	 * and all of its values. Currently it will insert a new record to prevent
	 * errors
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

	public static void backButton(View v) {
		v.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
		v.getRootView().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
	}

	
}
