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
import android.widget.Button;
import android.widget.LinearLayout;
import helpers.PaintView;

public class Notes_Drawing_Fragment extends Fragment implements OnClickListener {
	private final String TAG = "Notes_Drawing_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;

	PaintView paintView;
	LinearLayout canvas;
	Button btncolor_blue, btncolor_green, btncolor_red, btncolor_yellow, btncolor_white;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notes_drawing, container, false);
		paintView = new PaintView(getContext());
		paintView.requestFocus();
		canvas = (LinearLayout) rootView.findViewById(R.id.canvas);
		canvas.addView(paintView);
		btncolor_blue = (Button) rootView.findViewById(R.id.colorbtn_blue);
		btncolor_green = (Button) rootView.findViewById(R.id.colorbtn_green);
		btncolor_red = (Button) rootView.findViewById(R.id.colorbtn_red);
		btncolor_yellow = (Button) rootView.findViewById(R.id.colorbtn_yellow);
		btncolor_white = (Button) rootView.findViewById(R.id.colorbtn_white);

		btncolor_blue.setOnClickListener(this);
		btncolor_green.setOnClickListener(this);
		btncolor_red.setOnClickListener(this);
		btncolor_yellow.setOnClickListener(this);
		btncolor_white.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.colorbtn_blue:
			paintView.paint.setColor(Color.BLUE);
			paintView.color = Color.BLUE;
			break;
		case R.id.colorbtn_white:
			paintView.paint.setColor(Color.WHITE);
			paintView.color = Color.WHITE;
			break;
		case R.id.colorbtn_red:
			paintView.paint.setColor(Color.RED);
			paintView.color = Color.RED;
			break;
		case R.id.colorbtn_green:
			paintView.paint.setColor(Color.GREEN);
			paintView.color = Color.GREEN;
			break;
		case R.id.colorbtn_yellow:
			paintView.paint.setColor(Color.YELLOW);
			paintView.color = Color.YELLOW;
			break;

		default:
			break;
		}
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
		Notes_Main_Fragment.isVisible = false;
		Notes_Main_Fragment.isStarted = false;
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