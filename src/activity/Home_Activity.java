package activity;

import com.example.schoolapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import tools.Tools;

public class Home_Activity extends Activity implements android.view.View.OnClickListener {
	public static int CURRENT_ID = 1;
	public static boolean RECORDING_AUDIO = false;
	LinearLayout lectureNew, lectureOld;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		lectureNew = (LinearLayout) this.findViewById(R.id.main_newlecture);
		lectureOld = (LinearLayout) this.findViewById(R.id.main_oldlecture);

		lectureNew.setOnClickListener(this);
		lectureOld.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_newlecture:
			break;
		case R.id.main_oldlecture:
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Tools.startActivity(this, MainActivity.class);
	}

}
