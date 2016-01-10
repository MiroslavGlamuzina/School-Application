package activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileHelper {

	File file;

	FileHelper() {

	}

	/**
	 * getSubDirectoryContents(String SubDirectory)
	 * 
	 * @param String
	 *            sub_directory can add optional sub directory to list contents
	 *            of such.
	 * @return returns file with all contents of directory
	 */
	public static void getSubDirectoryContents(String SubDirectory) {
		Log.d("fileTesting()", "passed");
		SubDirectory = "/" + SubDirectory;
		String path = Environment.getExternalStorageDirectory().toString() + "/Android/data/com.example.schoolapp"
				+ SubDirectory;
		Log.d("Files", "Path: " + path);
		File f = new File(path);
		File file[] = f.listFiles();
		Log.d("Files", "Size: " + file.length);
		for (int i = 0; i < file.length; i++) {
			Log.d("Files", "FileName:" + file[i].getName());
		}
	}

	public static ArrayList<String> listDirectory(File dir) {
		ArrayList<String> res = new ArrayList<String>();
		File file[] = dir.listFiles();
		Log.d("Files", "Size: " + file.length);
		for (int i = 0; i < file.length; i++) {
			Log.d("Files", "FileName:" + file[i].getName());
			res.add(file[i].getName());
		}
		return res;
	}

	/**
	 * getParentDirectoryContents()
	 * 
	 * @return returns file with all contents of Master directory
	 */
	public static void getParentDirectoryContents() {
		try {
			String path = Environment.getExternalStorageDirectory().toString() + "/Android/data/com.example.schoolapp";
			Log.d("Files", "Path: " + path);
			File f = new File(path);
			File file[] = f.listFiles();
			Log.d("Files", "Size: " + file.length);
			for (int i = 0; i < file.length; i++) {
				Log.d("Files", "FileName:" + file[i].getName());
			}
		} catch (Exception e) {
			Log.e("FileHelper,getParentDirectoryContents() ", "directory is empty, error has been thrown!");
		}
	}

	public static ArrayList<String> getParentDirectoryContentsResults() {
		ArrayList<String> res = new ArrayList<String>();
		String path = Environment.getExternalStorageDirectory().toString() + "/Android/data/com.example.schoolapp";
		// String path = Environment.getExternalStorageDirectory().toString() +
		// "/Android/data";
		Log.d("Files", "Path: " + path);
		File f = new File(path);
		File file[] = f.listFiles();
		Log.d("Files", "Size: " + file.length);
		for (int i = 0; i < file.length; i++) {
			Log.d("Files", "FileName:" + file[i].getName());
			res.add(file[i].getName());
		}
		return res;
	}

	public static void makeDirectory(String dir) {
		String path = Environment.getExternalStorageDirectory().toString() + "/Android/data/com.example.schoolapp/"
				+ dir;
		File f = new File(path);
		f.mkdirs();
	}

	public static void makeFile(File f) {
		// File dir = ctx.getDir("my_sub_dir", Context.MODE_PRIVATE);
		// File newfile = new File(topDirFile.getAbsolutePath() + File.separator
		// + "new_file_name");
		// newfile.createNewFile();
		// BufferedOutputStream fout = new BufferedOutputStream(new
		// FileOutputStream(newfile), 16 * 1024);
	}

	public static void save(String filename, String write, Context ctx) {
		FileOutputStream fos;
		String path = Environment.getExternalStorageDirectory().toString() + "/Android/data/com.example.schoolapp/";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(write);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeToFile(String dir, String file, String data, Context context) {
		// String path = Environment.getExternalStorageDirectory().toString() +
		// "/Android/data/com.example.schoolapp/";
		// try {
		// OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
		// context.openFileOutput(path+dir+file"config.txt",
		// Context.MODE_PRIVATE));
		// outputStreamWriter.write(data);
		// outputStreamWriter.close();
		// } catch (IOException e) {
		// Log.e("Exception", "File write failed: " + e.toString());
		// }
	}
}
