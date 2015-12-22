package cn.spreadtrum.com.attacktheisis.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import cn.spreadtrum.com.attacktheisis.obj.BaseObj;
import cn.spreadtrum.com.attacktheisis.obj.Coordinate;
import cn.spreadtrum.com.attacktheisis.obj.EnemyJet;
import cn.spreadtrum.com.attacktheisis.obj.Jet;
import cn.spreadtrum.com.attacktheisis.obj.Motion;
import cn.spreadtrum.com.attacktheisis.obj.Settings;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Stage {

    /*
    *
    *
    *
    *
    *
    *
    * */



    private int stageCount = 0;

    private Context mContext;
    private Jet mJet ;
    private EnemyJet mEnemyJet;
    private int mScreenWidth;
    private int mScreenHeight;
    private ArrayList<BaseObj>  mObjs ;
    public Stage(int count, Context context, int width ,int height){
        stageCount = count;
        mContext = context;
        mScreenHeight = height;
        mScreenWidth = width;
        mObjs = new ArrayList<BaseObj>();
        addJet();
        addEmenyJet();
        addEmenyJet();
    }
    //add our jet to stage...
    private void addJet() {
        Coordinate coordinate = new Coordinate(mScreenWidth/2,3*mScreenHeight/4);
        Log.e(Settings.TAG, "addJet ---->x = " +mScreenWidth/2+" y = " + (3*mScreenHeight/4));
        Log.e(Settings.TAG, "addJet ---->co = "+coordinate);
        Motion motion = new Motion(Motion.MOTION_TYPE_NORMAL, coordinate,0);
        Log.e(Settings.TAG, "addJet 1---->" + motion+ "screen :w "+mScreenWidth +"Screen h:"+mScreenHeight);
        mJet = new Jet(motion,"JOE", Settings.JET_WIDTH,Settings.JET_HEIGHT,mContext,this);
        Log.e(Settings.TAG, "addJet ---->" + motion);
        synchronized (mObjs) {
            mObjs.add(mJet);
        }
    }
    private void addEmenyJet(){
        Coordinate coordinate = new Coordinate(mScreenWidth/2,mScreenHeight/4);
        Motion motion = new Motion(Motion.MOTION_TYPE_NORMAL, coordinate,0);
        Log.e(Settings.TAG, "addJet 1---->" + motion+ "screen :w "+mScreenWidth +"Screen h:"+mScreenHeight);
        EnemyJet jet = new EnemyJet(motion,"EMY-1", Settings.ENEMY_JET_WIDTH,Settings.ENEMY_JET_HEIGHT,mContext,mJet,this);
        jet.reset();
        Log.e(Settings.TAG, "addJet ---->" + motion);
        synchronized (mObjs) {
            mObjs.add(jet);
        }
    }

    public int getScreenWidth(){
        return mScreenWidth;
    }
    public int getmScreenHeight(){
        return mScreenHeight;
    }
    public void OnDraw(Canvas canvas) {
        synchronized (this) {
            for (BaseObj obj : mObjs) {
                obj.onDraw(canvas);
            }
        }
    }

    public ArrayList<BaseObj> getObjs(){
        synchronized (mObjs){
            return mObjs;
        }
    }
    public void onTouch(MotionEvent event){
        synchronized (this) {
            for (BaseObj obj : mObjs) {
                obj.onTouch(event);
            }
        }
        //return false;
    }



}
