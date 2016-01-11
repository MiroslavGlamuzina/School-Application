package activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.schoolapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import database.Database;
import tools.Tools;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Camera_Activity extends Activity implements OnClickListener {
    static final int REQUEST_CAMERA = 1;
    public static Button cameraBtn;
    public LinearLayout testing;
    public LinearLayout hscroll;
    public File mTempCameraPhotoFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraBtn = (Button) this.findViewById(R.id.camera);
        hscroll = (LinearLayout) this.findViewById(R.id.hscrollcam);
        testing = (LinearLayout) this.findViewById(R.id.camera_test);
        cameraBtn.setOnClickListener(this);
        testing.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        thumbnailCaptureOnActivityResult(requestCode, resultCode, data);
    }

    //todo something here
    Bitmap bitMap;
    Uri imageUri;

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                thumbnailCaptureIntent();
                hscroll.removeAllViews();
                testing.removeAllViews();
                getPhotos();
                break;
            case R.id.camera_test:
                refreshView();
                getPhotos();
                break;
            case R.id.hscrollcam:
                // getPhotos();
                break;
            default:
                break;
        }
    }

    public Bitmap image;


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
        Toast.makeText(this, "SaveInternal(): " + mediaStorageDir.getAbsolutePath().toString(), Toast.LENGTH_SHORT)
                .show();
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        String mImageName = Tools.getRandomFileName(this);
        Database db = new Database(this);
        db.updatePicture(mImageName, String.valueOf(MainActivity.CURRENT_ID));
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    private void thumbnailCaptureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void SelectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void thumbnailCaptureOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                saveExternal(photo);
                ImageView image = new ImageView(this);
                image.setLayoutParams(new LayoutParams(700, 900));
                image.setScaleType(ScaleType.FIT_XY);
                image.setImageBitmap(photo);
                hscroll.addView(image);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    public void refreshView() {
        ContextWrapper cw = new ContextWrapper(this);
        File storageDir = cw.getDir("", Context.MODE_PRIVATE);
        ArrayList<String> res = FileHelper.listDirectory(storageDir);
        testing.removeAllViews();
        for (int i = 0; i < res.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(res.get(i));
            testing.addView(tv);
        }
    }

    public void getPhotos() {
        ContextWrapper cw = new ContextWrapper(this);
        File storageDir = cw.getDir("", Context.MODE_PRIVATE);
        Toast.makeText(this, "GetPhotos(): " + storageDir.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
        File[] dir = storageDir.listFiles();
        for (int i = 0; i < dir.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(dir[i].getAbsolutePath());
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LayoutParams(700, 900));
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setImageBitmap(bitmap);
            hscroll.addView(iv);
        }
    }
}
