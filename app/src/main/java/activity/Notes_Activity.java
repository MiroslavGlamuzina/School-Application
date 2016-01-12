package activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import tools.Entry;
import tools.Tools;

/**
 * Created by miroslav on 11/01/16.
 */


//// TODO: 11/01/16 dynamic scroll .. meaning. only hold a set number of items from the list in memory rest let the garbage collector take care off.
public class Notes_Activity extends Activity implements View.OnClickListener {
    private final String TAG = "Notes_Activity";
    public static boolean isCamera = true;
    public static boolean isRecording = true;
    public static boolean takenPhoto = false;
    public static boolean savedDrawing = false;

    TextView lecture_title;
    ImageButton camera_btn, video_btn, audio_btn, flag_btn, draw_btn;
    Button submit_btn;
    EditText note_et;
    ScrollView scroll;
    LinearLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);
        camera_btn = (ImageButton) this.findViewById(R.id.notes_camera);
        audio_btn = (ImageButton) this.findViewById(R.id.notes_audiotoggle);
        submit_btn = (Button) this.findViewById(R.id.notes_submit);
        flag_btn = (ImageButton) this.findViewById(R.id.notes_flag);
        video_btn = (ImageButton) this.findViewById(R.id.notes_video);
        draw_btn = (ImageButton) this.findViewById(R.id.notes_draw);
        body = (LinearLayout) this.findViewById(R.id.notes_body);
        note_et = (EditText) this.findViewById(R.id.notes_et);
        scroll = (ScrollView) this.findViewById(R.id.notes_scrollview);
        lecture_title = (TextView) this.findViewById(R.id.notes_lecturetitle);

        Database db = new Database(this);
//		db.updateTitle("Biology Lecture 1", Tools.getCurrentID());
        lecture_title.setText(db.getTitle(Tools.getCurrentID()));

        draw_btn.setOnClickListener(this);
        video_btn.setOnClickListener(this);
        flag_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        audio_btn.setOnClickListener(this);
        camera_btn.setOnClickListener(this);

        // The following block of code will grab the sorted arraylist from the
        // database to repopulated the page after close..!!!
        // DEBUGGING();
        populateBody();
    }

    private void DEBUGGING() {
        Database db = new Database(this);
        ArrayList<Entry> temp = Entry.sortEntries(db.getAllFromID("1"));
        String res = "";
        for (int i = 0; i < temp.size(); i++) {
            res += temp.get(i).getDate() + ", " + temp.get(i).getVal() + ", " + temp.get(i).getType() + "\n";
        }
        addTextElement(res);
    }

    public void populateBody() {
        body.removeAllViews();
        addAudioElement();// debugging
        runOnUiThread(new Runnable() {
                          public void run() {
                              Database db = new Database(Notes_Activity.this);
                              ArrayList<Entry> list = Entry.sortEntries(db.getAllFromID("1"));
                              String note_temp = "";
                              addTextElement("testing!!");
                              for (int i = 0; i < list.size(); i++) {

                                  if (list.get(i).getType().equals(new String(Entry.PICTURE))) {
                                      addPhotoElement(
                                              Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
                                  } else if (list.get(i).getType().equals(new String(Entry.DRAWING))) {
                                      addPhotoElement(
                                              Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
                                  } else if (list.get(i).getType().equals(new String(Entry.FLAG))) {
                                      addFlagElement();
                                  } else if (list.get(i).getType().equals(new String(Entry.NOTE))) {
                                      addTextElement(list.get(i).getVal());
                                  }
                              }
                              //// TODO alt
//                    if (list.get(i).getType().equals(new String(Entry.NOTE))) {
//                        note_temp += list.get(i).getVal() + "\n";
//                    } else if (list.get(i).getType().equals(new String(Entry.PICTURE))) {
//                        if (!note_temp.equals(new String(""))) {
//                            addTextElement(note_temp);
//                            note_temp = "";
//                        }
//                        addPhotoElement(
//                                Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
//                    } else if (list.get(i).getType().equals(new String(Entry.DRAWING))) {
//                        if (!note_temp.equals(new String(""))) {
//                            addTextElement(note_temp);
//                            note_temp = "";
//                        }
//                        addPhotoElement(
//                                Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
//                    } else if (list.get(i).getType().equals(new String(Entry.FLAG))) {
//                        if (!note_temp.equals(new String(""))) {
//                            addTextElement(note_temp);
//                            note_temp = "";
//                        }
//                        addFlagElement();
//                    } else if (!note_temp.equals(new String(""))) {
//                        addTextElement(note_temp.substring(0, note_temp.length() - 2));
//                        note_temp = "";
//                    }
//                  addPhotoElement(Uri.parse(list.get(i).getVal()));
//                try {
//                    wait(50);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
                          }
                      }

        );
    }

    @SuppressLint("NewApi")
    public void addTextElement(String val) {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);
        TableRow.LayoutParams item_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.LEFT;
        item.setLayoutParams(item_params);

        TextView tv = new TextView(this);
        tv.setText(val);
        item.addView(tv);
        body.addView(item);

        scrollDown();
    }

    @SuppressLint("NewApi")
    public void addPhotoElement(Uri imgUri) {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setBackgroundResource(R.drawable.shape);
        TableRow.LayoutParams item_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.RIGHT;
        item_params.setMargins(5, 8, 5, 8);
        item.setLayoutParams(item_params);
        item.setPadding(-20, 40, -20, 40);
//        item.setPadding(-30, 20, -30, 20);
        ImageView iv = new ImageView(this);
        TableRow.LayoutParams iv_params = new TableRow.LayoutParams(500, 500);
        iv.setLayoutParams(iv_params);
        iv.setImageURI(imgUri);
        item.addView(iv);
        body.addView(item);
    }

    public void addDrawElement() {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);

    }

    public void addVideoElement() {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);

    }

    public void addAudioElement() {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);
        item.setWeightSum(10f);

        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.mic);
        TableRow.LayoutParams ivparams = new TableRow.LayoutParams(75, 75);
        ivparams.setMargins(25, 25, 55, 25);
        iv.setLayoutParams(ivparams);

        TextView tv = new TextView(this);
        TableRow.LayoutParams tvparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvparams.gravity = Gravity.CENTER;
        tv.setLayoutParams(tvparams);
        tv.setText("Class is in session . . . ");

        item.addView(iv);
        item.addView(tv);
        body.addView(item);
    }

    public void addFlagElement() {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);
        item.setWeightSum(10f);

        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.flag);
        TableRow.LayoutParams ivparams = new TableRow.LayoutParams(75, 75);
        ivparams.setMargins(25, 25, 55, 25);
        iv.setLayoutParams(ivparams);

        TextView tv = new TextView(this);
        TableRow.LayoutParams tvparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvparams.gravity = Gravity.CENTER;
        tv.setLayoutParams(tvparams);
        tv.setText("Flag is set here!!!");

        item.addView(iv);
        item.addView(tv);
        body.addView(item);
    }

    //// TODO: 11/01/16 think of a better way to incorporate values.. add values .. updt the submit button to show dynamically , maybe create a view for it.
    public void insertNote() {
        TextView tv = new TextView(this);
        String edittext = "";
        edittext = note_et.getText().toString();
        edittext = "temp";
        Database db = new Database(this);
        db.updateNote(edittext, "1");
        Toast.makeText(this,
                "Description Size(): " + db.sizeNote(String.valueOf(MainActivity.CURRENT_ID)),
                Toast.LENGTH_SHORT).show();
        addTextElement(edittext);
    }

    public void insertFlag() {
        TextView tv = new TextView(this);
        Database db = new Database(this);
        db.updateFlag(Tools.FLAG, "1");
        Toast.makeText(this,
                "Description Size(): " + db.sizeFlag(String.valueOf(MainActivity.CURRENT_ID)),
                Toast.LENGTH_SHORT).show();
        //todo reset this with add flagelement.. which will be esentially the same as the addaudio begin element..
        addFlagElement();
        scrollDown();
    }

    public void scrollDown() {
        scroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        }, 250);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notes_audiotoggle:
                if (isRecording) {
                    isRecording = false;
                } else if (!isRecording) {
                    isRecording = true;
                }
                break;
            case R.id.notes_submit:
                insertNote();
                break;
            case R.id.notes_flag:
                insertFlag();
                break;
            case R.id.notes_draw:
                Tools.startActivity(this, Drawing_Activity.class); //// completed: 11/01/16
                break;
            case R.id.notes_camera:
//                Tools.startActivity(this, Camera_Capture_Activity.class); // completed: 11/01/16 -- REMOVED THE CAMERA ACTIVITY
                thumbnailCaptureIntent();
                break;
            case R.id.notes_video:
//                    Tools.startActivity(this, ); // FIXME: 11/01/16
                break;
            default:
                break;
        }
    }

    // CAMERA CAPTURE INTENT / CODE / RESOURCES
    public static final int REQUEST_CAMERA = 1;

    private void thumbnailCaptureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        thumbnailCaptureOnActivityResult(requestCode, resultCode, data);
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
                populateBody();//// FIXME: 11/01/16 -> do a neater adding the layout... consumes too much RAM
            }
        }
    }

    private void saveExternal(Bitmap image) {
        File pictureFile = saveInternal();
        if (pictureFile == null) {
            Log.d("SaveImage(); ", "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            addPhotoElement(Tools.getFileUri(pictureFile)); // // DEBUG: 11/01/16 find a faster || correct way to insert values.. make sure to prevent values in the linear layout from been lost at any point.. also ensure that all values are stored with least amount of memory taken
            scrollDown();
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
        Notes_Activity.takenPhoto = true;
        return mediaFile;
    }

    public static File drawing_activity_file_temp = null;

    @Override
    protected void onResume() {
        super.onResume();
        if (savedDrawing) {
            addPhotoElement(Tools.getFileUri(drawing_activity_file_temp));
            savedDrawing = false;
            scrollDown();
        }
    }
}
