package activity;

/**
 * Created by miroslav on 17/01/16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.schoolapp.R;


public class AudioService extends Service {
    private int NOTIFICATION = R.string.app_name;
    private NotificationManager mNM;

    @Override
    public void onCreate() {
//        super.onStart(intent, startId);
        Log.i("AudioService", "Service started...");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(getApplicationContext(), Notes_Activity.class), 0);
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)  // the status icon
                .setTicker("status text")  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.app_name))  // the label of the entry
                .setContentText("content text")  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        mNM.notify(NOTIFICATION, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNM.cancel(NOTIFICATION);
        Toast.makeText(this, "Service destroyed...", Toast.LENGTH_LONG).show();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}