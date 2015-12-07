package cn.spreadtrum.com.attacktheisis;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Jet extends BaseObj{

    private static int JET_HEALTH = 100;
    private static int JET_ARMOR_TYPE = Armor.ARMOR_JET;

    private static final int GUN_SHOOT_SPEED = 5; //5 bullets per sec.
    private static final int GUN_BULLET_COUNTS = 1;
    private static final int GUN_PAY_LOAD = -100; //means unlimit

    private Gun mGun;
    public Jet( Motion motion ,String name, int width, int height) {
        super(JET_HEALTH, STATUS_NORMAL, motion, JET_ARMOR_TYPE, name , width, height);
        setWeapons();
    }

     void setWeapons() {
         mGun = new Gun(this, GUN_SHOOT_SPEED, GUN_PAY_LOAD,GUN_BULLET_COUNTS);
     }
}
