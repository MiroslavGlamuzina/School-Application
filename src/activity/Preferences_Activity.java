package activity;

import com.example.schoolapp.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Preferences_Activity extends PreferenceActivity implements OnClickListener {
	private static String key_camera = "";
	private static String key_audio = "";
	public CheckBoxPreference audio, camera;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_preferences);
		final Preference audio = findPreference(getString(R.string.pref_record_on_start));
		final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

		
		//GOOD!!!
		audio.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newVal) {
				final boolean value = (Boolean) newVal;
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putBoolean(getString(R.string.pref_record_on_start), value);
				editor.commit();
				Toast.makeText(getBaseContext(), String.valueOf(sharedPref.getBoolean(getString(R.string.pref_record_on_start), true)), Toast.LENGTH_SHORT).show();
				return true;
			}

		});

		// save
		// SharedPreferences sharedPref =
		// this.getPreferences(Context.MODE_PRIVATE);
		// SharedPreferences.Editor editor = sharedPref.edit();
		// editor.putBoolean(getString(R.string.pref_record_on_start),
		// false);
		// editor.putBoolean(getString(R.string.pref_camera_default),
		// true);
		// editor.commit();

		// load
		SharedPreferences sharedPrefload = this.getPreferences(Context.MODE_PRIVATE);
		// boolean record_on_start =
		// sharedPrefload.getBoolean(R.string.pref_record_on_start);
		boolean record_on_start = sharedPrefload.getBoolean(getString(R.string.pref_record_on_start), true);

		Toast.makeText(this, String.valueOf(record_on_start), Toast.LENGTH_SHORT).show();
		// long highScore =
		// sharedPrefload.getInt(getString(R.string.saved_high_score),
		// defaultValue);
	}

	// @Override
	// public boolean onPreferenceClick(Preference preference) {
	// if (preference.getKey().equals(new
	// String(getString(R.string.pref_record_on_start)))) {
	// SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
	// boolean record_on_start =
	// sharedPref.getBoolean(getString(R.string.pref_record_on_start), true);
	// SharedPreferences.Editor editor = sharedPref.edit();
	// if (record_on_start) {
	// editor.putBoolean(getString(R.string.pref_record_on_start), false);
	// } else {
	// editor.putBoolean(getString(R.string.pref_record_on_start), true);
	//
	// }
	// editor.commit();
	// Toast.makeText(this, String.valueOf(record_on_start),
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// return false;
	// }

	@Override
	public void onClick(View v) {
		// if (preference.getKey().equals(new
		// String(getString(R.string.pref_record_on_start)))) {
		// SharedPreferences sharedPref =
		// this.getPreferences(Context.MODE_PRIVATE);
		// boolean record_on_start =
		// sharedPref.getBoolean(getString(R.string.pref_record_on_start),
		// true);
		// SharedPreferences.Editor editor = sharedPref.edit();
		// if (record_on_start) {
		// editor.putBoolean(getString(R.string.pref_record_on_start), false);
		// } else {
		// editor.putBoolean(getString(R.string.pref_record_on_start), true);
		//
		// }
		// editor.commit();
		// Toast.makeText(this, String.valueOf(record_on_start),
		// Toast.LENGTH_SHORT).show();
		// }
	}

	// @Override
	// public boolean onPreferenceChange(Preference preference, Object newValue)
	// {
	// if (preference.getKey().equals(new
	// String(getString(R.string.pref_record_on_start)))) {
	// SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
	// boolean record_on_start =
	// sharedPref.getBoolean(getString(R.string.pref_record_on_start), true);
	// SharedPreferences.Editor editor = sharedPref.edit();
	// if (record_on_start) {
	// editor.putBoolean(getString(R.string.pref_record_on_start), false);
	// } else {
	// editor.putBoolean(getString(R.string.pref_record_on_start), true);
	//
	// }
	// editor.commit();
	// Toast.makeText(this, String.valueOf(record_on_start),
	// Toast.LENGTH_SHORT).show();
	// }
	// return false;
	// }

}
