package cn.spreadtrum.com.attacktheisis.obj;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import cn.spreadtrum.com.attacktheisis.R;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Gun extends BaseWeapon {


    private static final int BULLET_AREA = -10;
    private static final int BULLET_WIDTH = 15;
    private static final int BULLET_HEIGHT = 30;
    private static final int BULLET_DAMAGE = 100;
    private static final int BULLET_SPEED = 20;

    Motion mBulletMotion;
    boolean mAutoFire = false;
    boolean mShotUp = true;
    public Gun(BaseObj owner, int shootingSpeed, int payLoad, boolean unlimited, boolean autoFire) {
        super(owner, shootingSpeed, payLoad, unlimited);
        mAutoFire = autoFire;
        mShotUp = true;
        initBullets();


    }
    public Gun(BaseObj owner, int shootingSpeed, int payLoad, boolean unlimited, boolean autoFire, boolean shootUp) {
        super(owner, shootingSpeed, payLoad, unlimited);
        mAutoFire = autoFire;
        mShotUp = shootUp;
        initBullets();

    }
    private void initBullets() {
        mBulletMotion = new Motion(Motion.MOTION_TYPE_NORMAL, this.owner.motion.position, owner.getStage().getmScreenHeight()/60); //bullest use the owner's pos as default.
        mBullets = new GunBullet[shootingSpeed];
        for (int i = 0; i < shootingSpeed; i++) {
            GunBullet bullet = new GunBullet(mBulletMotion);
            mBullets[i] = bullet;
        }
        initFireControl(mBullets);
    }

    @Override
    public void onDraw(Canvas can) {
        super.onDraw(can);
        if(mAutoFire && !owner.motion.position.outOfScreen()){

            if(mShotUp)
            this.fire();
            else{
                this.fireDown();
            }
        }

    }

    class GunBullet extends Bullet {
        public GunBullet(Motion motion) {
            super(AttackType.TYPE_GUN, BULLET_AREA, motion, BULLET_DAMAGE, Gun.this, BULLET_WIDTH, BULLET_HEIGHT);
            if(mShotUp){
            mBitmap = BitmapFactory.decodeResource(owner.mContext.getResources(),R.drawable.bulet);}
            else {
                mBitmap = BitmapFactory.decodeResource(owner.mContext.getResources(),R.drawable.bulet_down);
            }
        }

    }
}
