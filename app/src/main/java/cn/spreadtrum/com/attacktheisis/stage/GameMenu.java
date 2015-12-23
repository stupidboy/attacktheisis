package cn.spreadtrum.com.attacktheisis.stage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import cn.spreadtrum.com.attacktheisis.R;
import cn.spreadtrum.com.attacktheisis.obj.Motion;

/**
 * Created by SPREADTRUM\joe.yu on 12/23/15.
 */
public class GameMenu {

    private Bitmap mBoard ;
    private Bitmap mLogo;
    private Bitmap mPlay;
    private Bitmap mRetry;
    private Rect mRect = new Rect();
    private Stage mStage ;

    private int startX;
    private int startY;
    private int startWidth;
    private int startHeight;
    public GameMenu(Stage stage){
        mStage = stage;
        initRes();
    }
    private void initRes(){
        mBoard = BitmapFactory.decodeResource(mStage.getContext().getResources(), R.drawable.menuboard);
        mLogo = BitmapFactory.decodeResource(mStage.getContext().getResources(),R.drawable.logo);
        mPlay = BitmapFactory.decodeResource(mStage.getContext().getResources(),R.drawable.play);
        mRetry = BitmapFactory.decodeResource(mStage.getContext().getResources(),R.drawable.menu_reset);
        //cos:
        startX = mStage.getScreenWidth()/2;
        startY = 3 * mStage.getmScreenHeight() / 4;
        startWidth = 150;
        startHeight = 150;


    }

    private void drawBitmap(Bitmap bitmap, int x,int y, int width ,int height,Canvas canvas) {
        int left = (int) (x - width / 2);
        int top = (int) (y - height / 2);
        int right = (int) (x + width / 2);
        int bottom = (int) (y + height / 2);
        mRect.set(left, top, right, bottom);
        //Log.e(Settings.TAG, "obj on draw----->" + name + " " + left + " :" + top + " :" + right + " :" + bottom);
        canvas.drawBitmap(bitmap, null, mRect, null);
    }



    public void drawOnGameInit(Canvas canvas){
        //draw logo & play:
        drawBitmap(mLogo,mStage.getScreenWidth()/2,mStage.getmScreenHeight()/4,3*mStage.getScreenWidth()/4,mStage.getmScreenHeight()/2,canvas);
        drawBitmap(mPlay, startX,startY,startWidth,startHeight, canvas);
    }

    public boolean startTouched(int x ,int y){
        int left = (int) (startX - startWidth / 2);
        int top = (int) (startY - startHeight / 2);
        int right = (int) (startX + startWidth / 2);
        int bottom = (int) (startY + startHeight / 2);
        Rect rect = new Rect(left, top, right, bottom);
        return rect.contains(x, y);
    }

    public void drawOnGameOver(Canvas canvas){
        drawBitmap(mPlay, startX,startY,startWidth,startHeight, canvas);
    }

}
