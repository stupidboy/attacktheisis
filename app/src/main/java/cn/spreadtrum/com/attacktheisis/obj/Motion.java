package cn.spreadtrum.com.attacktheisis.obj;

import cn.spreadtrum.com.attacktheisis.obj.Coordinate;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Motion {

    public static final int MOTION_TYPE_NORMAL = 0;
    public static final int MOTION_TYPE_ACC = 1;
    protected Coordinate position;
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
