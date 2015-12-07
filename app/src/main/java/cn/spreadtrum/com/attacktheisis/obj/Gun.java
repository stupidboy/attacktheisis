package cn.spreadtrum.com.attacktheisis.obj;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Gun extends BaseWeapon{



    private static final int BULLET_AREA = 5;
    private static final int BULLET_WIDTH = 4;
    private static final int BULLET_HEIGHT = 4;
    private static final int BULLET_DAMAGE = 10;
    private static final int BULLET_SPEED = 500;
    Motion mBulletMotion;
    public Gun(BaseObj owner, int shootingSpeed, int payLoad, boolean unlimited) {
        super(owner, shootingSpeed, payLoad, unlimited);
        initBullets();

    }
    private void initBullets(){

        mBulletMotion = new Motion(Motion.MOTION_TYPE_NORMAL,this.owner.motion.position,BULLET_SPEED); //bullest use the owner's pos as default.
        mBullets = new GunBullet[shootingSpeed];
        for (GunBullet bullet : (GunBullet[])mBullets){
            bullet = new GunBullet(mBulletMotion);
        }
        initFireControl(mBullets);
    }
    class GunBullet extends Bullet{
        public GunBullet(Motion motion){
            super(AttackType.TYPE_GUN, BULLET_AREA, motion, BULLET_DAMAGE, Gun.this, BULLET_WIDTH, BULLET_HEIGHT);
        }

    }
}
