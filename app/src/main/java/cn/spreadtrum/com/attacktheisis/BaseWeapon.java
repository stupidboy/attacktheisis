package cn.spreadtrum.com.attacktheisis;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */


public class BaseWeapon {


    public static int ATTACK_TYPE_GUN = 0;
    public static int ATTACK_TYPE_BOMB = 1;
    public static int ATTACK_TYPE_MISSILE = 2;

    protected  BaseObj owner;
    protected  int shootingSpeed; // xx per sec or means cool done.
    protected  int payLoad; // total Bullet counts.
    protected  int bulletCounts; //xx bullets per fire



    public BaseWeapon(BaseObj owner, int shootingSpeed, int payLoad, int bulletCounts ){
        this.owner = owner;
        this.shootingSpeed = shootingSpeed;
        this.payLoad = payLoad;
        this.bulletCounts = bulletCounts;
    }
    public void fireTargetPosition(int x, int y){

    }
    public void fireTargetPosition(Coordinate co){
        fireTargetPosition(co.getPosX(),co.getPosY());
    }
    public void fire(){
        fireTargetPosition(owner.motion.position.getPosX(),0);
    }
}
