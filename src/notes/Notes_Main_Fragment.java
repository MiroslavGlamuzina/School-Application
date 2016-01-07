package notes;

import java.io.File;
import java.util.ArrayList;

import com.example.schoolapp.R;

import activity.MainActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
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

@SuppressLint("RtlHardcoded")
public class Notes_Main_Fragment extends Fragment implements OnClickListener {
	private final String TAG = "Notes_Main_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;
	public static boolean isCamera = true;
	public static boolean isRecording = true;
	public static boolean takenPhoto = false;
	public static boolean savedDrawing = false;
	
	TextView lecture_title;
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
		lecture_title = (TextView)rootView.findViewById(R.id.notes_lecturetitle);
		
		Database db = new Database(getContext());
		db.updateTitle("Biology Lecture 1", Tools.getCurrentID());
		lecture_title.setText(db.getTitle(Tools.getCurrentID()));
		
		submit_btn.setOnClickListener(this);
		audio_btn.setOnClickListener(this);
		camtoggle_btn.setOnClickListener(this);

		// The following block of code will grab the sorted arraylist from the
		// database to repopulated the page after close..!!!
		// DEBUGGING();
		populateBody();

		return rootView;
	}

	private void DEBUGGING() {
		Database db = new Database(getContext());
		ArrayList<Entry> temp = Entry.sortEntries(db.getAll("1"));
		String res = "";
		for (int i = 0; i < temp.size(); i++) {
			res += temp.get(i).getDate() + ", " + temp.get(i).getVal() + ", " + temp.get(i).getType() + "\n";
		}
		addTextElement(res);
	}

	public void populateBody() {
		body.removeAllViews();
		addAudioElement();// debugging
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				Database db = new Database(getContext());
				ArrayList<Entry> list = Entry.sortEntries(db.getAll("1"));
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
								Uri.fromFile(new File(Tools.getContextWrapperDir(getContext()), list.get(i).getVal())));
					} else if (list.get(i).getType().equals(new String(Entry.DRAWING))) {
						if (!note_temp.equals(new String(""))) {
							addTextElement(note_temp);
							note_temp = "";
						}
						addPhotoElement(
								Uri.fromFile(new File(Tools.getContextWrapperDir(getContext()), list.get(i).getVal())));
					} else if (!note_temp.equals(new String(""))) {
						addTextElement(note_temp.substring(0, note_temp.length() - 2));
						note_temp = "";
					}
					// addPhotoElement(Uri.parse(list.get(i).getVal()));
					try {
						wait(50);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void test() {

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
		default:
			break;
		}
	}

	public void insertNote() {
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

	@SuppressLint("NewApi")
	public void addTextElement(String val) {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);
		TableRow.LayoutParams item_params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.LEFT;
		item.setLayoutParams(item_params);

		TextView tv = new TextView(getContext());
		tv.setText(val);
		item.addView(tv);
		body.addView(item);

		scrollDown();
	}

	@SuppressLint("NewApi")
	public void addPhotoElement(Uri imgUri) {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		item.setLayoutParams(Tools.setMargins());
		item.setBackgroundResource(R.drawable.shape);
		TableRow.LayoutParams item_params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
//		item_params.gravity =Gravity.RIGHT;
		item.setLayoutParams(item_params);
		item.setPadding(-20, 40, -20, 40);

		ImageView iv = new ImageView(getContext());
		TableRow.LayoutParams iv_params = new TableRow.LayoutParams(500, 500);
		iv.setLayoutParams(iv_params);
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

	public void scrollDown() {
		scroll.postDelayed(new Runnable() {
			@Override
			public void run() {
				scroll.fullScroll(View.FOCUS_DOWN);
			}
		}, 250);
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
		if (isVisibleToUser && takenPhoto) {
			takenPhoto = false;
			populateBody();
		}
		if (isVisibleToUser && savedDrawing) {
			savedDrawing = false;
			populateBody();
		}
		if (isStarted && isVisible) {
			isActive();
		}
	}

	public void isActive() {
		Log.d(TAG, "shown!");

	}
}
