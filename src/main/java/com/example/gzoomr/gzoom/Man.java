package com.example.gzoomr.gzoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Administrator on 2016/3/8.
 */
public class Man {
   private int width;
    private int height;
    private int x;
    private int y;

    private  int gameWidth;
    private  Bitmap mBitmap;
    private RectF rectf;
    private Bitmap bitmap_center;
    private Bitmap bitmap_left;
    private  Bitmap bitmap_right;
    private  int ori;
    private  boolean boom_left;
    private  boolean boom_right;
    private  int boom_time;

    /**
     * set the ori,-1left 0center 1right
     */
    public void setOri(int x) {
        ori = x;
        if (x == -1) {
            mBitmap = bitmap_left;
        } else if (x == 1) {
            mBitmap = bitmap_right;
        } else {
            mBitmap = bitmap_center;
        }
    }

    public void setBoom_left(boolean boom_left) {
        this.boom_left = boom_left;
    }

    public void setBoom_right(boolean boom_right) {
        this.boom_right = boom_right;
    }

    public Man(Context context, int gameHeight, int gameWidth, Bitmap bitmap) {
        y = gameHeight / 2;
        x = 20;
        mBitmap = bitmap;
        this.gameWidth = gameWidth;
        ori = 0;
        boom_left = false;
        boom_right=false;
        boom_time=5;
    }

    public void trans_y(int x)
    {
        this.y+=x;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public void setRectf(RectF rectf) {
        this.rectf = rectf;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public void setBitmap_center(Bitmap bitmap_center) {
        this.bitmap_center = bitmap_center;
    }

    public void setBitmap_left(Bitmap bitmap_left) {
        this.bitmap_left = bitmap_left;
    }

    public void setBitmap_right(Bitmap bitmap_right) {
        this.bitmap_right = bitmap_right;
    }

    public void setBoom_time(int boom_time) {
        this.boom_time = boom_time;
    }

    public Bitmap getBitmap_center() {
        return bitmap_center;
    }

    public Bitmap getBitmap_left() {
        return bitmap_left;
    }

    public Bitmap getBitmap_right() {
        return bitmap_right;
    }

    public int getOri() {
        return ori;
    }

    public boolean isBoom_left() {
        return boom_left;
    }

    public boolean isBoom_right() {
        return boom_right;
    }

    public int getBoom_time() {
        return boom_time;
    }

    public RectF getRectf() {
        return rectf;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public int getGameWidth() {
        return gameWidth;
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

    //实际调用的是这个
    public void draw(Canvas canvas, RectF rectf) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        if (this.x>0&&this.x<gameWidth-width) {

        }else if(this.x<=0)
        {
            this.x=0;
        }else{
            this.x=gameWidth-width;
        }
        canvas.translate(x,y);
        canvas.drawBitmap(mBitmap, null, rectf, null);
        canvas.restore();

        this.rectf = rectf;
    }

    public void draw(Canvas canvas) {

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        if (this.x>0&&this.x<gameWidth-width) {

        }else if(this.x<=0)
        {
            this.x=0;
        }else{
            this.x=gameWidth-width;
        }
        canvas.translate(x, y);
        canvas.drawBitmap(mBitmap, null, rectf, null);
        canvas.restore();
        boom_left=false;
        boom_right=false;
        boom_time=5;

    }


}
