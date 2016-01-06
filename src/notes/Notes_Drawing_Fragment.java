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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import helpers.PaintView;

public class Notes_Drawing_Fragment extends Fragment implements OnClickListener {
	private final String TAG = "Notes_Drawing_Fragment";
	public static Boolean isStarted = false;
	public static Boolean isVisible = false;

	PaintView paintView;
	LinearLayout canvas;
	Button btncolor_blue, btncolor_green, btncolor_red, btncolor_yellow, btncolor_white;
	ImageButton btn_stroke1, btn_stroke2, btn_stroke3, btn_stroke4, btn_stroke5, btn_stroke6, btn_stroke7, btn_stroke8;

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

		btn_stroke1 = (ImageButton) rootView.findViewById(R.id.paint_size_one);
		btn_stroke2 = (ImageButton) rootView.findViewById(R.id.paint_size_two);
		btn_stroke3 = (ImageButton) rootView.findViewById(R.id.paint_size_three);
		btn_stroke4 = (ImageButton) rootView.findViewById(R.id.paint_size_four);
		btn_stroke5 = (ImageButton) rootView.findViewById(R.id.paint_size_five);
		btn_stroke6 = (ImageButton) rootView.findViewById(R.id.paint_size_six);
		btn_stroke7 = (ImageButton) rootView.findViewById(R.id.paint_size_seven);
		btn_stroke8 = (ImageButton) rootView.findViewById(R.id.paint_size_eight);

		btn_stroke1.setOnClickListener(this);
		btn_stroke2.setOnClickListener(this);
		btn_stroke3.setOnClickListener(this);
		btn_stroke4.setOnClickListener(this);
		btn_stroke5.setOnClickListener(this);
		btn_stroke6.setOnClickListener(this);
		btn_stroke7.setOnClickListener(this);
		btn_stroke8.setOnClickListener(this);
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
		setStrokeWidth(v);
	}

	public void setStrokeWidth(View v) {
		switch (v.getId()) {
		case R.id.paint_size_one:
			paintView.paint.setStrokeWidth(5f);
			paintView.strokeWidth = 5f;
			break;
		case R.id.paint_size_two:
			paintView.paint.setStrokeWidth(10f);
			paintView.strokeWidth = 10f;
			break;
		case R.id.paint_size_three:
			paintView.paint.setStrokeWidth(15f);
			paintView.strokeWidth = 15f;
			break;
		case R.id.paint_size_four:
			paintView.paint.setStrokeWidth(20f);
			paintView.strokeWidth = 20f;
			break;
		case R.id.paint_size_five:
			paintView.paint.setStrokeWidth(25f);
			paintView.strokeWidth = 25f;
			break;
		case R.id.paint_size_six:
			paintView.paint.setStrokeWidth(30f);
			paintView.strokeWidth = 30f;
			break;
		case R.id.paint_size_seven:
			paintView.paint.setStrokeWidth(35f);
			paintView.strokeWidth = 35f;
			break;
		case R.id.paint_size_eight:
			paintView.paint.setStrokeWidth(40f);
			paintView.strokeWidth = 40f;
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