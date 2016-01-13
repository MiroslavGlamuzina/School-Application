package activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.schoolapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import database.Database;
import helpers.PaintView;
import tools.Tools;

/**
 * Created by miroslav on 11/01/16.
 */

// complete: 11/01/16 make this activity look like the google keep drawing activity

public class Drawing_Activity extends Activity implements View.OnClickListener {
    private final String TAG = "Notes_Drawing_Fragment";
    public static Boolean isStarted = false;
    public static Boolean isVisible = false;
    //color
    ImageButton color_one, color_two, color_three, color_four, color_five, color_six, color_seven, color_eight, color_nine, color_ten, color_eleven, color_twelve, color_thirteen, color_fourteen, color_fifteen, color_sixteen, color_seventeen, color_eighteen, color_nineteen, color_twenty, color_twenty_one, color_twenty_two, color_twenty_three, color_twenty_four, color_twenty_five, color_twenty_six, color_twenty_seven, color_twenty_eight;
    //stroke size
    ImageButton btn_stroke1, btn_stroke2, btn_stroke3, btn_stroke4, btn_stroke5, btn_stroke6, btn_stroke7, btn_stroke8;
    //TOOLBAR ---- Color - Brush - Stroke size -> selectors
    LinearLayout row_one, row_two, row_three, row_four, row_five, row_six;
    ImageButton pencil, eraser;
    boolean toolbar_open_size, toolbar_open_color = false;
    int previous_selected_stroke_width = 0;
    int previous_selected_color = R.id.paint_color_five; //using actual color as switch
    //other
    Button btn_save, btn_del, back_btn, forward_btn;
    PaintView pv;
    LinearLayout canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notes_drawing);
        //paintview
        pv = new PaintView(this);
        pv.requestFocus();
        canvas = (LinearLayout) this.findViewById(R.id.canvas);
        canvas.addView(pv);
        //color
        color_one = (ImageButton) this.findViewById(R.id.paint_color_one);
        color_two = (ImageButton) this.findViewById(R.id.paint_color_two);
        color_three = (ImageButton) this.findViewById(R.id.paint_color_three);
        color_four = (ImageButton) this.findViewById(R.id.paint_color_four);
        color_five = (ImageButton) this.findViewById(R.id.paint_color_five);
        color_six = (ImageButton) this.findViewById(R.id.paint_color_six);
        color_seven = (ImageButton) this.findViewById(R.id.paint_color_seven);
        color_eight = (ImageButton) this.findViewById(R.id.paint_color_eight);
        color_nine = (ImageButton) this.findViewById(R.id.paint_color_nine);
        color_ten = (ImageButton) this.findViewById(R.id.paint_color_ten);
        color_eleven = (ImageButton) this.findViewById(R.id.paint_color_eleven);
        color_twelve = (ImageButton) this.findViewById(R.id.paint_color_twelve);
        color_thirteen = (ImageButton) this.findViewById(R.id.paint_color_thirteen);
        color_fourteen = (ImageButton) this.findViewById(R.id.paint_color_fourteen);
        color_fifteen = (ImageButton) this.findViewById(R.id.paint_color_fifteen);
        color_sixteen = (ImageButton) this.findViewById(R.id.paint_color_sixteen);
        color_seventeen = (ImageButton) this.findViewById(R.id.paint_color_seventeen);
        color_eighteen = (ImageButton) this.findViewById(R.id.paint_color_eighteen);
        color_nineteen = (ImageButton) this.findViewById(R.id.paint_color_nineteen);
        color_twenty = (ImageButton) this.findViewById(R.id.paint_color_twenty);
        color_twenty_one = (ImageButton) this.findViewById(R.id.paint_color_twenty_one);
        color_twenty_two = (ImageButton) this.findViewById(R.id.paint_color_twenty_two);
        color_twenty_three = (ImageButton) this.findViewById(R.id.paint_color_twenty_three);
        color_twenty_four = (ImageButton) this.findViewById(R.id.paint_color_twenty_four);
        color_twenty_five = (ImageButton) this.findViewById(R.id.paint_color_twenty_five);
        color_twenty_six = (ImageButton) this.findViewById(R.id.paint_color_twenty_six);
        color_twenty_seven = (ImageButton) this.findViewById(R.id.paint_color_twenty_seven);
        color_twenty_eight = (ImageButton) this.findViewById(R.id.paint_color_twenty_eight);
        //stroke width
        btn_stroke1 = (ImageButton) this.findViewById(R.id.paint_size_one);
        btn_stroke2 = (ImageButton) this.findViewById(R.id.paint_size_two);
        btn_stroke3 = (ImageButton) this.findViewById(R.id.paint_size_three);
        btn_stroke4 = (ImageButton) this.findViewById(R.id.paint_size_four);
        btn_stroke5 = (ImageButton) this.findViewById(R.id.paint_size_five);
        btn_stroke6 = (ImageButton) this.findViewById(R.id.paint_size_six);
        btn_stroke7 = (ImageButton) this.findViewById(R.id.paint_size_seven);
        btn_stroke8 = (ImageButton) this.findViewById(R.id.paint_size_eight);
        //Other
        back_btn = (Button) this.findViewById(R.id.paint_back);
        forward_btn = (Button) this.findViewById(R.id.paint_forward);
        btn_save = (Button) this.findViewById(R.id.paint_save);
        btn_del = (Button) this.findViewById(R.id.paint_del);
        //Bottom TOOLBAR (colors, stroke sizes, brush etc)
        row_one = (LinearLayout) this.findViewById(R.id.paint_row_one);
        row_two = (LinearLayout) this.findViewById(R.id.paint_row_two);
        row_three = (LinearLayout) this.findViewById(R.id.paint_row_three);
        row_four = (LinearLayout) this.findViewById(R.id.paint_row_four);
        row_five = (LinearLayout) this.findViewById(R.id.paint_row_five);
        row_six = (LinearLayout) this.findViewById(R.id.paint_row_six);
        pencil = (ImageButton) this.findViewById(R.id.paint_toolbar_pen);
        eraser = (ImageButton) this.findViewById(R.id.paint_toolbar_eraser);
        //TOOLBAR click listener
        pencil.setOnClickListener(this);
        eraser.setOnClickListener(this);
        //click listener other
        btn_save.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        forward_btn.setOnClickListener(this);
        //click listener stroke size
        btn_stroke1.setOnClickListener(this);
        btn_stroke2.setOnClickListener(this);
        btn_stroke3.setOnClickListener(this);
        btn_stroke4.setOnClickListener(this);
        btn_stroke5.setOnClickListener(this);
        btn_stroke6.setOnClickListener(this);
        btn_stroke7.setOnClickListener(this);
        btn_stroke8.setOnClickListener(this);
        //click listener color
        color_one.setOnClickListener(this);
        color_two.setOnClickListener(this);
        color_three.setOnClickListener(this);
        color_four.setOnClickListener(this);
        color_five.setOnClickListener(this);
        color_six.setOnClickListener(this);
        color_seven.setOnClickListener(this);
        color_eight.setOnClickListener(this);
        color_nine.setOnClickListener(this);
        color_ten.setOnClickListener(this);
        color_eleven.setOnClickListener(this);
        color_twelve.setOnClickListener(this);
        color_thirteen.setOnClickListener(this);
        color_fourteen.setOnClickListener(this);
        color_fifteen.setOnClickListener(this);
        color_sixteen.setOnClickListener(this);
        color_seventeen.setOnClickListener(this);
        color_eighteen.setOnClickListener(this);
        color_nineteen.setOnClickListener(this);
        color_twenty.setOnClickListener(this);
        color_twenty_one.setOnClickListener(this);
        color_twenty_two.setOnClickListener(this);
        color_twenty_three.setOnClickListener(this);
        color_twenty_four.setOnClickListener(this);
        color_twenty_five.setOnClickListener(this);
        color_twenty_six.setOnClickListener(this);
        color_twenty_seven.setOnClickListener(this);
        color_twenty_eight.setOnClickListener(this);
        //Initialize Toolbar references and supply bitmaps
        setStrokeSizeImageButton();
        setColorImageButton(PaintView.paint_color_one, R.id.paint_color_one);
        setColorImageButton(PaintView.paint_color_two, R.id.paint_color_two);
        setColorImageButton(PaintView.paint_color_three, R.id.paint_color_three);
        setColorImageButton(PaintView.paint_color_four, R.id.paint_color_four);
        setColorImageButton(PaintView.paint_color_five, R.id.paint_color_five);
        setColorImageButton(PaintView.paint_color_six, R.id.paint_color_six);
        setColorImageButton(PaintView.paint_color_seven, R.id.paint_color_seven);
        setColorImageButton(PaintView.paint_color_eight, R.id.paint_color_eight);
        setColorImageButton(PaintView.paint_color_nine, R.id.paint_color_nine);
        setColorImageButton(PaintView.paint_color_ten, R.id.paint_color_ten);
        setColorImageButton(PaintView.paint_color_eleven, R.id.paint_color_eleven);
        setColorImageButton(PaintView.paint_color_twelve, R.id.paint_color_twelve);
        setColorImageButton(PaintView.paint_color_thirteen, R.id.paint_color_thirteen);
        setColorImageButton(PaintView.paint_color_fourteen, R.id.paint_color_fourteen);
        setColorImageButton(PaintView.paint_color_fifteen, R.id.paint_color_fifteen);
        setColorImageButton(PaintView.paint_color_sixteen, R.id.paint_color_sixteen);
        setColorImageButton(PaintView.paint_color_seventeen, R.id.paint_color_seventeen);
        setColorImageButton(PaintView.paint_color_eighteen, R.id.paint_color_eighteen);
        setColorImageButton(PaintView.paint_color_nineteen, R.id.paint_color_nineteen);
        setColorImageButton(PaintView.paint_color_twenty, R.id.paint_color_twenty);
        setColorImageButton(PaintView.paint_color_twenty_one, R.id.paint_color_twenty_one);
        setColorImageButton(PaintView.paint_color_twenty_two, R.id.paint_color_twenty_two);
        setColorImageButton(PaintView.paint_color_twenty_three, R.id.paint_color_twenty_three);
        setColorImageButton(PaintView.paint_color_twenty_four, R.id.paint_color_twenty_four);
        setColorImageButton(PaintView.paint_color_twenty_five, R.id.paint_color_twenty_five);
        setColorImageButton(PaintView.paint_color_twenty_six, R.id.paint_color_twenty_six);
        setColorImageButton(PaintView.paint_color_twenty_seven, R.id.paint_color_twenty_seven);
        setColorImageButton(PaintView.paint_color_twenty_eight, R.id.paint_color_twenty_eight);


        //// DEBUG: 12/01/16
        row_one.setVisibility(View.VISIBLE);
        row_two.setVisibility(View.VISIBLE);
        row_three.setVisibility(View.VISIBLE);
        row_four.setVisibility(View.VISIBLE);
        row_five.setVisibility(View.VISIBLE);
        row_five.setVisibility(View.VISIBLE);
        openToolbar(0);
        previous_selected_stroke_width = 3;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        onClickColor(color_five);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paint_del:
                pv.clearCanvas();
                pv.invalidate();
                break;
            case R.id.paint_save:
                saveDrawing();
                break;
            default:
                break;
        }
        onClickStrokeWidth(v);
        onClickColor(v);
        onClickImageHistory(v);
        onClickToolbar(v);
    }

    public void onClickColor(View v) {
        Paint paint = new Paint();
        int width = getwidth() / 8;
        int height = (getwidth() / 8);
        int rad = pv.stroke_width_seven;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap workingBitmap = null;
        Canvas canvas = null;
        Paint mpaint = new Paint();
        ImageView size_one = null;
        Paint paint2 = null;
        switch (v.getId()) {
            case R.id.paint_color_one:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_one;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_one); //here
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_one); //here
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_one);
                pv.color = PaintView.paint_color_one;
                closeToolbar(0);
                break;
            case R.id.paint_color_two:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_two;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_two); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_two); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_two);
                pv.color = PaintView.paint_color_two;
                closeToolbar(0);
                break;
            case R.id.paint_color_three:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_three;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_three); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_three); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_three);
                pv.color = PaintView.paint_color_three;
                closeToolbar(0);
                break;
            case R.id.paint_color_four:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_four;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_four); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_four); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_four);
                pv.color = PaintView.paint_color_four;
                closeToolbar(0);
                break;
            case R.id.paint_color_five:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_five;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_five); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_five); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_five);
                pv.color = PaintView.paint_color_five;
                closeToolbar(0);
                break;
            case R.id.paint_color_six:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_six;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_six); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_six); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_six);
                pv.color = PaintView.paint_color_six;
                closeToolbar(0);
                break;
            case R.id.paint_color_seven:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_seven;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_seven); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_seven); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_seven);
                pv.color = PaintView.paint_color_seven;
                closeToolbar(0);
                break;
            case R.id.paint_color_eight:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_eight;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_eight); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_eight); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_eight);
                pv.color = PaintView.paint_color_eight;
                closeToolbar(0);
                break;
            case R.id.paint_color_nine:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_nine;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_nine); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_nine); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_nine);
                pv.color = PaintView.paint_color_nine;
                closeToolbar(0);
                break;
            case R.id.paint_color_ten:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_ten;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_ten); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_ten); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_ten);
                pv.color = PaintView.paint_color_ten;
                closeToolbar(0);
                break;
            case R.id.paint_color_eleven:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_eleven;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_eleven); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_eleven); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_eleven);
                pv.color = PaintView.paint_color_eleven;
                closeToolbar(0);
                break;
            case R.id.paint_color_twelve:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twelve;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twelve); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twelve); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twelve);
                pv.color = PaintView.paint_color_twelve;
                closeToolbar(0);
                break;
            case R.id.paint_color_thirteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_thirteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_thirteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_thirteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_thirteen);
                pv.color = PaintView.paint_color_thirteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_fourteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_fourteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_fourteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_fourteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_fourteen);
                pv.color = PaintView.paint_color_fourteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_fifteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_fifteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_fifteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_fifteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_fifteen);
                pv.color = PaintView.paint_color_fifteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_sixteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_sixteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_sixteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_sixteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_sixteen);
                pv.color = PaintView.paint_color_sixteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_seventeen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_seventeen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_seventeen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_seventeen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_seventeen);
                pv.color = PaintView.paint_color_seventeen;
                closeToolbar(0);
                break;
            case R.id.paint_color_eighteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_eighteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_eighteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_eighteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_eighteen);
                pv.color = PaintView.paint_color_eighteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_nineteen:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_nineteen;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_nineteen); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_nineteen); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_nineteen);
                pv.color = PaintView.paint_color_nineteen;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty);
                pv.color = PaintView.paint_color_twenty;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_one:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_one;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_one); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_one); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_one);
                pv.color = PaintView.paint_color_twenty_one;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_two:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_two;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_two); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_two); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_three);
                pv.color = PaintView.paint_color_twenty_three;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_three:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_three;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_three); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_three); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_three);
                pv.color = PaintView.paint_color_twenty_three;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_four:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_four;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_four); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_four); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_four);
                pv.color = PaintView.paint_color_twenty_four;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_five:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_five;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_five); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_five); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_five);
                pv.color = PaintView.paint_color_twenty_five;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_six:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_six;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_six); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_six); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_six);
                pv.color = PaintView.paint_color_twenty_six;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_seven:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_seven;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_seven); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_seven); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_seven);
                pv.color = PaintView.paint_color_twenty_seven;
                closeToolbar(0);
                break;
            case R.id.paint_color_twenty_eight:
                removePreviousColor(previous_selected_color);
                previous_selected_color = R.id.paint_color_twenty_eight;
                paint.setAntiAlias(true);
                paint.setColor(pv.paint_color_twenty_eight); //here -----------------------------------
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                size_one = (ImageView) findViewById(R.id.paint_color_twenty_eight); //here--------------
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                pv.paint.setColor(PaintView.paint_color_twenty_eight);
                pv.color = PaintView.paint_color_twenty_eight;
                closeToolbar(0);
                break;
            case R.id.paint_toolbar_eraser:
                pv.paint.setColor(PaintView.paint_color_ERASER);
                pv.color = PaintView.paint_color_ERASER;
                break;
            default:
                break;
        }
    }

    private void onClickImageHistory(View v) {
        switch (v.getId()) {
            case R.id.paint_back:
                if (pv.points.size() > 0) {
                    pv.invalidate();
                    pv.points.remove(pv.points.size() - 1);
                    pv.invalidate();
                }
                break;
            case R.id.paint_forward:
                if (pv.points_history.size() >= pv.points.size()) {
                    pv.invalidate();
                    try {
                        pv.points.add(pv.points_history.get(pv.points.size()));
                    } catch (IndexOutOfBoundsException e) {
                    }
                    pv.invalidate();
                }
                break;
            default:
                break;
        }
    }

    public void removePreviousStrokeWidth(int prev) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        int width = getwidth() / 8;
        int height = (getwidth() / 8);
        Bitmap workingBitmap = Bitmap.createBitmap(width, height, conf);
        Canvas canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);
        ImageView size_one = (ImageView) findViewById(R.id.paint_size_one);
        int rad = 0;
        switch (prev) {
            case 1:
                rad = pv.stroke_width_one;
                size_one = (ImageView) findViewById(R.id.paint_size_one);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 2:
                rad = pv.stroke_width_two;
                size_one = (ImageView) findViewById(R.id.paint_size_two);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 3:
                rad = pv.stroke_width_three;
                size_one = (ImageView) findViewById(R.id.paint_size_three);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 4:
                rad = pv.stroke_width_four;
                size_one = (ImageView) findViewById(R.id.paint_size_four);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 5:
                rad = pv.stroke_width_five;
                size_one = (ImageView) findViewById(R.id.paint_size_five);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 6:
                rad = pv.stroke_width_six;
                size_one = (ImageView) findViewById(R.id.paint_size_six);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 7:
                rad = pv.stroke_width_seven;
                size_one = (ImageView) findViewById(R.id.paint_size_seven);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
            case 8:
                rad = pv.stroke_width_eight;
                size_one = (ImageView) findViewById(R.id.paint_size_eight);
                canvas.drawCircle((width / 2), (height / 2), rad, paint);
                size_one.setAdjustViewBounds(true);
                size_one.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                break;
        }
    }

    public void onClickStrokeWidth(View v) {
        //method variables
        int rad = 0;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        int width = getwidth() / 8;
        int height = (getwidth() / 8);
        Bitmap workingBitmap = Bitmap.createBitmap(width, height, conf);
        Canvas canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);
        switch (v.getId()) {
            case R.id.paint_size_one:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 1;
                rad = pv.stroke_width_one;
                ImageView size_three_selected = (ImageView) findViewById(R.id.paint_size_one);
                Paint paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_one);
                pv.strokeWidth = (float) pv.stroke_width_one;
                closeToolbar(0);
                break;
            case R.id.paint_size_two:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 2;
                rad = pv.stroke_width_two;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_two);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_two);
                pv.strokeWidth = (float) pv.stroke_width_two;
                closeToolbar(0);
                break;
            case R.id.paint_size_three:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 3;
                rad = pv.stroke_width_three;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_three);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_three);
                pv.strokeWidth = (float) pv.stroke_width_three;
                closeToolbar(0);
                break;
            case R.id.paint_size_four:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 4;
                rad = pv.stroke_width_four;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_four);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_four);
                pv.strokeWidth = (float) pv.stroke_width_four;
                closeToolbar(0);
                break;
            case R.id.paint_size_five:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 5;
                rad = pv.stroke_width_five;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_five);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_five);
                pv.strokeWidth = (float) pv.stroke_width_five;
                closeToolbar(0);
                break;
            case R.id.paint_size_six:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 6;
                rad = pv.stroke_width_six;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_six);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_six);
                pv.strokeWidth = (float) pv.stroke_width_six;
                closeToolbar(0);
                break;
            case R.id.paint_size_seven:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 7;
                rad = pv.stroke_width_seven;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_seven);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_seven);
                pv.strokeWidth = (float) pv.stroke_width_seven;
                closeToolbar(0);
                break;
            case R.id.paint_size_eight:
                removePreviousStrokeWidth(previous_selected_stroke_width);
                previous_selected_stroke_width = 8;
                rad = pv.stroke_width_eight;
                size_three_selected = (ImageView) findViewById(R.id.paint_size_eight);
                paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setColor(Color.BLACK);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(3f);
                canvas.drawCircle((width / 2), (height / 2), rad + 10, paint2);
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle((width / 2), (height / 2), rad, paint2);
                size_three_selected.setAdjustViewBounds(true);
                size_three_selected.setImageBitmap(workingBitmap);
                workingBitmap = Bitmap.createBitmap(width, height, conf);
                canvas = new Canvas(workingBitmap);
                canvas.drawColor(Color.WHITE);
                //set color
                pv.paint.setStrokeWidth((float) pv.stroke_width_seven);
                pv.strokeWidth = (float) pv.stroke_width_eight;
                closeToolbar(0);
                break;
            default:
                break;
        }

    }

    public void onClickToolbar(View v) {
        switch (v.getId()) {
            case R.id.paint_toolbar_pen:
                if (!toolbar_open_color) {
                    openToolbar(0);
                } else {
                    closeToolbar(0);
                }
                break;
            case R.id.paint_toolbar_eraser:
                if (toolbar_open_color && !toolbar_open_size) {
                    closeToolbar(1);
                } else if (!toolbar_open_size) {
                    openToolbar(1);
                } else {
                    closeToolbar(0);
                }

                break;
        }

    }

    public void openToolbar(int type) {
        /*TYPE
        * 0 -> Full Toolbar
        * 1 -> Stroke size (size picker)
        * */
        row_one.bringToFront();
        row_two.bringToFront();
        row_three.bringToFront();
        row_four.bringToFront();
        row_five.bringToFront();
        switch (type) {
            case 0:
                toolbar_open_color = true;
                row_one.animate().alpha(1.0f).setDuration(100);
                row_two.animate().alpha(1.0f).setDuration(200);
                row_three.animate().alpha(1.0f).setDuration(300);
                row_four.animate().alpha(1.0f).setDuration(400);
                row_five.animate().alpha(1.0f).setDuration(500);
                break;
            case 1:
                toolbar_open_size = true;
                row_five.animate().alpha(1.0f).setDuration(400);
                break;
        }
    }

    public void closeToolbar(int type) {
        switch (type) {
            case 0:
                row_five.animate().alpha(0f).setDuration(500);
                row_four.animate().alpha(0f).setDuration(400);
                row_three.animate().alpha(0f).setDuration(300);
                row_two.animate().alpha(0f).setDuration(200);
                row_one.animate().alpha(0f).setDuration(100);
                canvas.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canvas.bringToFront();
                    }
                }, 500);
                toolbar_open_color = toolbar_open_size = false;
                break;
            case 1:
                row_four.animate().alpha(0f).setDuration(400);
                row_three.animate().alpha(0f).setDuration(300);
                row_two.animate().alpha(0f).setDuration(200);
                row_one.animate().alpha(0f).setDuration(100);
//                canvas.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        canvas.bringToFront();
//                    }
//
//                }, 1500);
                toolbar_open_color = false;
                toolbar_open_size = true;
                break;
        }
    }

    public void setStrokeSizeImageButton() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        int width = getwidth() / 8;
        int height = (getwidth() / 8);

        Bitmap workingBitmap = Bitmap.createBitmap(width, height, conf);
        Canvas canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        int rad = pv.stroke_width_one;
        ImageView size_one = (ImageView) findViewById(R.id.paint_size_one);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_one.setAdjustViewBounds(true);
        size_one.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_two;
        ImageView size_two = (ImageView) findViewById(R.id.paint_size_two);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_two.setAdjustViewBounds(true);
        size_two.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);


        rad = pv.stroke_width_three;
        ImageView size_three = (ImageView) findViewById(R.id.paint_size_three);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLACK);
        mpaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((width / 2), (height / 2), rad + 10, mpaint);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_three.setAdjustViewBounds(true);
        size_three.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_four;
        ImageView size_four = (ImageView) findViewById(R.id.paint_size_four);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_four.setAdjustViewBounds(true);
        size_four.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_five;
        ImageView size_five = (ImageView) findViewById(R.id.paint_size_five);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_five.setAdjustViewBounds(true);
        size_five.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_six;
        ImageView size_six = (ImageView) findViewById(R.id.paint_size_six);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_six.setAdjustViewBounds(true);
        size_six.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_seven;
        ImageView size_seven = (ImageView) findViewById(R.id.paint_size_seven);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_seven.setAdjustViewBounds(true);
        size_seven.setImageBitmap(workingBitmap);

        workingBitmap = Bitmap.createBitmap(width, height, conf);
        canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE);

        rad = pv.stroke_width_eight;
        ImageView size_eight = (ImageView) findViewById(R.id.paint_size_eight);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_eight.setAdjustViewBounds(true);
        size_eight.setImageBitmap(workingBitmap);
    }

    public void removePreviousColor(int resource) {
        switch (resource) {
            case R.id.paint_color_one:
                setColorImageButton(PaintView.paint_color_one, R.id.paint_color_one);
                break;
            case R.id.paint_color_two:
                setColorImageButton(PaintView.paint_color_two, R.id.paint_color_two);
                break;
            case R.id.paint_color_three:
                setColorImageButton(PaintView.paint_color_three, R.id.paint_color_three);
                break;
            case R.id.paint_color_four:
                setColorImageButton(PaintView.paint_color_four, R.id.paint_color_four);
                break;
            case R.id.paint_color_five:
                setColorImageButton(PaintView.paint_color_five, R.id.paint_color_five);
                break;
            case R.id.paint_color_six:
                setColorImageButton(PaintView.paint_color_six, R.id.paint_color_six);
                break;
            case R.id.paint_color_seven:
                setColorImageButton(PaintView.paint_color_seven, R.id.paint_color_seven);
                break;
            case R.id.paint_color_eight:
                setColorImageButton(PaintView.paint_color_eight, R.id.paint_color_eight);
                break;
            case R.id.paint_color_nine:
                setColorImageButton(PaintView.paint_color_nine, R.id.paint_color_nine);
                break;
            case R.id.paint_color_ten:
                setColorImageButton(PaintView.paint_color_ten, R.id.paint_color_ten);
                break;
            case R.id.paint_color_eleven:
                setColorImageButton(PaintView.paint_color_eleven, R.id.paint_color_eleven);
                break;
            case R.id.paint_color_twelve:
                setColorImageButton(PaintView.paint_color_twelve, R.id.paint_color_twelve);
                break;
            case R.id.paint_color_thirteen:
                setColorImageButton(PaintView.paint_color_thirteen, R.id.paint_color_thirteen);
                break;
            case R.id.paint_color_fourteen:
                setColorImageButton(PaintView.paint_color_fourteen, R.id.paint_color_fourteen);
                break;
            case R.id.paint_color_fifteen:
                setColorImageButton(PaintView.paint_color_fifteen, R.id.paint_color_fifteen);
                break;
            case R.id.paint_color_sixteen:
                setColorImageButton(PaintView.paint_color_sixteen, R.id.paint_color_sixteen);
                break;
            case R.id.paint_color_seventeen:
                setColorImageButton(PaintView.paint_color_seventeen, R.id.paint_color_seventeen);
                break;
            case R.id.paint_color_eighteen:
                setColorImageButton(PaintView.paint_color_eighteen, R.id.paint_color_eighteen);
                break;
            case R.id.paint_color_nineteen:
                setColorImageButton(PaintView.paint_color_nineteen, R.id.paint_color_nineteen);
                break;
            case R.id.paint_color_twenty:
                setColorImageButton(PaintView.paint_color_twenty, R.id.paint_color_twenty);
                break;
            case R.id.paint_color_twenty_one:
                setColorImageButton(PaintView.paint_color_twenty_one, R.id.paint_color_twenty_one);
                break;
            case R.id.paint_color_twenty_two:
                setColorImageButton(PaintView.paint_color_twenty_two, R.id.paint_color_twenty_two);
                break;
            case R.id.paint_color_twenty_three:
                setColorImageButton(PaintView.paint_color_twenty_three, R.id.paint_color_twenty_three);
                break;
            case R.id.paint_color_twenty_four:
                setColorImageButton(PaintView.paint_color_twenty_four, R.id.paint_color_twenty_four);
                break;
            case R.id.paint_color_twenty_five:
                setColorImageButton(PaintView.paint_color_twenty_five, R.id.paint_color_twenty_five);
                break;
            case R.id.paint_color_twenty_six:
                setColorImageButton(PaintView.paint_color_twenty_six, R.id.paint_color_twenty_six);
                break;
            case R.id.paint_color_twenty_seven:
                setColorImageButton(PaintView.paint_color_twenty_seven, R.id.paint_color_twenty_seven);
                break;
            case R.id.paint_color_twenty_eight:
                setColorImageButton(PaintView.paint_color_twenty_eight, R.id.paint_color_twenty_eight);
                break;
            default:
                break;
        }

    }

    public void setColorImageButton(int color, int resource) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        int width = getwidth() / 8;
        int height = (getwidth() / 8);

        Bitmap workingBitmap = Bitmap.createBitmap(width, height, conf);
        Canvas canvas = new Canvas(workingBitmap);
        canvas.drawColor(Color.WHITE); //background

        int rad = pv.stroke_width_seven;
        ImageView size_one = (ImageView) findViewById(resource);
        canvas.drawCircle((width / 2), (height / 2), rad, paint);
        size_one.setAdjustViewBounds(true);
        size_one.setImageBitmap(workingBitmap);
    }

    public int getwidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void saveDrawing() {
        Bitmap bitmap = pv.getBitmap();
        saveExternal(bitmap);
        Notes_Activity.savedDrawing = true;
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
            Notes_Activity.drawing_activity_file_temp = pictureFile;
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

    @Override
    public void onBackPressed() {
        if (toolbar_open_color) {
            closeToolbar(0);
        } else if (toolbar_open_size) {
            closeToolbar(1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
