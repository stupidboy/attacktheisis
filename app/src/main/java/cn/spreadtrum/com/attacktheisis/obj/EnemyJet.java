package cn.spreadtrum.com.attacktheisis.obj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;
import java.util.Set;

import cn.spreadtrum.com.attacktheisis.R;
import cn.spreadtrum.com.attacktheisis.stage.Stage;

/**
 * Created by SPREADTRUM\joe.yu on 12/22/15.
 */
public class EnemyJet extends  BaseObj {
    private static int JET_HEALTH = 100;
    private static int JET_ARMOR_TYPE = Armor.ARMOR_JET;

    private static final int GUN_SHOOT_SPEED = 1; //5 bullets per sec.
    private static final int GUN_PAY_LOAD = 100; // unlimit

    private Gun mGun;
    private Jet mTarget;
    private int speedX = Settings.ENEMY_JET_SPEED;
    private int speedY = Settings.ENEMY_JET_SPEED/2;

    public EnemyJet(Motion motion, String name, int width, int height,Context context,Jet target, Stage stage) {
        super(JET_HEALTH, STATUS_NORMAL, motion, JET_ARMOR_TYPE, name, width, height,context ,stage);
        mView = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_jet);
        Log.e(Settings.TAG, "Enemy Jet creating..... " + motion);
        setWeapons();
        mAliance = Settings.ALIANCE_IS;
        mTarget = target;
        initNormalAnim();
    }
    private void  initNormalAnim(){
        mViews = new  Bitmap[Settings.ENEMY_JET_NORMAL_ANIM_COUNTS];
        mView = null;
        Bitmap all =  BitmapFactory.decodeResource(mContext.getResources(),R.drawable.enemy2);
        for (int i = 0; i < Settings.ENEMY_JET_NORMAL_ANIM_COUNTS; i++){
            mViews[i] = Bitmap.createBitmap(all, i*Settings.ENEMY_JET_NORMAL_ANIM_WIDTH,0,Settings.ENEMY_JET_NORMAL_ANIM_WIDTH, Settings.ENEMY_JET_NORMAL_ANIM_HEIGHT);
        }

    }
    @Override
    protected void OnDestory() {
        super.OnDestory();
        getStage().updateScore(Settings.ENEMY_JET_SCORE);
        //reset();

    }

    void setWeapons() {
        mGun = new Gun(this, GUN_SHOOT_SPEED, GUN_PAY_LOAD, true,true,false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        updateMotion();
        mGun.onDraw(canvas);
        super.onDraw(canvas);
    }
    //update our pos here
    //ramdom goes anywhere
    /*
    * type 1: S style:
    *        ----->
    *        <----
    *
    * */
    @Override
    public void reset(){
        //reset to ramdom pos to start when we are fisrt add to stage or get destoryed.
        super.reset();
        health = JET_HEALTH;
        status = STATUS_NORMAL;
        Random r = new Random();
        motion.position.setPosX(r.nextInt(getStage().getScreenWidth()));
        motion.position.setPosY(-r.nextInt(getStage().getmScreenHeight() / 3));
        mNormalAnimCounts = 0;
    }
    private void updateMotion(){
        //1. out of screen, get in the stage...
        if(motion.position.getPosY() <= this.height){
            motion.setSpeed(0,Settings.ENEMY_JET_SPEED);
        }else{
            if(motion.position.getPosX() < 0){
                speedX = Settings.ENEMY_JET_SPEED;
            }
            if(motion.position.getPosX() >= mStage.getScreenWidth()){
                speedX = -Settings.ENEMY_JET_SPEED;
            }
            if(motion.position.getPosY() >= mStage.getmScreenHeight()/2){
                speedY = -Settings.ENEMY_JET_SPEED/2;
            }
            if(motion.position.getPosY() < this.height+20){
                speedY = Settings.ENEMY_JET_SPEED/2;
            }
            motion.setSpeed(speedX, speedY);
        }
        motion.update();
    }

    @Override
    protected void drawNormalAnim(Canvas canvas) {
        //super.drawNormalAnim(canvas);
        if (mNormalAnimCounts >= mViews.length) {
            mNormalAnimCounts = 0;
        }
        drawBitmap(mViews[mNormalAnimCounts++], canvas);
    }
}
