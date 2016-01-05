package activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.example.schoolapp.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import database.Database;
import tools.Tools;

public class Audio_Activity extends Activity implements OnClickListener {
	Button start, stop, btntest;
	TextView time;
	MediaRecorder recorder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);

		start = (Button) this.findViewById(R.id.start_rec);
		stop = (Button) this.findViewById(R.id.stop_rec);
		btntest = (Button) this.findViewById(R.id.audio_test);
		time = (TextView) this.findViewById(R.id.audio_stamp);

		btntest.setOnClickListener(this);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		Toast.makeText(this, "Audio LENGTH: " + (getAudioDurationString(Tools.getAllAudioFiles(this).get(0))),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.start_rec:
			Toast.makeText(this, String.valueOf(MainActivity.RECORDING_AUDIO), Toast.LENGTH_SHORT).show();
			if (recorder == null && !MainActivity.RECORDING_AUDIO) {
				Toast.makeText(this, "START recording", Toast.LENGTH_SHORT).show();
				MainActivity.RECORDING_AUDIO = true;
				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				String filename = Tools.getFileNameAudio();
				recorder.setOutputFile(filename);
				Database db = new Database(this);
				db.updateAudio(filename, Tools.getCurrentID());
				try {
					recorder.prepare();
				} catch (Exception e) {
					db.removeAudio(filename, Tools.getCurrentID());
					e.printStackTrace();
				}
				recorder.start();
			}
			break;
		case R.id.stop_rec:
			Toast.makeText(this, String.valueOf(MainActivity.RECORDING_AUDIO), Toast.LENGTH_SHORT).show();
			MainActivity.RECORDING_AUDIO = false;
			if (recorder != null) {
				Toast.makeText(this, "STOP recording", Toast.LENGTH_SHORT).show();
				recorder.stop();
				recorder.release();
				recorder = null;
			}
			break;
		case R.id.audio_test:
			String temp = Tools.listAllAudio(this);
			Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();

			// playback
			ArrayList<File> files = Tools.getAllAudioFiles(this);
			if (files.size() > 0) {
				Toast.makeText(this, files.get(0).getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse(files.get(0).getAbsolutePath().toString());
				MediaPlayer mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(getApplicationContext(), uri);
					mediaPlayer.prepare();
					mediaPlayer.start();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	public String getAudioDurationString(String file) {
		Uri uri = Uri.parse(file);
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(this, uri);
		String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		int millSecond = Integer.parseInt(durationStr);
		String audioDuration = String.format("%02d min, %02d sec", TimeUnit.MILLISECONDS.toMinutes(millSecond),
				TimeUnit.MILLISECONDS.toSeconds(millSecond)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecond)));
		return audioDuration;
	}

	@SuppressLint("NewApi")
	public String getAudioDurationString(File file) {
		Uri uri = Uri.parse(file.getAbsolutePath().toString());
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(this, uri);
		String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		int millSecond = Integer.parseInt(durationStr);
		String audioDuration = String.format("%02d min, %02d sec", TimeUnit.MILLISECONDS.toMinutes(millSecond),
				TimeUnit.MILLISECONDS.toSeconds(millSecond)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecond)));
		return audioDuration;
	}

	@SuppressLint("NewApi")
	public int getAudioDuration(String file) {
		Uri uri = Uri.parse(file);
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(this, uri);
		String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		int millSecond = Integer.parseInt(durationStr);
		return millSecond;
	}

	@SuppressLint("NewApi")
	public int getAudioDuration(File file) {
		Uri uri = Uri.parse(file.getAbsolutePath().toString());
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(this, uri);
		String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		int millSecond = Integer.parseInt(durationStr);
		return millSecond;
	}
}
