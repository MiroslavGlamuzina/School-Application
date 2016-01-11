package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.schoolapp.R;

import tools.Tools;

public class Home_Activity extends Activity implements android.view.View.OnClickListener {
    public static int CURRENT_ID = 1;
    public static boolean RECORDING_AUDIO = false;
    LinearLayout lectureNew, lectureOld, leaveFeedback, preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lectureNew = (LinearLayout) this.findViewById(R.id.main_newlecture);
        lectureOld = (LinearLayout) this.findViewById(R.id.main_oldlecture);
        leaveFeedback = (LinearLayout) this.findViewById(R.id.main_leavefeedback);
        preferences = (LinearLayout) this.findViewById(R.id.main_preferences);

        preferences.setOnClickListener(this);
        leaveFeedback.setOnClickListener(this);
        lectureNew.setOnClickListener(this);
        lectureOld.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_newlecture:
                Tools.startActivity(this, Notes_Activity.class);
                break;
            case R.id.main_oldlecture:
                break;
            case R.id.main_leavefeedback:
                sendEmail();
                break;
            case R.id.main_preferences:
                Tools.startActivity(this, Preferences_Activity.class);
                break;
            default:
                break;
        }
    }

    public void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"Remington.Productions.Feedback@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Leave Feedback");
        i.putExtra(Intent.EXTRA_TEXT, "temp holder");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Home_Activity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Tools.startActivity(this, MainActivity.class);
    }

}
