package com.example.gzoomr.gzoom;

/**
 * Created by Administrator on 2016/3/5.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Floor {
    int width;
    int height;
    int x;
    int y;
    Bitmap mBitmap;
    Man man;
    boolean isWith;
    public Floor(Context context, int gameHeight, int gameWidth, Bitmap bitmap)
    {
        y=gameHeight;
        mBitmap=bitmap;
        isWith=false;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWith(Man m)
    {
        man=m;
        isWith=true;
    }
    public void setUnWith()
    {
        isWith=false;
    }
    public  void draw(Canvas canvas,RectF rectf)
    {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(x, y);
        canvas.drawBitmap(mBitmap,null,rectf,null);
        canvas.restore();
        if(isWith)
        {
            man.setY(this.y-man.getHeight()/2- this.height/2);
            man.draw(canvas);
        }
    }
    public boolean isWith()
    {
        return isWith;
    }
}
