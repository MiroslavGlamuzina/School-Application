package activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.R;

import java.io.File;
import java.util.ArrayList;

import database.Database;
import tools.Entry;
import tools.Tools;

/**
 * Created by miroslav on 11/01/16.
 */

public class Notes_Activity extends Activity implements View.OnClickListener {
    private final String TAG = "Notes_Activity";
    public static boolean isCamera = true;
    public static boolean isRecording = true;
    public static boolean takenPhoto = false;
    public static boolean savedDrawing = false;

    TextView lecture_title;
    Button camtoggle_btn, audio_btn, submit_btn, flag_btn;
    EditText note_et;
    ScrollView scroll;
    LinearLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);
        camtoggle_btn = (Button) this.findViewById(R.id.notes_camtoggle);
        audio_btn = (Button) this.findViewById(R.id.notes_audiotoggle);
        submit_btn = (Button) this.findViewById(R.id.notes_submit);
        flag_btn = (Button) this.findViewById(R.id.notes_flag);
        body = (LinearLayout) this.findViewById(R.id.notes_body);
        note_et = (EditText) this.findViewById(R.id.notes_et);
        scroll = (ScrollView) this.findViewById(R.id.notes_scrollview);
        lecture_title = (TextView) this.findViewById(R.id.notes_lecturetitle);

        Database db = new Database(this);
//		db.updateTitle("Biology Lecture 1", Tools.getCurrentID());
        lecture_title.setText(db.getTitle(Tools.getCurrentID()));

        flag_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        audio_btn.setOnClickListener(this);
        camtoggle_btn.setOnClickListener(this);

        // The following block of code will grab the sorted arraylist from the
        // database to repopulated the page after close..!!!
        // DEBUGGING();
        populateBody();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
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
                    if (list.get(i).getType().equals(new String(Entry.NOTE))) {
                        note_temp += list.get(i).getVal() + "\n";
                    } else if (list.get(i).getType().equals(new String(Entry.PICTURE))) {
                        if (!note_temp.equals(new String(""))) {
                            addTextElement(note_temp);
                            note_temp = "";
                        }
                        addPhotoElement(
                                Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
                    } else if (list.get(i).getType().equals(new String(Entry.DRAWING))) {
                        if (!note_temp.equals(new String(""))) {
                            addTextElement(note_temp);
                            note_temp = "";
                        }
                        addPhotoElement(
                                Uri.fromFile(new File(Tools.getContextWrapperDir(Notes_Activity.this), list.get(i).getVal())));
                    } else if (list.get(i).getType().equals(new String(Entry.FLAG))) {
                        if (!note_temp.equals(new String(""))) {
                            addTextElement(note_temp);
                            note_temp = "";
                        }
                        addFlagElement();
                    } else if (!note_temp.equals(new String(""))) {
                        addTextElement(note_temp.substring(0, note_temp.length() - 2));
                        note_temp = "";
                    }
                    // addPhotoElement(Uri.parse(list.get(i).getVal()));
                    try {
                        wait(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        item.setLayoutParams(Tools.setMargins());
        item.setBackgroundResource(R.drawable.shape);
        TableRow.LayoutParams item_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.RIGHT;
        item.setLayoutParams(item_params);
        item.setPadding(-20, 40, -20, 40);

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

    public void insertNote() {
        TextView tv = new TextView(this);
        String edittext = "";
        edittext = note_et.getText().toString();

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
            case R.id.notes_camtoggle:
                if (camtoggle_btn.getText().equals("vid")) {
                    camtoggle_btn.setText("cam");
                    isCamera = true;
                } else if (camtoggle_btn.getText().equals("cam")) {
                    camtoggle_btn.setText("vid");
                    isCamera = false;
                }
                break;
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
            default:
                break;
        }
    }
}
