package com.example.laboratorium5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Thread drawThread;
    private boolean isDrawing = false;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private List<Line> lines = new ArrayList<>();
    private Line currentLine;
    private int currentColor = 0xFFFF0000; // Default Red

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(currentColor);
        paint.setStrokeWidth(5);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        drawThread = new Thread(this);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentLine = new Line();
                currentLine.setColor(currentColor);
                currentLine.addPoint(event.getX(), event.getY());
                lines.add(currentLine);
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentLine != null) {
                    currentLine.addPoint(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentLine != null) {
                    currentLine.addPoint(event.getX(), event.getY());
                    currentLine = null;
                }
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (isDrawing) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(0xFFFFFFFF); // White background
            for (Line line : lines) {
                paint.setColor(line.getColor());
                List<Point> points = line.getPoints();
                for (int i = 1; i < points.size(); i++) {
                    float startX = points.get(i - 1).x;
                    float startY = points.get(i - 1).y;
                    float endX = points.get(i).x;
                    float endY = points.get(i).y;
                    canvas.drawLine(startX, startY, endX, endY, paint);

                    if (i == 1) {
                        // Draw a larger circle at the start of the line
                        canvas.drawCircle(startX, startY, 10, paint);
                    }
                    if (i == points.size() - 1) {
                        // Draw a larger circle at the end of the line
                        canvas.drawCircle(endX, endY, 10, paint);
                    }
                }
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void setColor(int color) {
        currentColor = color;
    }

    public void clear() {
        lines.clear();
    }

    private class Line {
        private List<Point> points = new ArrayList<>();
        private int color;

        public void addPoint(float x, float y) {
            points.add(new Point(x, y));
        }

        public List<Point> getPoints() {
            return points;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    private class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
