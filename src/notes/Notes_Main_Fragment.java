package notes;

import com.example.schoolapp.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Notes_Main_Fragment extends Fragment implements OnClickListener {
	private final String TAG = "Notes_Main_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;
	public static boolean isCamera = true;
	public static boolean isRecording = true;
	Button camtoggle_btn, audio_btn;
	LinearLayout body;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notes_main, container, false);
		camtoggle_btn = (Button) rootView.findViewById(R.id.notes_camtoggle);
		audio_btn = (Button) rootView.findViewById(R.id.notes_audiotoggle);
		body = (LinearLayout) rootView.findViewById(R.id.notes_body);

		audio_btn.setOnClickListener(this);
		camtoggle_btn.setOnClickListener(this);

		addElement();
		addElement();
		addElement();
		addElement();
		addElement();
		addElement();
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
		default:
			break;
		}
	}

	public void addElement() {
		LinearLayout item = new LinearLayout(getContext());
		item.setBackgroundColor(Color.BLACK);
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		item.setLayoutParams(params);
		item.setBackgroundResource(R.drawable.shape);
			
		TextView tv = new TextView(getContext());
		tv.setText("this is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an elementthis is an element");
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