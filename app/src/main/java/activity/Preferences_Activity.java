package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.example.schoolapp.R;

import tools.Tools;

public class Preferences_Activity extends PreferenceActivity {
    //testing git
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preferences);

        final Preference pref_audio = findPreference(getString(R.string.pref_record_on_start));
        final Preference pref_camera = findPreference(getString(R.string.pref_camera_default));
        final Preference pref_deleteAll = findPreference(getString(R.string.pref_delete_all));
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);


        pref_audio.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newVal) {
                final boolean value = (Boolean) newVal;
                Toast.makeText(getBaseContext(), String.valueOf(sharedPref.getBoolean(getString(R.string.pref_record_on_start), true)), Toast.LENGTH_SHORT).show();//debug
                return true;
            }
        });

        pref_camera.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newVal) {
                final boolean value = (Boolean) newVal;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.pref_camera_default), value);
                editor.commit();
                Toast.makeText(getBaseContext(), String.valueOf(sharedPref.getBoolean(getString(R.string.pref_camera_default), true)), Toast.LENGTH_SHORT).show(); //debug
                return true;
            }
        });


        pref_deleteAll.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDeleteAllDialog();
                return false;
            }
        });
    }

    public void showDeleteAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Preferences_Activity.this);
        builder.setCancelable(true);
        builder.setTitle("Delete all files?");
        builder.setMessage("Clicking OK will delete all data from this application on your phone");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Tools.deleteDatabase(Preferences_Activity.this);
                        Tools.startActivity(Preferences_Activity.this, Home_Activity.class);
                    }
                }
        );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //action on dialog close
                    }
                }
        );
        builder.show();
    }
}
