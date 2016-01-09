package activity;

import java.util.ArrayList;

import com.example.schoolapp.R;

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
import database.Database;
import tools.Tools;

public class MainActivity extends Activity implements android.view.View.OnClickListener {
	public static int CURRENT_ID = 1;
	public static boolean RECORDING_AUDIO = false;

	public static TextView tv;
	public static LinearLayout ll_main, ll_scrollView, ll_db;
	public static EditText et;
	public static Button etbtn, etbtnpaint, etbtncamera, etbtnfiles, etbtnDB, etbtndeletedb, etbtntest, etbtnaudio,
			etbtnlecture, etbtnmenu,etbtnprev;
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
			db.createRecords(Tools.getDateString(), "", "", "", "", "", "", "");
		}
		
		
		// DEBUGGING
		// Tools.startActivity(this, Home_Activity.class);
		// startActivity(new Intent(this, Home_Activity.class));
		// startActivity(new Intent(this, Notes_Activity.class));

	}

	public void addNote() {
		ll_scrollView = (LinearLayout) this.findViewById(R.id.scrollview_linearlayout);
		tv = new TextView(this);
		String edittext = "";
		et = (EditText) this.findViewById(R.id.et);
		edittext = et.getText().toString();

		Database db = new Database(this);
		db.updateDescription(edittext, "1");
		Toast.makeText(this, "Description Size(): " + db.sizeDescription(String.valueOf(CURRENT_ID)),
				Toast.LENGTH_SHORT).show();

		String[] temp = db.getDescription(String.valueOf(CURRENT_ID)).split(Tools.HASH);
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
		db.removeDescription(edittext, "1");
		Toast.makeText(this, "Description Size(): " + db.sizeDescription(String.valueOf(CURRENT_ID)),
				Toast.LENGTH_SHORT).show();
		String[] temp = db.getDescription("1").split(Tools.HASH);
		ll_scrollView.removeAllViews();
		for (int i = 0; i < temp.length - 1; i += 2) {
			tv = new TextView(this);
			tv.setText(temp[i] + ": " + temp[i + 1]);
			ll_scrollView.addView(tv);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.etbtn:
			addNote();
			break;
		case R.id.etbtnpaint:
			// startActivity(new Intent(this, Paint_Activity.class));
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
			Tools.delteDatabase(this);
			ll_scrollView.removeAllViews();
			ll_db.removeAllViews();
			Tools.deleteFiles(this);
			break;
		case R.id.etbtndb:
			Database db = new Database(this);
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
			Tools.startActivity(this, Notes_Activity.class);
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
		default:
			break;
		}
	}

}
