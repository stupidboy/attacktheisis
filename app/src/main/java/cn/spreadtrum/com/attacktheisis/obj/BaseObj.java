package cn.spreadtrum.com.attacktheisis.obj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import cn.spreadtrum.com.attacktheisis.R;
import cn.spreadtrum.com.attacktheisis.obj.Settings;
import cn.spreadtrum.com.attacktheisis.stage.Stage;

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

    public static int STATUS_NORMAL = 0;
    public static int STATUS_DISTORYED = 1;
    public static int STATUS_IMMUE = 2;

    protected int health;
    protected int status;
    protected int shield;
    protected Motion motion;
    protected Armor armor;
    protected BaseWeapon[] weapons;
    protected String name = "baseObj";
    protected int width;
    protected int height;
    protected Context mContext;
    protected Bitmap mView;
    protected Rect mRect = new Rect();
    protected Stage mStage;
    protected int mAliance = 0;
    protected Bitmap[] mDistoryAnim;
    private int mDestoryAnimCount = 0;
    protected int mNormalAnimCounts = 0;
    protected Bitmap[] mViews;

    public BaseObj(int health, int status, Motion motion, int armorType, String name, int width, int height, Context context, Stage stage) {
        this.health = health;
        this.status = status;
        this.motion = motion;
        this.armor = new Armor(armorType);
        this.name = name;
        this.width = width;
        this.height = height;
        this.mContext = context;
        this.mStage = stage;
        //mDistoryView = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.b)
        initDestoryAnim();
        Log.e(Settings.TAG, "obj creating... width = " + width + "height = " + height + "::" + motion);
    }

    private void initDestoryAnim() {
        Bitmap all = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion);
        mDistoryAnim = new Bitmap[Settings.DESTORY_ANIM_COUNTS];
        for (int i = 0; i < Settings.DESTORY_ANIM_COUNTS; i++) {
            if (i < 4) {
                mDistoryAnim[i] = Bitmap.createBitmap(all, i * all.getWidth()/4, 0, all.getWidth()/4, all.getHeight()/2);
            } else {
                mDistoryAnim[i] = Bitmap.createBitmap(all, (i - 4) *  all.getWidth()/4, all.getHeight()/2,  all.getWidth()/4 ,all.getHeight()/2);
            }

        }
    }

    protected void setImageView(Bitmap view) {
        mView = view;
    }

    protected void underAttack(int attackType, int damage) {
        if (status == STATUS_NORMAL) {
            int trueDamage = armor.takeDamage(attackType, damage);
            health -= trueDamage;
            Log.e(Settings.TAG, "underattack!! damge = " + damage);
            if (health <= 0) {
                status = STATUS_DISTORYED;
                OnDestory();
                //getStage().playBomb();
            }
        }
    }

    //draw destory anim & playeffect, if draw done ,get reset;
    protected void OnDestory() {
        getStage().playBomb();
    }

    //return true if draw done
    protected boolean drawDestoryDone() {
        return mDestoryAnimCount >= Settings.DESTORY_ANIM_COUNTS;
    }

    public Stage getStage() {
        return mStage;
    }

    protected boolean isUnderAttack(Bullet bullet) {
        if (bullet.weapon.owner == this || bullet.weapon.owner.mAliance == this.mAliance) {
            return false;
        }
        int left = (int) (motion.position.getPosX() - width / 2);
        int top = (int) (motion.position.getPosY() - height / 2);
        int right = (int) (motion.position.getPosX() + width / 2);
        int bottom = (int) (motion.position.getPosY() + height / 2);
        Rect rect = new Rect(left, top, right, bottom);
        //rect.inset(-bullet.attackArea, -bullet.attackArea);
        return rect.contains(bullet.motion.position.getPosX(), bullet.motion.position.getPosY());
    }

    protected boolean isOnTouch(int x, int y) {
        int left = (int) (motion.position.getPosX() - width / 2);
        int top = (int) (motion.position.getPosY() - height / 2);
        int right = (int) (motion.position.getPosX() + width / 2);
        int bottom = (int) (motion.position.getPosY() + height / 2);
        Rect rect = new Rect(left, top, right, bottom);
        return rect.contains(x, y);
    }

    public void onTouch(MotionEvent event) {

    }

    public void reset() {
        mDestoryAnimCount = 0;
    }

    protected void drawBitmap(Bitmap bitmap, Canvas canvas) {
        int left = (int) (motion.position.getPosX() - width / 2);
        int top = (int) (motion.position.getPosY() - height / 2);
        int right = (int) (motion.position.getPosX() + width / 2);
        int bottom = (int) (motion.position.getPosY() + height / 2);
        mRect.set(left, top, right, bottom);
        //Log.e(Settings.TAG, "obj on draw----->" + name + " " + left + " :" + top + " :" + right + " :" + bottom);
        canvas.drawBitmap(bitmap, null, mRect, null);
    }

    private void drawDestoryAnim(Canvas canvas) {
        if (mDestoryAnimCount < Settings.DESTORY_ANIM_COUNTS) {
            drawBitmap(mDistoryAnim[mDestoryAnimCount], canvas);
            mDestoryAnimCount++;
        } else {
            reset();
        }
    }

    protected void drawNormalAnim(Canvas canvas) {
        drawBitmap(mView, canvas);
    }

    public void onDraw(Canvas canvas) {
        synchronized (this) {
            if (status == STATUS_NORMAL) {
                drawNormalAnim(canvas);
            } else if (status == STATUS_DISTORYED) {
                drawDestoryAnim(canvas);
            }
        }

    }

}
