package helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PaintView extends View implements OnTouchListener {
    private static final String TAG = "PaintView";
    public int color;
    public static List<ArrayList<Point>> points = new ArrayList<ArrayList<Point>>();
    public static List<ArrayList<Point>> points_history = new ArrayList<ArrayList<Point>>();
    public Paint paint = new Paint();
    public TextView tv;
    public float strokeWidth;

    //stroke widths
    public static final int stroke_width_one = 10;
    public static final int stroke_width_two = 15;
    public static final int stroke_width_three = 20;
    public static final int stroke_width_four = 25;
    public static final int stroke_width_five = 30;
    public static final int stroke_width_six = 35;
    public static final int stroke_width_seven = 40;
    public static final int stroke_width_eight = 45;


    public PaintView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

        paint.setColor(Color.BLUE);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setAntiAlias(true);

        color = Color.BLUE;
        strokeWidth = 5f;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.get(i).size() - 1; j++) {
                paint.setColor(points.get(i).get(j).color);
                paint.setStrokeWidth(points.get(i).get(j).strokeWidth);
                canvas.drawLine(points.get(i).get(j).x, points.get(i).get(j).y, points.get(i).get(j + 1).x,
                        points.get(i).get(j + 1).y, paint);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
//        if (points.size() == 0) {
//            points.add(new ArrayList<Point>());
//        }
        if (event.getAction() == event.ACTION_DOWN) {
            points.add(new ArrayList<Point>());
            points_history.add(new ArrayList<Point>());
        }
        Point point = null;
        if (event.getAction() == event.ACTION_MOVE) {
            point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            point.color = color;
            point.strokeWidth = strokeWidth;
            points.get(points.size() - 1).add(point);
            points_history.get(points_history.size() - 1).add(point);
            invalidate();
        }
        Log.d(TAG, "Point_HISTORY Size: " + String.valueOf(points_history.size()));
        return true;
    }

    public void clearCanvas() {
        points = new ArrayList<ArrayList<Point>>();
    }

    @SuppressLint("NewApi")
    public Bitmap getBitmap() {
        // this.measure(100, 100);
        // this.layout(0, 0, 100, 100);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        this.setBackgroundColor(Color.WHITE);
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }
}

class Point {
    float x, y;
    int color;
    float strokeWidth;

    @Override
    public String toString() {
        return x + ", " + y;
    }
}