package cn.spreadtrum.com.attacktheisis;

import android.graphics.Rect;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Gun extends BaseWeapon{



    private static final int BULLET_AREA = 5;
    private static final int BULLET_WIDTH = 4;
    private static final int BULLET_HEIGHT = 4;

    public Gun(BaseObj owner, int shootingSpeed, int payLoad, int bulletCounts) {
        super(owner, shootingSpeed, payLoad, bulletCounts);
    }
    private void initBullets(){

    }
    class GunBullet extends Bullet{
        public GunBullet(Motion motion, int damage, BaseWeapon weapon){
            super(AttackType.TYPE_GUN, BULLET_AREA, motion, damage, weapon, BULLET_WIDTH, BULLET_HEIGHT);
        }

    }
}
