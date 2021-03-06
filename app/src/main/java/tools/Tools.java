package tools;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import activity.MainActivity;
import database.Database;

public class Tools {

    /**
     * <b>HASH</b></br>
     * Value to split Database entries and values DateTime() + HASH + Entry()
     */
    public static final String HASH = " qwerqwer ";
    public static final String FLAG = " iuoyiouy ";

    public static boolean checkURL(CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                }
            }
        }
        return isURL;
    }

    public static String checkURLTypeWebView(String url) {
        if (url.toString().contains(new String("youtube")) || url.toString().contains(new String("youtu"))) {
            return "https://www.youtube.com/embed/" + url.toString().split("/")[url.toString().split("/").length - 1];
        }
        return url;
    }

    public static boolean checkURLType(String url) {
        if (url.toString().contains(new String("youtube")) || url.toString().contains(new String("youtu")) || url.toString().contains(new String(".jpg")) || url.toString().contains(new String(".png")) || url.toString().contains(new String(".gif"))) {
            return true;
        }
        return false;
    }


    /**
     * <b>memoryAvailable()</b></br>
     *
     * @return the total amount of Mbs (Megabytes) available in the internal data directory
     */
    public static long memoryAvailable(Context c) {
        StatFs stat = new StatFs(getContextWrapperDir(c).getAbsolutePath());
        long bytesAvailable = (long) stat.getFreeBlocks() * (long) stat.getBlockSize();
        long megAvailable = bytesAvailable / 1048576;
        return megAvailable;
    }

    public static void startActivity(Context c, Class mclass) {
        c.startActivity(new Intent(c, mclass));
        ((Activity) c).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * <b> setMargins()</b></br>
     *
     * @return Margins used to display content on the Notes_Main_Fragment and
     * the Lecture_Review activities.
     */
    public static TableRow.LayoutParams setMargins() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 8, 5, 8);
        return params;
    }

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
        return "AUDIO_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "").replaceAll(":", "-") + ".3gp";
    }

    /**
     * <b>getFileNamePhoto()</b></br>
     * Returns randomly generatated file name specific for <b>photo</b> files.
     *
     * @return
     */
    public static String getFileNamePhoto() {
        return "PIC_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "").replaceAll(":", "-") + ".jpg";
    }

    /**
     * <b>getFileNameDrawing()</b></br>
     * Returns randomly generatated file name specific for <b>drawing</b> files.
     *
     * @return
     */
    public static String getFileNameDrawing() {
        return "DRAW_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "").replaceAll(":", "-") + ".jpg";
    }

    /**
     * <b>getFileNameVideo()</b></br>
     * Returns randomly generatated file name specific for <b>video</b> files.
     *
     * @return
     */
    public static String getFileNameVideo() {
        return "VID_" + Tools.getCurrentID() + "_" + UUID.randomUUID().toString().replaceAll("-", "").replaceAll(":", "-") + ".mp4";
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
     * @param id Designated ID
     */
    public static void setCurrentID(int id) {
        MainActivity.CURRENT_ID = id;
    }

    /**
     * <b>setCurrentID(String id)</b></br>
     * Used to set the current ID inside the <i>MainActivity.java</i> activity.
     *
     * @param id Designated ID
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

    public static String getTimeStamp() {
        String currentDateTimeString = new SimpleDateFormat("hh:mm:ss").format(new java.util.Date());
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
     * @param context -Current application context
     */
    public static void deleteDatabase(Context context) {
        Database dbtemp = new Database(context);
        dbtemp.deleteDatabase();
        dbtemp.createRecords(getDateString(), "", "", "", "", "", "", "", "");
        Toast.makeText(context, "Database has been deleted", Toast.LENGTH_SHORT).show(); //temp
    }

    /**
     * <b>deleteFiles(Context c)</b></br>
     * This method will delete all files from the app. <i>This does not include
     * the database values.</i> Only will remove files within the application
     * main directory.
     *
     * @param context -Current application context
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

    public static void pressView(View v, int x, int y) {
        v.dispatchTouchEvent(MotionEvent.obtain(50, 50, MotionEvent.ACTION_DOWN, x, y, 0));
        v.dispatchTouchEvent(MotionEvent.obtain(5, 50, MotionEvent.ACTION_UP, x, y, 0));
    }

    public static void upPressViews(View v, int x, int y) {
        v.dispatchTouchEvent(MotionEvent.obtain(5, 50, MotionEvent.ACTION_UP, x, y, 0));
    }
}
