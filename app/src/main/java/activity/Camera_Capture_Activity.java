package activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.schoolapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import database.Database;
import notes.Notes_Main_Fragment;
import tools.Tools;

/**
 * Created by miroslav on 11/01/16.
 */
public class Camera_Capture_Activity extends Activity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_camera);
        background = (ImageView) this.findViewById(R.id.notes_imagebackground);
        thumbnailCaptureIntent();
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
                //fix: notify if the user is out of internal memory .
            }
        }
        Tools.backButton(background);
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
        ContextWrapper cw = new ContextWrapper(this);
        File mediaStorageDir = cw.getDir("", Context.MODE_PRIVATE);
        File mediaFile;
        String mImageName = Tools.getFileNamePhoto();
        Database db = new Database(this);
        db.updatePicture(mImageName, String.valueOf(MainActivity.CURRENT_ID));
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Notes_Main_Fragment.takenPhoto = true;
        return mediaFile;
    }
}
