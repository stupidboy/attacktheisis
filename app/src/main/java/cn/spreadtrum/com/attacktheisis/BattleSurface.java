package cn.spreadtrum.com.attacktheisis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cn.spreadtrum.com.attacktheisis.obj.Settings;
import cn.spreadtrum.com.attacktheisis.stage.Stage;

/**
 * Created by SPREADTRUM\joe.yu on 12/7/15.
 */
public class BattleSurface extends SurfaceView implements SurfaceHolder.Callback,Runnable{


    private String TAG = "joe";
    private Bitmap mBg ;
    private Context mContext;
    Canvas mCanvas = null;

    SurfaceHolder mHolder = null;
    boolean isRunning;
    Thread mRenderThread = null;
    RectF mPanelRect = new RectF();
    static final int STATUS_INIT = 0;

    static final int STATUS_RUNNING = 1;

    static  final int STATUS_STOP = 2;

    private int mGameStatus = 0;

    private int mPoint = 0;
    int mWidth = 480;

    int mHeight = 800;

    Stage mStage ;

    public BattleSurface(Context context) {
        super(context);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        this.setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        setFocusable(true);
        setFocusableInTouchMode(true);
        mBg = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        mContext = context;

    }

    public BattleSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BattleSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRenderThread = new Thread(this);
        isRunning = true;
        mRenderThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mPanelRect.set(0,0,w,h);
        Settings.SCREEN_HEIGHT =  mHeight;
        Settings.SCREEN_WIDTH = mWidth;
        mStage = new Stage(0,mContext,mWidth,mHeight);
        Log.e(TAG, "-----> w = "+w+" ----> h = "+h);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void  drawBg(Canvas canvas){
        canvas.drawBitmap(mBg,null,mPanelRect,null);
    }
    void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                drawBg(mCanvas);
                mStage.OnDraw(mCanvas);
            }
            mHolder.unlockCanvasAndPost(mCanvas);
        } catch (java.lang.IllegalStateException e) {
        } catch (java.lang.IllegalArgumentException e){

        }
        finally {

        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e(TAG,"OnTouch ---->x = "+event.getRawX()+"  y= "+event.getRawY());
        mStage.onTouch(event);
        return true;
    }



    @Override
    public void run() {
        while (isRunning) {
            draw();
            try {
                Thread.sleep(16);
            } catch (Exception e) {
            }
        }
    }
}
