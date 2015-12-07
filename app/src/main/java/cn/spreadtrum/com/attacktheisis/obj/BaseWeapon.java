package cn.spreadtrum.com.attacktheisis.obj;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */


public class BaseWeapon {


    public static int ATTACK_TYPE_GUN = 0;
    public static int ATTACK_TYPE_BOMB = 1;
    public static int ATTACK_TYPE_MISSILE = 2;

    protected  BaseObj owner;
    protected  int shootingSpeed; // x bullets shoot per sec
    protected  int payLoad; // total Bullet counts.
    private Object mPayloadLock = new Object();
    protected  boolean unlimited = false;
    private FireQueue<Bullet> mQueue;
    protected  Bullet [] mBullets;


    public BaseWeapon(BaseObj owner, int shootingSpeed, int payLoad, boolean unlimited){
        this.owner = owner;
        this.shootingSpeed = shootingSpeed;
        this.payLoad = payLoad;
        this.unlimited = unlimited;
    }
    public void initFireControl(Bullet[] bullets) {
        mQueue = new FireQueue<Bullet>(bullets.length);
        for(Bullet bullet : bullets){
            mQueue.enqueue(bullet);
        }
    }
    public int fireTargetPosition(int x, int y){
        synchronized (mPayloadLock) {
            if (payLoad <= 0) {
                return ERROR.ERROR_OUT_OF_BULLET;
            }
            if (!unlimited) payLoad--;

            return ERROR.ERROR_OK;
        }
    }
    public int fireTargetPosition(Coordinate co){
        return fireTargetPosition(co.getPosX(),co.getPosY());
    }
    public int fire(){
        return fireTargetPosition(owner.motion.position.getPosX(),0);
    }
}
