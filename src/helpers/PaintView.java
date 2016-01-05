package helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class PaintView extends View implements OnTouchListener {
	private static final String TAG = "PaintView";
	public int color;
	List<ArrayList<Point>> points = new ArrayList<ArrayList<Point>>();
	public Paint paint = new Paint();
	public TextView tv;

	public PaintView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		paint.setColor(Color.BLUE);
		color = Color.BLUE;
		paint.setAntiAlias(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// for (Point point : points) {
		// canvas.drawCircle(point.x, point.y, 5, point.paint);
		// }
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.get(i).size()-1; j++) {
				paint.setColor(points.get(i).get(j).color);
				canvas.drawLine(points.get(i).get(j).x, points.get(i).get(j).y, points.get(i).get(j + 1).x,
						points.get(i).get(j + 1).y, paint);
			}
		}
	}

	public boolean onTouch(View view, MotionEvent event) {
		if (points.size() == 0) {
			points.add(new ArrayList<Point>());
		}
		 Point point = new Point();
		 point.x = event.getX();
		 point.y = event.getY();
		 point.color = paint.getColor();
		 points.get(points.size()-1).add(point);
		 invalidate();
		 Log.d(TAG, "point: " + point);
		if (event.getAction() == event.ACTION_UP) {
			points.add(new ArrayList<Point>());
		}
		return true;
	}
}

class Point {
	float x, y;
	int color;

	@Override
	public String toString() {
		return x + ", " + y;
	}
}