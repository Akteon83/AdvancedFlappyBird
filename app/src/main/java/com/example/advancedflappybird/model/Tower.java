package com.example.advancedflappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.advancedflappybird.R;
import com.example.advancedflappybird.ui.GameView;

public class Tower extends GameObject{
    public Bitmap getTop() {
        return top;
    }

    public Bitmap getBottom() {
        return bottom;
    }

    private Bitmap top;
    private Bitmap bottom;

    private static final float xSpeed = 10;
    private static final float spacerSize = 200;

    private float ySpacer;
    private final float height;
    private final float width;

    public Tower(Context context, float height, float width) {
        super(width, 0);
        this.height = height;
        this.width = width;
        top = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe_rotated);
        bottom = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe);
        generateTowers();
    }

    private void generateTowers() {
        y = random(height / 4f, height * 3 / 4f);

        top = Bitmap.createScaledBitmap(top, 200, (int) (y - spacerSize), false);
        bottom = Bitmap.createScaledBitmap(bottom, 200, (int) (height - y - spacerSize), false);
    }

    public void update() {
        x -= xSpeed;
        if (x <= bottom.getWidth()) {
            GameView.score += 1;
            x = width;
            generateTowers();
        }
    }

    public boolean isCollision(GameObject object) {
        if (x - 150 < object.x && x + bottom.getWidth() > object.x) {
            if (object.y + 100 < top.getHeight()) return true;
            return object.y + 100> height - bottom.getHeight();
        }
        return false;
    }

    private float random(float min, float max) {
        return (float) (min + (Math.random() * (max - min)));
    }
}
