package cn.spreadtrum.com.attacktheisis.obj;

import android.content.Context;
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
    public EnemyJet(Motion motion, String name, int width, int height,Context context,Jet target, Stage stage) {
        super(JET_HEALTH, STATUS_NORMAL, motion, JET_ARMOR_TYPE, name, width, height,context ,stage);
        mView = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_jet);
        Log.e(Settings.TAG, "Enemy Jet creating..... " + motion);
        setWeapons();

        mTarget = target;
    }

    @Override
    protected void OnDestory() {
        super.OnDestory();
        reset();

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
    public void reset(){
        //reset to ramdom pos to start when we are fisrt add to stage or get destoryed.

        health = JET_HEALTH;
        status = STATUS_NORMAL;
        Random r = new Random();
        motion.position.setPosX(r.nextInt(getStage().getScreenWidth()));
        motion.position.setPosY(-r.nextInt(getStage().getmScreenHeight()/3));
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
            motion.setSpeed(speedX,0);
        }
        motion.update();
    }
}