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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaintView extends View implements OnTouchListener {
    private static final String TAG = "PaintView";
    public int color;
    public static List<ArrayList<Point>> points = new ArrayList<ArrayList<Point>>();
    public Paint paint = new Paint();
    public TextView tv;
    public float strokeWidth;
    public static int index_mod = 0;

    public PaintView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.BLUE);
        color = Color.BLUE;
        strokeWidth = 5f;
        paint.setAntiAlias(true);
        points.add(new ArrayList<Point>());
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.get(i).size() - 1; j++) {
                paint.setColor(points.get(i).get(j).color);
                paint.setStrokeWidth(points.get(i).get(j).strokeWidth);
                canvas.drawLine(points.get(i).get(j).x, points.get(i).get(j).y, points.get(i).get(j + 1).x,
                        points.get(i).get(j + 1).y, paint);

                /** helps out unevenness */
                // canvas.drawLine(points.get(i).get(j).x + 1,
                // points.get(i).get(j).y + 1, points.get(i).get(j + 1).x,
                // points.get(i).get(j + 1).y, paint);
                // canvas.drawLine(points.get(i).get(j).x - 1,
                // points.get(i).get(j).y - 1, points.get(i).get(j + 1).x,
                // points.get(i).get(j + 1).y, paint);
            }
        }
    }

    public static boolean backPressed = false;
    public static boolean passed = false;
    public boolean onTouch(View view, MotionEvent event) {
        if (points.size() == 0) {
            points.add(new ArrayList<Point>());
        }
        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        point.color = color;
        point.strokeWidth = strokeWidth;
        points.get(points.size() - 1).add(point);
        invalidate();
        Log.d(TAG, "point: " + point);
        if (event.getAction() == event.ACTION_UP) {
            points.add(new ArrayList<Point>());
            Toast.makeText(getContext(), "second block", Toast.LENGTH_SHORT).show();
            passed = true;
        }
        backPressed = false;
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