package activity;

import java.util.ArrayList;

import com.example.schoolapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import database.Database;
import tools.EntryList;

public class PreviousLectures_Activity extends Activity {
	ArrayList<EntryList> list = new ArrayList<EntryList>();
	LinearLayout body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previouslectures);

		body = (LinearLayout) this.findViewById(R.id.prev_list);

		Database db = new Database(this);
		list.add(db.getAllList("1"));

		for (int id = 0; id < list.size(); id++) {
			TextView tv = new TextView(this);
			tv.setText("Title: " + list.get(id).getTitle() + ", Date: " + list.get(id).getDate() + ", AudioSize: "
					+ list.get(id).getAudioSize() + ", PictureSize: " + list.get(id).getPictureSize()
					+ ", DrawingSize: " + list.get(id).getDrawingSize() + ", NoteSize: " + list.get(id).getNoteSize());
			body.addView(tv);
		}
	}

}
