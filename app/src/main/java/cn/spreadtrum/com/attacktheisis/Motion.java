package cn.spreadtrum.com.attacktheisis;

import android.graphics.Point;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Motion {
    protected Coordinate  position;
    protected int motionType;
    protected int speed;



    public  Motion(int type, Coordinate initPos, int speed){
        position = new Coordinate(initPos);
        motionType = type;
        this.speed = speed;
    }
    public void update(){

    }
    public void reset(Coordinate initPos){
        position = new Coordinate(initPos);
    }
}
