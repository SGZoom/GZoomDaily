package com.example.gzoomr.gzoom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Administrator on 2016/3/13.
 */
public class Background {
    Bitmap bitmap;
    RectF rectf;
    int gameHeight;
    int gameWidth;
    public Background(Bitmap bitmap, RectF rectf) {
        this.bitmap = bitmap;
        this.rectf = rectf;
    }
    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public void draw(Canvas canvas)
    {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.drawBitmap(bitmap, null, rectf, null);
        canvas.restore();
    }
}
/**   if(wall!=null)
 {
 currentHeight=0;
 while(currentHeight<gameHeight)
 {
 canvas.translate(0,currentHeight);
 canvas.drawBitmap(wall, null, wall_rectf, null);
 canvas.translate(gameWidth,currentHeight);
 canvas.drawBitmap(wall,null,wall_rectf,null);
 currentHeight+=wallHeight;
 }
 }*/
/**public void setWall(Bitmap wall) {
 this.wall = wall;
 wallHeight=wall.getHeight();
 wall_rectf=new RectF(0,0,wall.getWidth(),wallHeight);
 }*/