package notes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.schoolapp.R;

import activity.MainActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import database.Database;
import tools.Tools;

public class Notes_Camera_Fragment extends Fragment {
	private final String TAG = "Notes_Camera_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;

	// CAMERA
	public static final int RESULT_CANCELED = 0;
	public static final int RESULT_OK = -1;
	public static final int REQUEST_CAMERA = 1;
	private ImageView background;
	public static boolean isComplete = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notes_camera, container, false);
		background = (ImageView) rootView.findViewById(R.id.notes_imagebackground);
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		thumbnailCaptureOnActivityResult(requestCode, resultCode, data);
	}

	private void thumbnailCaptureIntent() {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, REQUEST_CAMERA);
	}

	private void thumbnailCaptureOnActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CAMERA) {
			if (resultCode == RESULT_OK) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				saveExternal(photo);
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}
		Tools.backButton(background);
	}

	private void saveExternal(Bitmap image) {
		File pictureFile = saveInternal();
		if (pictureFile == null) {
			Log.d("SaveImage(); ", "Error creating media file, check storage permissions: ");// e.getMessage());
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			image.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d("SaveImage(); ", "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d("SaveImage(); ", "Error accessing file: " + e.getMessage());
		}
	}

	public File saveInternal() {
		ContextWrapper cw = new ContextWrapper(getContext());
		File mediaStorageDir = cw.getDir("", Context.MODE_PRIVATE);
		File mediaFile;
		String mImageName = Tools.getFileNamePhoto();
		Database db = new Database(getContext());
		db.updatePicture(mImageName, String.valueOf(MainActivity.CURRENT_ID));
		mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
		Notes_Main_Fragment.takenPhoto = true;
		return mediaFile;
	}

	@Override
	public void onStart() {
		super.onStart();
		isStarted = true;
		if (isVisible && isStarted) {
			isVisible();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		Notes_Main_Fragment.isVisible = false;
		Notes_Main_Fragment.isStarted = false;
		Notes_Drawing_Fragment.isVisible = false;
		Notes_Drawing_Fragment.isStarted = false;
		isVisible = isVisibleToUser;
		if (isStarted && isVisible) {
			isActive();
		}
		if (isVisible) {
			thumbnailCaptureIntent();
		}
	}

	public void isActive() {
		Log.d(TAG, "shown!");
	}
}