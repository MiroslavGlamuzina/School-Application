package activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
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

//todo create bubble icions fo the amount of different pictures and images drawn
//// TODO: 15/01/16 create DUMP() method to un-allocate resources. . .
//// TODO: 11/01/16 dynamic scroll .. meaning. only hold a set number of items from the list in memory rest let the garbage collector take care off.
public class Notes_Activity extends Activity implements View.OnClickListener {
    private final String TAG = "Notes_Activity";
    public static boolean isCamera = true;
    public static boolean isRecording = true;
    public static boolean takenPhoto = false;
    public static boolean savedDrawing = false;

    TextView lecture_title;
    LinearLayout camera_btn, video_btn, audio_btn, flag_btn, draw_btn, back_btn;
    TextView note_et;
    ScrollView scroll;
    LinearLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notes_main);
        camera_btn = (LinearLayout) this.findViewById(R.id.notes_camera);
        audio_btn = (LinearLayout) this.findViewById(R.id.notes_audiotoggle);
        flag_btn = (LinearLayout) this.findViewById(R.id.notes_flag);
        video_btn = (LinearLayout) this.findViewById(R.id.notes_video);
        draw_btn = (LinearLayout) this.findViewById(R.id.notes_draw);
        body = (LinearLayout) this.findViewById(R.id.notes_body);
        note_et = (TextView) this.findViewById(R.id.notes_et);
        scroll = (ScrollView) this.findViewById(R.id.notes_scrollview);
        lecture_title = (TextView) this.findViewById(R.id.notes_lecturetitle);
        back_btn = (LinearLayout) this.findViewById(R.id.notes_back_to_home);

        draw_btn.setOnClickListener(this);
        video_btn.setOnClickListener(this);
        flag_btn.setOnClickListener(this);
        audio_btn.setOnClickListener(this);
        camera_btn.setOnClickListener(this);
        lecture_title.setOnClickListener(this);
        note_et.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        // The following block of code will grab the sorted arraylist from the
        // database to repopulated the page after close..!!!
        // DEBUGGING();
        populateBody(populatBodyStartIndex(), populateBodyEndIndex());
        startService(new Intent(this, AudioService.class));
    }

    public void dump() {
        camera_btn = null;
        audio_btn = null;
        flag_btn = null;
        video_btn = null;
        draw_btn = null;
        note_et = null;
        scroll = null;
        lecture_title = null;
        back_btn = null;
//        body.removeAllViews();
//        body = null;
        Runtime.getRuntime().gc();
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

    public int populatBodyStartIndex() {

        return 0;
    }

    public int populateBodyEndIndex() {

        return 20;
    }


    public void populateBody(int start_index, int end_index) {
        body.removeAllViews();
        addAudioElement();// debugging
        //populate
        Database db = new Database(Notes_Activity.this);
        lecture_title.setText(db.getTitle(Tools.getCurrentID()));
        ArrayList<Entry> list = Entry.sortEntries(db.getAllFromID("1"));
        for (int i = start_index; i < list.size(); i++) {
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

            //// TODO alt
//                              String note_temp = "";
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

    WebView w;

    @SuppressLint("NewApi")
    public void addTextElement(final String val) {
        //todo check for URL
        if (Tools.checkURL(val)) {
            LinearLayout item = new LinearLayout(this);
            item.setBackgroundColor(Color.BLACK);
            item.setLayoutParams(Tools.setMargins());
            item.setBackgroundResource(R.drawable.shape);
            item.setOrientation(LinearLayout.VERTICAL);
            TableRow.LayoutParams item_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            item_params.setMargins(5, 8, 5, 8);
            item.setLayoutParams(item_params);
            TextView tv = new TextView(this);
            tv.setTextColor(Color.parseColor("#00FFFF"));
            tv.setText(val);

            if (Tools.checkURLType(val)) {
//          webview ------
                w = new WebView(this);
                WebSettings webSettings = w.getSettings();
                webSettings.setJavaScriptEnabled(true);
                w.setVerticalScrollBarEnabled(false);
                w.setHorizontalScrollBarEnabled(false);
                w.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        Picture picture = view.capturePicture();
                        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(b);
                        picture.draw(c);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(Tools.getContextWrapperDir(Notes_Activity.this) + "/temp.jpg");
                            if (fos != null) {
                                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                w.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return (event.getAction() == MotionEvent.ACTION_MOVE);
                    }
                });

                w.loadUrl(Tools.checkURLTypeWebView(val));
                item.addView(w, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
            }
            //clickable
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = val;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            //webicew -------------

            item.addView(tv);
            body.addView(item);
            scrollDown();
            addTimeStamp();
        } else {
            LinearLayout item = new LinearLayout(this);
            item.setBackgroundColor(Color.BLACK);
            item.setLayoutParams(Tools.setMargins());
            item.setBackgroundResource(R.drawable.shape);
            TableRow.LayoutParams item_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.LEFT;
            item_params.setMargins(5, 8, 5, 8);
            item.setLayoutParams(item_params);

            TextView tv = new TextView(this);
            tv.setTextColor(Color.WHITE);
            tv.setText(val);
            item.addView(tv);
            body.addView(item);
            scrollDown();
            addTimeStamp();
        }

    }

    public void onClickId(View v) {
        switch (v.getId()) {
            case R.id.id1:
                Toast.makeText(this, "id1 (text element) HIT", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
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
        addTimeStamp();
    }

    public void addDrawElement() {
        LinearLayout item = new LinearLayout(this);
        item.setBackgroundColor(Color.BLACK);
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);
        addTimeStamp();
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
        tv.setTextColor(Color.WHITE);

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
        tv.setTextColor(Color.WHITE);

        item.addView(iv);
        item.addView(tv);
        body.addView(item);
        addTimeStamp();
    }

    public void addTimeStamp() {
        TextView tv = new TextView(this);
        TableRow.LayoutParams tvparams_time = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvparams_time.gravity = Gravity.RIGHT;
        tv.setText(Tools.getTimeStamp());
        tv.setTextColor(Color.GRAY);
        tv.setTextSize(10f);
        tv.setLayoutParams(tvparams_time);
        tv.setPadding(0, 0, 5, 0);
        body.addView(tv);
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
//            case R.id.notes_submit:
//                insertNote();
//                break;
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
            case R.id.notes_lecturetitle:
                updateTitle();
                break;
            case R.id.notes_et:
                addNoteAlertDialog();
                break;
            case R.id.notes_back_to_home:
                Tools.startActivity(this, Home_Activity.class);
                break;
            default:
                break;
        }
        onClickId(v);
    }

    AlertDialog.Builder builder;

    public void addNoteAlertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Add New Note");
//        builder.
        final EditText input = new EditText(Notes_Activity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database db = new Database(Notes_Activity.this);
                        db.updateNote(input.getText().toString(), Tools.getCurrentID());
                        addTextElement(input.getText().toString());
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                }
        );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //action on dialog close
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                }
        );
        builder.show();
    }

    public void updateTitle() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Change Lecture Title");

        final EditText input = new EditText(Notes_Activity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database db = new Database(Notes_Activity.this);
                        db.updateTitle(input.getText().toString(), Tools.getCurrentID());
                        lecture_title.setText(db.getTitle(Tools.getCurrentID()));
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                }
        );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //action on dialog close
                    }
                }
        );
        builder.show();
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
                populateBody(populatBodyStartIndex(), populateBodyEndIndex());//// FIXME: 11/01/16 -> do a neater adding the layout... consumes too much RAM
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

    @Override
    protected void onDestroy() {
//        dump();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
//        dump();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}
