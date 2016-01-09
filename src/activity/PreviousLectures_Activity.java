package activity;

import java.util.ArrayList;

import com.example.schoolapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import database.Database;
import tools.EntryList;

public class PreviousLectures_Activity extends Activity {
	LinearLayout body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previouslectures);

		body = (LinearLayout) this.findViewById(R.id.prev_list);

		ArrayList<EntryList> list = new ArrayList<EntryList>();
		Database db = new Database(this);
		for (int i = 1; i <= db.size(); i++) {
			list.add(db.getAllList(String.valueOf(i)));
		}
		for(int i =0; i < list.size(); i++)
		{
			TextView tv = new TextView(this);
			tv.setText(list.get(i).getTitle()+"\n"+list.get(i).getDate()+"\n"+list.get(i).getAudioSize()+"\n"+list.get(i).getDrawingSize()+"\n"+list.get(i).getPictureSize()+"\n"+list.get(i).getTagsSize()+"\n"+list.get(i).getNoteSize());
			body.addView(tv);
		}
	

	}

}
