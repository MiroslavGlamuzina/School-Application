package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.R;

import java.util.ArrayList;

import database.Database;
import tools.Tools;

public class MainActivity extends Activity implements android.view.View.OnClickListener {
    public static int CURRENT_ID = 1;
    public static boolean RECORDING_AUDIO = false;

    public static TextView tv;
    public static LinearLayout ll_main, ll_scrollView, ll_db;
    public static EditText et;
    public static Button etbtn, etbtnpaint, etbtncamera, etbtnfiles, etbtnDB, etbtndeletedb, etbtntest, etbtnaudio,
            etbtnlecture, etbtnmenu, etbtnprev, etbtnprefs, testclass;
    public static ScrollView scrollView, db_scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintemp);
        et = (EditText) this.findViewById(R.id.et);
        etbtnpaint = (Button) this.findViewById(R.id.etbtnpaint);
        etbtncamera = (Button) this.findViewById(R.id.etbtncamera);
        etbtn = (Button) this.findViewById(R.id.etbtn);
        etbtnfiles = (Button) this.findViewById(R.id.etbtnfiles);
        ll_db = (LinearLayout) this.findViewById(R.id.database);
        db_scrollview = (ScrollView) this.findViewById(R.id.sv_database);
        etbtnDB = (Button) this.findViewById(R.id.etbtndb);
        etbtndeletedb = (Button) this.findViewById(R.id.etbtndeletedatabase);
        etbtntest = (Button) this.findViewById(R.id.etbtntest);
        etbtnaudio = (Button) this.findViewById(R.id.etbtnaudio);
        etbtnlecture = (Button) this.findViewById(R.id.etbtnlecture);
        etbtnmenu = (Button) this.findViewById(R.id.etbtnmenu);
        etbtnprev = (Button) this.findViewById(R.id.etbtnprev);
        etbtnprefs = (Button) this.findViewById(R.id.etbtnprefs);
        testclass = (Button) this.findViewById(R.id.etbtntesterclass);

        testclass.setOnClickListener(this);
        etbtnprefs.setOnClickListener(this);
        etbtnprev.setOnClickListener(this);
        etbtnmenu.setOnClickListener(this);
        etbtnlecture.setOnClickListener(this);
        etbtnaudio.setOnClickListener(this);
        etbtntest.setOnClickListener(this);
        etbtndeletedb.setOnClickListener(this);
        etbtnDB.setOnClickListener(this);
        etbtnpaint.setOnClickListener(this);
        etbtn.setOnClickListener(this);
        etbtncamera.setOnClickListener(this);
        etbtnfiles.setOnClickListener(this);

        Database db = new Database(this);
        if (db.size() <= 0) {
            db.createRecords(Tools.getDateString(), "Untitled", "temp", "temp", "temp", "temp", "temp", "temp", "temp");
        }
        Toast.makeText(this, "Memory Available: " + String.valueOf(Tools.memoryAvailable(this)), Toast.LENGTH_LONG).show();

        // DEBUGGING
        // Tools.startActivity(this, Home_Activity.class);
        // startActivity(new Intent(this, Home_Activity.class));
        // startActivity(new Intent(this, Notes_Activity_OLD.class));

    }

    public void addNote() {
        ll_scrollView = (LinearLayout) this.findViewById(R.id.scrollview_linearlayout);
        tv = new TextView(this);
        String edittext = "";
        et = (EditText) this.findViewById(R.id.et);
        edittext = et.getText().toString();

        Database db = new Database(this);
        db.updateNote(edittext, "1");
        Toast.makeText(this, "Description Size(): " + db.sizeNote(String.valueOf(CURRENT_ID)),
                Toast.LENGTH_SHORT).show();

        String[] temp = db.getNote(String.valueOf(CURRENT_ID)).split(Tools.HASH);
        ll_scrollView.removeAllViews();
        for (int i = 0; i < temp.length - 1; i += 2) {
            tv = new TextView(this);
            tv.setText(temp[i] + ": " + temp[i + 1]);
            ll_scrollView.addView(tv);
        }
    }

    public void removeNote() {
        ll_scrollView = (LinearLayout) this.findViewById(R.id.scrollview_linearlayout);
        tv = new TextView(this);
        String edittext = "";
        et = (EditText) this.findViewById(R.id.et);
        edittext = et.getText().toString();
        Database db = new Database(this);
        db.removeNote(edittext, "1");
        Toast.makeText(this, "Description Size(): " + db.sizeNote(String.valueOf(CURRENT_ID)),
                Toast.LENGTH_SHORT).show();
        String[] temp = db.getNote("1").split(Tools.HASH);
        ll_scrollView.removeAllViews();
        for (int i = 0; i < temp.length - 1; i += 2) {
            tv = new TextView(this);
            tv.setText(temp[i] + ": " + temp[i + 1]);
            ll_scrollView.addView(tv);
        }
    }

    @Override
    public void onClick(View v) {
        Database db = new Database(this); // temp this reference to db
        switch (v.getId()) {
            case R.id.etbtn:
                addNote();
                break;
            case R.id.etbtnpaint:
                // startActivity(new Intent(this, Paint_Activity.class));
                //asdfasdf
                Toast.makeText(this, "Paint Activity has been removed!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.etbtncamera:
                Tools.startActivity(this, Camera_Activity.class);
                break;
            case R.id.etbtnfiles:
                Tools.startActivity(this, Files_Activity.class);
                break;
            case R.id.etbtnaudio:
                Tools.startActivity(this, Audio_Activity.class);
                break;
            case R.id.etbtndeletedatabase:
                Tools.deleteDatabase(this);
                try {
                    ll_scrollView.removeAllViews();
                    ll_db.removeAllViews();
                } catch (Exception e) {
                    Toast.makeText(this, "One or both of views are already empty", Toast.LENGTH_SHORT).show();
                }
                Tools.deleteFiles(this);
                db.createRecords(Tools.getDateString(), "Untitled", "temp", "temp", "temp", "temp", "temp", "temp", "temp");
                break;
            case R.id.etbtndb:
                Toast.makeText(this, "Database size: " + db.size(), Toast.LENGTH_SHORT).show();
                ArrayList<String> testing = db.getAllEntries();
                TextView temp = new TextView(this);
                ll_db.removeAllViews();
                for (int i = 0; i < testing.size(); i++) {
                    temp = new TextView(this);
                    temp.setText(testing.get(i));
                    ll_db.addView(temp);
                }
                break;
            case R.id.etbtnlecture:
//                Tools.startActivity(this, Notes_Activity.class);
                Intent i = new Intent(this, Notes_Activity.class);
                startActivity(i);
                break;
            case R.id.etbtntest:
                removeNote();
                break;
            case R.id.etbtnmenu:
                Tools.startActivity(this, Home_Activity.class);
                break;
            case R.id.etbtnprev:
                Tools.startActivity(this, PreviousLectures_Activity.class);
                break;
            case R.id.etbtnprefs:
                Tools.startActivity(this, Preferences_Activity.class);
                break;
            case R.id.etbtntesterclass:
                Tools.startActivity(this, Notes_Activity_OLD.class);
                break;
            default:
                break;
        }
    }

}
