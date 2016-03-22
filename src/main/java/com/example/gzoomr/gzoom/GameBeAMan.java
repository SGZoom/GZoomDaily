package com.example.gzoomr.gzoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/5.
 */
public class GameBeAMan extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    /**contain three enum:WAITTING, RUNNING, STOP*/
    private enum Game_State {
        WAITTING, RUNNING, STOP;
    }

    private Game_State mGameState = Game_State.WAITTING;
    /**the main UI thread switch*/
    private boolean isrun;
    /**the main draw thread*/
    private Thread t;


    private Bitmap floor_bitmap;
    private Canvas mCanvas;
    private SurfaceHolder surfaceHolder;
    /**
     * the height and width of this game's floor
     */
    private int floor_width;
    private int floor_hight;
    /**
     * the draw rectf of the floor
     */
    private RectF floor_rectf;
    /**
     * the heigt and width of this game
     */
    private int game_height;
    private int game_width;
    /**
     * to caculate the distance floor move
     */
    private int mTmpMove;
    private int mTargetMove;
    /**
     * fly speed
     */
    private int mSpeed;
    /**my floor which needs to be draw*/
    private List<Floor> floors = new ArrayList<Floor>();
    /**the floor need to be delete,temp part*/
    private List<Floor> floors_delete = new ArrayList<Floor>();

    public GameBeAMan(Context context) {
        this(context, null);
    }

    /**
     * man
     */
    private Man man;
    private Bitmap man_bitmap;
    private int man_height;
    private int man_width;
    private RectF man_Rectf;

    /**
     * caculate there is a man o nthe floor or not
     */
    private boolean isClick;

    /**
     * caculate is touch
     */
//    private boolean isTouch;

    /**
     * tag the floor with man
     */
    private Floor floor_touch;
    /**
     * man roll down speed
     */
    private int man_down_speed;
    /**
     * draw background
     */
    private Background background;
    private Bitmap back_bitmap;
    private RectF back_rectf;

    /**tag the man run left or right or not*/
    private boolean runLeft;
    private boolean runRight;
    private int x_moveSpeed;


    /**different bitmap for man*/


    /**
     * celing
     */
   /* private Ceiling ceiling;
    private Bitmap ceiling_bitmap;*/


    /**
     * bound_value
     */
    private int top_bound;


    /**
     * wall
     */
//    private Bitmap wall_bitmap;


    /**
     * man boom the wall or not
     */
//    private boolean boomLeft;
//    private boolean boomRight;


    /**
     * start button
     */
//    private Button start_button;
//    private Bitmap button_bitmap;


    /**
     * died so not touch
     */
//    private boolean can_Touch;


    /**
     * 分数
     */
    private final int[] mNums = new int[] { R.drawable.n0, R.drawable.n1,
            R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5,
            R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9 };
    private Bitmap[] mNumBitmap;
    private int mGrade = 100 ;
    private static final float RADIO_SINGLE_NUM_HEIGHT = 1 / 15f;
    private int mSingleGradeWidth;
    private int mSingleGradeHeight;
    private RectF mSingleNumRectF;


    /**
     * draw grades
     */
    private void drawGrades(Canvas canvas)
    {
        String grade = mGrade + "";
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(0,game_height-mSingleGradeHeight);
        //draw single num one by one
        for (int i = 0; i < grade.length(); i++)
        {
            String numStr = grade.substring(i, i + 1);
            int num = Integer.valueOf(numStr);
            mCanvas.drawBitmap(mNumBitmap[num], null, mSingleNumRectF, null);
            mCanvas.translate(mSingleGradeWidth, 0);
        }
        mCanvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //isClick = true;
                switch (mGameState) {
                    case WAITTING:
                        mGameState = Game_State.RUNNING;
                        break;
                    case RUNNING:
                        //  mTmpBirdDis = mBirdUpDis;
                        break;
                    case STOP:
                        if(man.getY()>game_height){
                        mGameState= Game_State.WAITTING;
                        mGrade=0;}
                        break;
                }
                //caculate the touch place and change the man's x
                if (!isClick) {
                    if (x > game_width / 2) {
                        // man.setX(man.getX() + mSpeed);
                        runRight = true;
                      setManOri(1);
                    } else {
                        runLeft = true;
                      setManOri(-1);
//                        man.setX(man.getX() - mSpeed);
                    }

                }
                isClick = true;
                break;
            case MotionEvent.ACTION_UP:
                isClick = false;
                runRight = false;
                runLeft = false;
               setManOri(0);
                break;
        }
        return true;
    }



    /**change the man's x to left*/
    public void man_runleft() {
        if (man != null)
            man.setX(man.getX() - x_moveSpeed);
    }

    /**change the man's x to right*/
    public void man_runright() {
        if (man != null)
            man.setX(man.getX() + x_moveSpeed);
    }

    public void init_Images()
    {
        floor_bitmap = loadImageByResId(R.drawable.y_floor);
        man_bitmap = loadImageByResId(R.drawable.b1);
        back_bitmap = loadImageByResId(R.drawable.y_bg2);
    }

    public GameBeAMan(Context context, AttributeSet attrs) {
        super(context, attrs);

        init_Images();
//        floor_bitmap = loadImageByResId(R.drawable.y_floor);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//yi aishi wangl
        floor_hight = Util.dp2px(context, 20);
        floor_width = Util.dp2px(context, 100);
        ///man
        man_height = Util.dp2px(context, 40);
        man_width = Util.dp2px(context, 40);

//        man_bitmap = loadImageByResId(R.drawable.b1);

        game_height = getHeight();
        game_width = getWidth();
        mTmpMove = 0;
        mTargetMove = Util.dp2px(getContext(), 60);
        mSpeed = Util.dp2px(getContext(), 10);

        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);


        man_down_speed = Util.dp2px(context, 3);
//        back_bitmap = loadImageByResId(R.drawable.y_bg2);
        x_moveSpeed = Util.dp2px(context, 1);

        mGrade=0;
        mNumBitmap = new Bitmap[mNums.length];
        for (int i = 0; i < mNumBitmap.length; i++)
        {
            mNumBitmap[i] = loadImageByResId(mNums[i]);
        }
    }


    /**floors move up*/
    private void floor_move() {
        for (Floor f : floors) {
            f.setY(f.getY() - mSpeed);
        }
        mTmpMove += mSpeed;
    }

    /**
     * caculate need new floor and delete or not
     * and add grade
     */
    public void caculate() {
        for (Floor f : floors) {
            if (f.getY() < 0 - floor_hight) {//delete the floor out of window
                floors_delete.add(f);
                break;
            }
        }
        floors.removeAll(floors_delete);
        floors_delete.clear();
        //caculate need add floor or not
        if (mTmpMove > mTargetMove) {
            int randx = (int) (Math.random() * (game_width - floor_width));
            Floor floor = new Floor(getContext(), game_height, game_width, floor_bitmap);
            floor.setX(randx);
            floor.setY(game_height);
            floor.setHeight(floor_hight);
            floor.setWidth(floor_width);
            floors.add(floor);
            mTmpMove = 0;
            mGrade++;
        }
        //caculate the man die or not
        if (man.getY() < 0 || man.getY()> game_height) {
            this.mGameState = Game_State.STOP;
        }
    }

    private Bitmap loadImageByResId(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    /**set the man ori,-1 left ;0 center;1 right*/
    public void setManOri(int x) {
        if (man != null)
            this.man.setOri(x);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isrun = true;
        t = new Thread(this);
        //   floor=new Floor(getContext(),getHeight(),getWidth(),floor_bitmap);
        floor_rectf = new RectF(0, 0, floor_width, floor_hight);
        game_height = getHeight();
        game_width = getWidth();

        //man
        man_Rectf = new RectF(0, 0, man_width, man_height);
        man = new Man(getContext(), game_height, game_width, man_bitmap);
        man.setHeight(man_height);
        man.setWidth(man_width);

        //init man bitmap
        Bitmap temp = loadImageByResId(R.drawable.y_x);
        man.setmBitmap(temp);
        man.setBitmap_center(temp);
        man.setBitmap_left(loadImageByResId(R.drawable.y_likleft_x));
        man.setBitmap_right(loadImageByResId(R.drawable.y_likright_x));
        //  mCanvas=surfaceHolder.lockCanvas();一开始多了这一句，结果整个屏幕都是黑的


        back_rectf = new RectF(0, 0, game_width, game_height);
        background = new Background(back_bitmap, back_rectf);

        //init man palce
        man.setX(game_width / 2);
        man.setY(game_height / 2);

        t.start();
        //draw();
    }


    public void game_waitting() {
        mGameState = Game_State.WAITTING;
    }

    public void game_running() {
        mGameState = Game_State.RUNNING;
    }

    public void game_Stop() {
        mGameState = Game_State.STOP;
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化分数
        mSingleGradeHeight = (int) (h * RADIO_SINGLE_NUM_HEIGHT);
        mSingleGradeWidth = (int) (mSingleGradeHeight *1.0f / mNumBitmap[0].getHeight() * mNumBitmap[0].getWidth());
        mSingleNumRectF = new RectF(0, 0, mSingleGradeWidth, mSingleGradeHeight);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isrun = false;
    }


    @Override
    public void run() {

        while (isrun) {
            if (mGameState == Game_State.RUNNING) {
                caculate();
                draw();
                floor_move();
            } else if (mGameState == Game_State.WAITTING) {
               draw_Waitting();
            }else{
                draw_Stop();
            }
        }
    }
    private void draw_Stop() {
        //let the man flow down
        if (man.getY() <= game_height) {
            long start = System.currentTimeMillis();
            mCanvas = surfaceHolder.lockCanvas();
            if (mCanvas != null) {
                Paint mPaint = new Paint();
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//clear the canvas
                mCanvas.drawPaint(mPaint);
                background.draw(mCanvas);
                for (Floor f : floors) {
                        f.draw(mCanvas, floor_rectf);
                }
                man.trans_y(man_down_speed);
                    man.draw(mCanvas, man_Rectf);
                    if (floor_touch != null)
                        floor_touch.setUnWith();
                    man_down_speed++;

                  drawGrades(mCanvas);
                if (mCanvas != null) {
                    surfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                long end = System.currentTimeMillis();
                if (end - start < 50) {
                    try {
                        Thread.sleep(50 - end + start);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * return is will click the floor
     */
    private boolean pre_calcute_pomb(Floor f) {
        if (f.getX() <= man.getX() && f.getX() + floor_width >= man.getX() && (f.getY() - man_height <= man.getY() && f.getY() + floor_hight >= man.getY())) {
            return true;
        }
        return false;
    }

    private void draw_Waitting() {
        long start1 = System.currentTimeMillis();
        mCanvas = surfaceHolder.lockCanvas();
        if(man.getY()>game_height)
        {
            man.setY(game_height/2);
        }
        if (mCanvas != null) {
            Paint mPaint = new Paint();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(mPaint);
            floors.clear();
            background.draw(mCanvas);
           /* for (Floor f : floors) {
                f.draw(mCanvas, floor_rectf);
            }*/
            man.draw(mCanvas, man_Rectf);
            man_down_speed = 0;

            isClick = false;
            if (mCanvas != null) {
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }

            long end1 = System.currentTimeMillis();
            if (end1 - start1 < 50) {
                try {
                    Thread.sleep(50 - end1 + start1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**Runnning*/
    private void draw() {
        long start = System.currentTimeMillis();
        mCanvas = surfaceHolder.lockCanvas();
        if (mCanvas != null) {
            Paint mPaint = new Paint();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(mPaint);
            background.draw(mCanvas);
            if (runLeft) {
                man_runleft();
            }
            if (runRight) {
                man_runright();
            }
            for (Floor f : floors) {
                if (pre_calcute_pomb(f)) {//find the floor the man will on,if have
                    f.setWith(man);
                    f.draw(mCanvas, floor_rectf);
                    floor_touch = f;
                    isClick = true;
                } else {
                    f.draw(mCanvas, floor_rectf);
                }
            }
            if (!isClick) {
                man.trans_y(man_down_speed);
                man.draw(mCanvas, man_Rectf);
                if (floor_touch != null)
                    floor_touch.setUnWith();
                man_down_speed++;
            } else {
                man_down_speed = 0;
            }
            isClick = false;
            drawGrades(mCanvas);
            if (mCanvas != null) {
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }

            long end = System.currentTimeMillis();
            if (end - start < 50) {
                try {
                    Thread.sleep(50 - end + start);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
