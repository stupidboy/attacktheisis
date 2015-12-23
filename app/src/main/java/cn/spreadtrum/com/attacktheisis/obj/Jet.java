package cn.spreadtrum.com.attacktheisis.obj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import cn.spreadtrum.com.attacktheisis.R;
import cn.spreadtrum.com.attacktheisis.stage.Stage;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Jet extends BaseObj {

    private static int JET_HEALTH = 100;
    private static int JET_ARMOR_TYPE = Armor.ARMOR_JET;

    private static final int GUN_SHOOT_SPEED = 2; //5 bullets per sec.
    private static final int GUN_PAY_LOAD = 100; // unlimit

    private Gun mGun;

    public Jet(Motion motion, String name, int width, int height,Context context ,Stage stage) {
        super(JET_HEALTH, STATUS_NORMAL, motion, JET_ARMOR_TYPE, name, width, height, context, stage);
        Log.e(Settings.TAG, "Jet creating..... " + motion);
        setWeapons();
        //mView = BitmapFactory.decodeResource(context.getResources(),R.drawable.jet);
        mAliance = Settings.ALIANCE_UN;
        initNormalAnim();
    }
    private void  initNormalAnim(){
        mViews = new  Bitmap[Settings.UN_JET_NORMAL_ANIM_COUNTS];
        mView = null;
        Bitmap all =  BitmapFactory.decodeResource(mContext.getResources(),R.drawable.jet2);
        for (int i = 0; i < Settings.UN_JET_NORMAL_ANIM_COUNTS; i++){
            mViews[i] = Bitmap.createBitmap(all, all.getWidth()/4,0,all.getWidth()/4, all.getHeight());
        }

    }
    private float lastX;
    private float lastY;
    private boolean touching = false ;
    @Override
    public void onTouch(MotionEvent event) {
       // Log.e(Settings.TAG, "Jet onTouch..... " + event);
        super.onTouch(event);
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:

                    if(isOnTouch((int)event.getX(),(int)event.getY())){
                    lastX = event.getX();
                    lastY = event.getY();
                    touching = true ;
                }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(touching) {
                        int deltaX = (int) (event.getX() - lastX);
                        int deltaY = (int) (event.getY() - lastY);
                        //Log.e(Settings.TAG, "Jet onTouch..... deltaX = " + deltaX + "deltaY = " + deltaY);
                        this.motion.position.moveDelta(deltaX, deltaY);
                        lastX = event.getX();
                        lastY = event.getY();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    touching = false;
                    break;
                case MotionEvent.ACTION_UP:
                    touching = false;
                    break;
            }

    }

    void setWeapons() {
        mGun = new Gun(this, GUN_SHOOT_SPEED, GUN_PAY_LOAD, true,true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mGun.onDraw(canvas);
        super.onDraw(canvas);

    }
    @Override
    protected void drawNormalAnim(Canvas canvas) {
        //super.drawNormalAnim(canvas);
        if (mNormalAnimCounts >= mViews.length) {
            mNormalAnimCounts = 0;
        }
        drawBitmap(mViews[mNormalAnimCounts++], canvas);
    }
    @Override
    protected void OnDestory() {
        super.OnDestory();
        getStage().gameOver();
    }
}
