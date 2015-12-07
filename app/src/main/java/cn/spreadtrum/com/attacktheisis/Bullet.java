package cn.spreadtrum.com.attacktheisis;

import android.graphics.Rect;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Bullet {
    protected  int attackType; //  different attact types have different damage to the target according to different armor types;
    protected  int attackArea;
    protected Motion motion;
    protected int damage;
    protected int width;
    protected int height;
    protected BaseWeapon weapon;
    protected int trueAreaX;
    protected int trueAreaY;

    public Bullet(int attackType, int attackArea, Motion motion, int damage, BaseWeapon weapon , int width , int height){
        this.attackType = attackType;
        this.attackArea = attackArea;
        this.motion = motion;
        this.weapon = weapon;
        this.width = width;
        this.height = height;
        trueAreaX = (int)(width/2 + attackArea);
        trueAreaY = (int)(height/2 + attackArea);
    }

    public void  hit(BaseObj target){
        target.underAttack(attackType, damage);
    }
    public void flyToTarget(Coordinate co) {

    }

}
