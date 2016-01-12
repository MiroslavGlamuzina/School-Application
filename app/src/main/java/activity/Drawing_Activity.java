package activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.schoolapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import database.Database;
import helpers.PaintView;
import notes.Notes_Main_Fragment;
import tools.Tools;

/**
 * Created by miroslav on 11/01/16.
 */
public class Drawing_Activity extends Activity implements View.OnClickListener {
    private final String TAG = "Notes_Drawing_Fragment";
    public static Boolean isStarted = false;
    public static Boolean isVisible = false;

    PaintView paintView;
    LinearLayout canvas;
    Button btncolor_blue, btncolor_green, btncolor_red, btncolor_yellow, btncolor_white;
    ImageButton btn_stroke1, btn_stroke2, btn_stroke3, btn_stroke4, btn_stroke5, btn_stroke6, btn_stroke7, btn_stroke8;
    Button btn_save, btn_del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_drawing);
        paintView = new PaintView(this);
        paintView.requestFocus();
        canvas = (LinearLayout) this.findViewById(R.id.canvas);
        canvas.addView(paintView);

        btncolor_blue = (Button) this.findViewById(R.id.colorbtn_blue);
        btncolor_green = (Button) this.findViewById(R.id.colorbtn_green);
        btncolor_red = (Button) this.findViewById(R.id.colorbtn_red);
        btncolor_yellow = (Button) this.findViewById(R.id.colorbtn_yellow);
        btncolor_white = (Button) this.findViewById(R.id.colorbtn_white);

        btn_stroke1 = (ImageButton) this.findViewById(R.id.paint_size_one);
        btn_stroke2 = (ImageButton) this.findViewById(R.id.paint_size_two);
        btn_stroke3 = (ImageButton) this.findViewById(R.id.paint_size_three);
        btn_stroke4 = (ImageButton) this.findViewById(R.id.paint_size_four);
        btn_stroke5 = (ImageButton) this.findViewById(R.id.paint_size_five);
        btn_stroke6 = (ImageButton) this.findViewById(R.id.paint_size_six);
        btn_stroke7 = (ImageButton) this.findViewById(R.id.paint_size_seven);
        btn_stroke8 = (ImageButton) this.findViewById(R.id.paint_size_eight);

        btn_save = (Button) this.findViewById(R.id.paint_save);
        btn_del = (Button) this.findViewById(R.id.paint_del);

        btn_save.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        btn_stroke1.setOnClickListener(this);
        btn_stroke2.setOnClickListener(this);
        btn_stroke3.setOnClickListener(this);
        btn_stroke4.setOnClickListener(this);
        btn_stroke5.setOnClickListener(this);
        btn_stroke6.setOnClickListener(this);
        btn_stroke7.setOnClickListener(this);
        btn_stroke8.setOnClickListener(this);
        btncolor_blue.setOnClickListener(this);
        btncolor_green.setOnClickListener(this);
        btncolor_red.setOnClickListener(this);
        btncolor_yellow.setOnClickListener(this);
        btncolor_white.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.colorbtn_blue:
                paintView.paint.setColor(Color.BLUE);
                paintView.color = Color.BLUE;
                break;
            case R.id.colorbtn_white:
                paintView.paint.setColor(Color.WHITE);
                paintView.color = Color.WHITE;
                break;
            case R.id.colorbtn_red:
                paintView.paint.setColor(Color.RED);
                paintView.color = Color.RED;
                break;
            case R.id.colorbtn_green:
                paintView.paint.setColor(Color.GREEN);
                paintView.color = Color.GREEN;
                break;
            case R.id.colorbtn_yellow:
                paintView.paint.setColor(Color.YELLOW);
                paintView.color = Color.YELLOW;
                break;
            case R.id.paint_del:
                paintView.clearCanvas();
                canvas.requestFocus();
                Tools.pressView(canvas, 100, 100);
                // Tools.backButton(canvas);
                break;
            case R.id.paint_save:
                saveDrawing();
                break;
            default:
                break;
        }
        setStrokeWidth(v);
    }

    public void setStrokeWidth(View v) {
        switch (v.getId()) {
            case R.id.paint_size_one:
                paintView.paint.setStrokeWidth(5f);
                paintView.strokeWidth = 5f;
                break;
            case R.id.paint_size_two:
                paintView.paint.setStrokeWidth(10f);
                paintView.strokeWidth = 10f;
                break;
            case R.id.paint_size_three:
                paintView.paint.setStrokeWidth(15f);
                paintView.strokeWidth = 15f;
                break;
            case R.id.paint_size_four:
                paintView.paint.setStrokeWidth(20f);
                paintView.strokeWidth = 20f;
                break;
            case R.id.paint_size_five:
                paintView.paint.setStrokeWidth(25f);
                paintView.strokeWidth = 25f;
                break;
            case R.id.paint_size_six:
                paintView.paint.setStrokeWidth(30f);
                paintView.strokeWidth = 30f;
                break;
            case R.id.paint_size_seven:
                paintView.paint.setStrokeWidth(35f);
                paintView.strokeWidth = 35f;
                break;
            case R.id.paint_size_eight:
                paintView.paint.setStrokeWidth(40f);
                paintView.strokeWidth = 40f;
                break;
            default:
                break;
        }
    }

    public void saveDrawing() {
        Bitmap bitmap = paintView.getBitmap();
        saveExternal(bitmap);
        Notes_Main_Fragment.savedDrawing = true;
        //return to other screen
        Tools.backButton(canvas);
    }

    private void saveExternal(Bitmap image) {
        File pictureFile = saveInternal();
        if (pictureFile == null) {
            Log.d("SaveImage(); ", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("SaveImage(); ", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("SaveImage(); ", "Error accessing file: " + e.getMessage());
        }
    }

    public File saveInternal() {
        ContextWrapper cw = new ContextWrapper(this);
        File mediaStorageDir = cw.getDir("", Context.MODE_PRIVATE);
        File mediaFile;
        String mImageName = Tools.getFileNameDrawing();
        Database db = new Database(this);
        db.updateDrawing(mImageName, String.valueOf(MainActivity.CURRENT_ID));
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
