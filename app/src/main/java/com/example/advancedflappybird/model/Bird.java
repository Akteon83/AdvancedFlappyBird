package com.example.advancedflappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.advancedflappybird.R;

public class Bird extends GameObject {
    private static final float ACCELERATION = 1.25f;

    public Bitmap getSprite() {
        return sprite;
    }

    private Bitmap sprite;
    private float speed = 0;

    public Bird(Context context, float x, float y) {
        super(x, y);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
        sprite = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
    }

    public void fly() {
        speed = -25;
    }

    public void update() {
        y += speed;
        speed += ACCELERATION;
    }
}
