package activity;

import java.util.ArrayList;

import com.example.schoolapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Files_Activity extends Activity implements OnClickListener {
	public LinearLayout files;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_files);
		files = (LinearLayout) this.findViewById(R.id.files_scrollview);
		ArrayList<String> dir = FileHelper.getParentDirectoryContentsResults();
		Log.d("FILES SIZE", String.valueOf(dir.size()));

		for (int i = 0; i < dir.size(); i++) {
			TextView tv = new TextView(this);
			tv.setText("->"+dir.get(i));
			files.addView(tv);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.camera:

			break;

		default:
			break;
		}
	}

}
