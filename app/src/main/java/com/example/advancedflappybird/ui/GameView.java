package com.example.advancedflappybird.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.advancedflappybird.R;
import com.example.advancedflappybird.model.Bird;
import com.example.advancedflappybird.model.Tower;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final int FPS = 120;
    public static int score;
    private Bitmap background;
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;
    private Bird bird;
    private Tower tower;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        drawThread = new DrawThread();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        score = 0;
        surfaceHolder = holder;
        init();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        bird = new Bird(getContext(), 200, getHeight() / 2f);

        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void drawFrames(Canvas canvas) {
        Rect backgroundRect = new Rect(0, 0, getWidth(), getHeight());
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawBitmap(background, null, backgroundRect, null);
        canvas.drawBitmap(bird.getSprite(), bird.x, bird.y, null);
        canvas.drawBitmap(tower.getTop(), tower.x, 0, null);
        canvas.drawBitmap(tower.getBottom(), tower.x, getHeight() - tower.getBottom().getHeight(), null);
        canvas.drawText(Integer.toString(score), 100, 100, paint);
    }

    private void init() {
        bird = new Bird(getContext(), 200, getHeight() / 2f);
        tower = new Tower(getContext(), getHeight(), getWidth());
    }

    public void update(){
        bird.update();
        tower.update();
        if (tower.isCollision(bird) || bird.y <= 0 || bird.y >= getHeight()) {
            score = 0;
            drawThread.running = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird.fly();
        if (!drawThread.running) {
            drawThread = new DrawThread();
            init();
            drawThread.start();
        }
        return super.onTouchEvent(event);
    }

    private class DrawThread extends Thread {
        private volatile boolean running = true;

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                try {
                    Thread.sleep(1000/FPS);
                    drawFrames(canvas);
                    update();
                } catch (Exception e){
                    Log.d("TLOU", "run: ", e);
                }
                finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
