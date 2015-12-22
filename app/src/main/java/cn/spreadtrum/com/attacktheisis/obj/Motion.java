package cn.spreadtrum.com.attacktheisis.obj;

import android.util.Log;

import java.util.Set;

import cn.spreadtrum.com.attacktheisis.obj.Coordinate;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Motion {

    public static final int MOTION_TYPE_NORMAL = 0;
    public static final int MOTION_TYPE_ACC = 1;
    public static final int MOTION_TYPE_S = 2;
    protected Coordinate position =null;
    protected int motionType;
    protected int speed;
    protected int speedX;
    protected int speedY;


    public Motion(int type, Coordinate initPos, int speed) {
        position = new Coordinate(initPos.getPosX(),initPos.getPosY());
        motionType = type;
        this.speed = speed;
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }
    public void update(){
        position.moveDelta(speedX,speedY);
        //Log.e(Settings.TAG, "update.... --->spx =" + speedX + " spy =" + speedY + " x = " + position.getPosX() + " y = " + position.getPosY());
    }


    public void reset(Coordinate initPos) {
        position = new Coordinate(initPos);
    }

    @Override
    public String toString() {
        return "Motion:type "+motionType+" co:"+position;
    }
}
