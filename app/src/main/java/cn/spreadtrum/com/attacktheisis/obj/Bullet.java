package cn.spreadtrum.com.attacktheisis.obj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Bullet {
    static final int STATUS_NORMAL = 0;
    static final int STATUS_HIT =1;
    static final int STATUS_HIDDEN = 2;
    static final int STATUS_FLYING =3;
    protected Rect mRect = new Rect();

    protected int attackType; //  different attact types have different damage to the target according to different armor types;
    protected int attackArea;
    protected Motion motion;
    protected int mdamage;
    protected int width;
    protected int height;
    protected BaseWeapon weapon;
    protected int trueAreaX;
    protected int trueAreaY;
    protected FireQueue<Bullet> mQueue;
    protected  int mStatus;
    protected  Bitmap mBitmap = null;

    public Bullet(int attackType, int attackArea, Motion motion, int damage, BaseWeapon weapon, int width, int height) {
        this.attackType = attackType;
        this.attackArea = attackArea;
        this.motion = motion;
        this.weapon = weapon;
        this.width = width;
        this.height = height;
        trueAreaX = (int) (width / 2 + attackArea);
        trueAreaY = (int) (height / 2 + attackArea);
        mStatus = STATUS_HIDDEN;
        mdamage = damage;
    }
    public void setFireQueue( FireQueue<Bullet> queue) {
        mQueue = queue;
    }
    public void hit(BaseObj target) {
        target.underAttack(attackType, mdamage);
        mStatus = STATUS_HIT;
        OnHit();
    }
    //play some effect...
    protected void OnHit(){

    }
    public void flyToTarget(Coordinate co) {
     // we use linear at first, so
        int speedX = 0;
        int speedY = 0;
        speedX = co.getPosX() >this.weapon.owner.motion.position.getPosX() ? this.motion.speed: -this.motion.speed;
        speedY = co.getPosY() > this.weapon.owner.motion.position.getPosY() ? this.motion.speed: -this.motion.speed;
        if(co.getPosX() == this.weapon.owner.motion.position.getPosX()){
            //fire towards ,speed x = 0;
            speedX = 0;
        }
        if(co.getPosY() == this.weapon.owner.motion.position.getPosY()){
            speedY = 0;
        }
        this.motion.setSpeed(speedX,speedY);
        // Log.e(Settings.TAG, "current POS --->speed" + speedX+":"+speedY+"------->target co "+co+"current co "+motion.position);
        mStatus = STATUS_FLYING;
        //Log.e(Settings.TAG, "flyToTarget -->" + co);
        // Log.e(Settings.TAG,"current POS --->"+this.motion);

    }
    private void  reset(){
        this.motion.position.setPosX(this.weapon.owner.motion.position.getPosX());
        this.motion.position.setPosY(this.weapon.owner.motion.position.getPosY());
        this.mStatus = STATUS_HIDDEN;
    }
    public void checkIfRest(){
        //Log.e(Settings.TAG,"checkIfRest ---->mStatus = "+mStatus + " out ofscreen ?"+motion.position.outOfScreen());
        if(mStatus == STATUS_HIT || motion.position.outOfScreen()) {
          //  Log.e(Settings.TAG,"checkIfRest ---->");
            this.reset();
            weapon.reFillBullet(this);
        }
    }
    private void  mayHit(){
        ArrayList<BaseObj> objs = weapon.owner.getStage().getObjs();
        for(BaseObj obj : objs){
            if(obj.isUnderAttack(this)){
                hit(obj);
            }
        }
    }


    public void onDraw(Canvas canvas){
        if(mStatus == STATUS_FLYING){
            this.motion.update();
            //Log.e(Settings.TAG, "flyToTarget -->" + this.motion + "jets pos ---->" + this.weapon.owner.motion);
            int left = (int) (motion.position.getPosX() - width / 2);
            int top = (int) (motion.position.getPosY() - height / 2);
            int right = (int) (motion.position.getPosX() + width / 2);
            int bottom = (int) (motion.position.getPosY() + height / 2);
            mRect.set(left, top, right, bottom);
            canvas.drawBitmap(mBitmap, null, mRect, null);
            mayHit();
            //canvas.drawtext("hehe",);
        }
        checkIfRest();
    }
}
