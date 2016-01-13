package activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.schoolapp.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import database.Database;
import tools.Tools;

// // TODO: 11/01/16 create an audio service to use inside of the main application
public class Audio_Activity extends Activity implements OnClickListener {
    Button start, stop, btntest;
    MediaRecorder recorder = null;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        start = (Button) this.findViewById(R.id.start_rec);
        stop = (Button) this.findViewById(R.id.stop_rec);
        btntest = (Button) this.findViewById(R.id.audio_test);
        chronometer = (Chronometer) this.findViewById(R.id.audio_chronometer);

        btntest.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_rec:
                if (recorder == null && !MainActivity.RECORDING_AUDIO) {
                    MainActivity.RECORDING_AUDIO = true;
                    String filename = Tools.getContextWrapperDir(this).getAbsolutePath() + "/" + Tools.getFileNameAudio();
                    recorder = new MediaRecorder();

                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    recorder.setOutputFile(filename);

                    Database db = new Database(this);
                    db.updateAudio(filename, Tools.getCurrentID());
                    try {
                        recorder.prepare();
                    } catch (Exception e) {
                        Toast.makeText(this, "recorder.prepare() failed!!! >:(", Toast.LENGTH_SHORT).show();
                        db.removeAudio(filename, Tools.getCurrentID());
                        e.printStackTrace();
                    }
                    recorder.start();
                    chronometer = (Chronometer) this.findViewById(R.id.audio_chronometer); //timer -- Chronometer()
                    chronometer.setBase(SystemClock.elapsedRealtime()); //timer -- Chronometer()
                    chronometer.start(); //timer -- Chronometer()
                }
                break;
            case R.id.stop_rec:
                MainActivity.RECORDING_AUDIO = false;
                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    chronometer.stop(); //timer -- Chronometer()
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
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
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
