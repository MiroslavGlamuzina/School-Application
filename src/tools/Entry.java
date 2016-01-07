package tools;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class Entry {

	private String val;
	private String date;
	private String type;
	public static final String AUDIO = "AUDIO";
	public static final String PICTURE = "PICTURE";
	public static final String DRAWING = "DRAWING";
	public static final String VIDEO = "VIDEO";
	public static final String NOTE = "NOTE";

	public Entry(String date, String val, String type) {
		this.val = val;
		this.date = date;
		this.type = type;
	}

	public void copy(Entry other) {
		this.val = other.val;
		this.date = other.date;
		this.type = other.type;
	}

//	@SuppressWarnings("deprecation")
	public boolean compareDate(Entry other) {
		long curtemp = Date.parse(this.date);
		long othertemp = Date.parse(other.date);
//		Log.i("curtemp", "val "+String.valueOf(curtemp));
//		Log.i("othertemp", "val "+String.valueOf(othertemp));
		if (curtemp > othertemp) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<Entry> sortEntries(ArrayList<Entry> list) {
		Entry temp = new Entry("", "", "");
		for (int i = 0; i < list.size(); i++) {
			for (int z = i + 1; z < list.size(); z++) {
				if (list.get(i).compareDate(list.get(z))) {
					temp.copy(list.get(z));
					list.get(z).copy(list.get(i));
					list.get(i).copy(temp);
				}
			}
		}
		return list;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
