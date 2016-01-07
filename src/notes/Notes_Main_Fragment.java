package notes;

import java.util.ArrayList;

import com.example.schoolapp.R;

import activity.MainActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import database.Database;
import tools.Entry;
import tools.Tools;

public class Notes_Main_Fragment extends Fragment implements OnClickListener {
	private final String TAG = "Notes_Main_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;
	public static boolean isCamera = true;
	public static boolean isRecording = true;
	Button camtoggle_btn, audio_btn, submit_btn;
	EditText note_et;
	ScrollView scroll;
	LinearLayout body;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notes_main, container, false);
		camtoggle_btn = (Button) rootView.findViewById(R.id.notes_camtoggle);
		audio_btn = (Button) rootView.findViewById(R.id.notes_audiotoggle);
		body = (LinearLayout) rootView.findViewById(R.id.notes_body);
		submit_btn = (Button) rootView.findViewById(R.id.notes_submit);
		note_et = (EditText) rootView.findViewById(R.id.notes_et);
		scroll = (ScrollView) rootView.findViewById(R.id.notes_scrollview);

		submit_btn.setOnClickListener(this);
		audio_btn.setOnClickListener(this);
		camtoggle_btn.setOnClickListener(this);

		addAudioElement();
		// addPhotoElement(Tools.getFileUriDEBUG(getContext()));
		addTextElement(
				"this is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an element");
		// addPhotoElement(Tools.getFileUriDEBUG(getContext()));

		
		//The following block of code will grab the sorted arraylist from the database to repopulated the page after close..!!!
		Database db = new Database(getContext());
		addTextElement(db.getAllDEBUG("1"));
		ArrayList<Entry> temp = Entry.sortEntries(db.getAll("1"));
		String res = "";
		for (int i = 0; i < temp.size(); i++) {
			res += temp.get(i).getDate() + ", " + temp.get(i).getVal() + ", " + temp.get(i).getType() + "\n";
		}
		addTextElement(res);

		return rootView;
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
			addNote();
			break;
		default:
			break;
		}
	}

	public void addNote() {
		TextView tv = new TextView(getContext());
		String edittext = "";
		edittext = note_et.getText().toString();

		Database db = new Database(getContext());
		db.updateDescription(edittext, "1");
		Toast.makeText(getContext(),
				"Description Size(): " + db.sizeDescription(String.valueOf(MainActivity.CURRENT_ID)),
				Toast.LENGTH_SHORT).show();
		addTextElement(edittext);
	}

	public void addTextElement(String val) {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);

		TextView tv = new TextView(getContext());
		tv.setText(val);
		item.addView(tv);
		body.addView(item);
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

	@SuppressLint("NewApi")
	public void addPhotoElement(Uri imgUri) {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);

		ImageView iv = new ImageView(getContext());
		TableRow.LayoutParams params = new TableRow.LayoutParams(500, 500);
		params.gravity = Gravity.CENTER_HORIZONTAL;

		iv.setLayoutParams(params);
		iv.setImageURI(imgUri);
		item.addView(iv);
		body.addView(item);
	}

	public void addDrawElement() {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);

	}

	public void addVideoElement() {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);

	}

	public void addAudioElement() {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);
		item.setWeightSum(10f);

		ImageView iv = new ImageView(getContext());
		iv.setBackgroundResource(R.drawable.mic);
		TableRow.LayoutParams ivparams = new TableRow.LayoutParams(75, 75);
		ivparams.setMargins(25, 25, 55, 25);
		iv.setLayoutParams(ivparams);

		TextView tv = new TextView(getContext());
		TableRow.LayoutParams tvparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		tvparams.gravity = Gravity.CENTER;
		tv.setLayoutParams(tvparams);
		tv.setText("Class is in session . . . ");

		item.addView(iv);
		item.addView(tv);
		body.addView(item);

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
		Notes_Drawing_Fragment.isVisible = false;
		Notes_Drawing_Fragment.isStarted = false;
		Notes_Camera_Fragment.isVisible = false;
		Notes_Camera_Fragment.isStarted = false;
		isVisible = isVisibleToUser;
		if (isStarted && isVisible) {
			isActive();
		}
	}

	public void isActive() {
		Log.d(TAG, "shown!");

	}
}