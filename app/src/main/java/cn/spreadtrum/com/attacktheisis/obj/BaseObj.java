package cn.spreadtrum.com.attacktheisis.obj;

import android.graphics.Rect;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
/*   OBJS:
      ---->life        int
      ---->health      int
      ---->weapons
               ---->type : gun, missile,bombs etc.with different attack power and path
      ---->position
      ---->status
      ---->shield
      ---->speed
 */


public class BaseObj {

    public  static int STATUS_NORMAL = 0;
    public static int STATUS_DISTORYED = 1;
    public static int STATUS_IMMUE =2;

    protected int health;
    protected int status;
    protected int shield;
    protected Motion motion;
    protected Armor armor;
    protected BaseWeapon [] weapons;
    protected String name = "baseObj";
    protected int width;
    protected int height;

    public BaseObj(int health, int status,  Motion motion, int armorType,String name,int width, int height){
        this.health = health;
        this.status = status;
        this.motion = motion;
        this.armor = new Armor(armorType);
        this.name = name;
        this.width = width;
        this.height = height;
    }
    protected void underAttack(int attackType, int damage){
        if(status == STATUS_NORMAL) {
            int trueDamage = armor.takeDamage(attackType, damage);
            health -= trueDamage;
            if (health <= 0) {
                status = STATUS_DISTORYED;
                OnDestory();
            }
        }
    }
    protected void  OnDestory() {

    }
    protected boolean isUnderAttack(Bullet bullet) {
        int left = (int)(motion.position.getPosX() - width/2);
        int top = (int) (motion.position.getPosY() - height/2);
        int right = (int) (motion.position.getPosX() + width/2);
        int bottom = (int ) (motion.position.getPosY() + height/2);
        Rect rect = new Rect(left,top,right,bottom);
        rect.inset(-bullet.trueAreaX, -bullet.trueAreaY);
        return rect.contains(bullet.motion.position.getPosX(),bullet.motion.position.getPosY());
    }


}
