package cn.spreadtrum.com.attacktheisis.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import cn.spreadtrum.com.attacktheisis.R;
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
    static  final  int GAME_STATUS_INIT = 0;
    static  final int GAME_STATUS_RUNNING =1 ;
    static  final int GAME_STATUS_OVER=2;


    private int stageCount = 0;

    private Context mContext;
    private Jet mJet ;
    private EnemyJet mEnemyJet;
    private int mScreenWidth;
    private int mScreenHeight;
    private ArrayList<BaseObj>  mObjs ;
    private int mScore = 0;
    private Paint mPaint;

    private int mGameStatus = 0;
//audio
    private int mAudioBomb = 0;
    private int mAudioGameOver =0 ;
    private int mAudioBg = 0;
    private int mAudioshot = 0;
    private SoundPool mSoundPool;
    private GameMenu mGameMenu ;
    public Stage(int count, Context context, int width ,int height){
        mPaint = new Paint();
        mPaint.setTextSize(32);
        mPaint.setColor(Color.WHITE);
        stageCount = count;
        mContext = context;
        mScreenHeight = height;
        mScreenWidth = width;
        mObjs = new ArrayList<BaseObj>();
        mGameMenu = new GameMenu(this);
       // addJet();
        //addEmenyJet();
        //addEmenyJet();
        initAudio();
        playBg();

    }

    private void  initAudio(){
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,5);
        mAudioBomb = mSoundPool.load(mContext, R.raw.bomb,1);
        mAudioGameOver = mSoundPool.load(mContext, R.raw.gameover,1);
        mAudioBg = mSoundPool.load(mContext, R.raw.music,1);
        mAudioshot = mSoundPool.load(mContext, R.raw.shot,1);
    }
    public void playBg(){
        mSoundPool.play(mAudioBg,1,1,0,1,1);
    }
    public void playBomb(){
        mSoundPool.play(mAudioBomb,1,1,0,0,1);
    }
    public  void playGameOver(){
        mSoundPool.play(mAudioBg,1,1,0,0,1);
    }
    public  void playShot(){
        mSoundPool.play(mAudioshot, 1, 1, 0, 0, 1);
    }
    //add our jet to stage...
    private void addJet() {
        Coordinate coordinate = new Coordinate(mScreenWidth/2,3*mScreenHeight/4);
        Log.e(Settings.TAG, "addJet ---->x = " +mScreenWidth/2+" y = " + (3*mScreenHeight/4));
        Log.e(Settings.TAG, "addJet ---->co = "+coordinate);
        Motion motion = new Motion(Motion.MOTION_TYPE_NORMAL, coordinate,0);
        Log.e(Settings.TAG, "addJet 1---->" + motion + "screen :w " + mScreenWidth + "Screen h:" + mScreenHeight);
        mJet = new Jet(motion,"JOE", mScreenWidth/5,mScreenWidth/5,mContext,this);
        Log.e(Settings.TAG, "addJet ---->" + motion);
        synchronized (mObjs) {
            mObjs.add(mJet);
        }
    }
    private void addEmenyJet(){
        Coordinate coordinate = new Coordinate(mScreenWidth/2,mScreenHeight/4);
        Motion motion = new Motion(Motion.MOTION_TYPE_NORMAL, coordinate,0);
        Log.e(Settings.TAG, "addJet 1---->" + motion + "screen :w " + mScreenWidth + "Screen h:" + mScreenHeight);
        EnemyJet jet = new EnemyJet(motion,"EMY-1", mScreenWidth/5,mScreenWidth/5,mContext,mJet,this);
        jet.reset();
        Log.e(Settings.TAG, "addJet ---->" + motion);
        synchronized (mObjs) {
            mObjs.add(jet);
        }
    }
    private void drawScore(Canvas canvas){
        mPaint.setTextSize(40);
        canvas.drawText("SCORE:" + mScore, 0, getmScreenHeight() - 40, mPaint);

    }
    public int getScreenWidth(){
        return mScreenWidth;
    }
    public int getmScreenHeight(){
        return mScreenHeight;
    }
    public Context getContext(){
        return mContext;
    }
    public void OnDraw(Canvas canvas) {
        synchronized (this) {
            switch (mGameStatus){

                case GAME_STATUS_INIT:
                    //draw wellcom:
                    drawInitGame(canvas);
                    break;
                case GAME_STATUS_RUNNING:
                    drawScore(canvas);
                    for (BaseObj obj : mObjs) {
                        obj.onDraw(canvas);
                    }
                    break;
                case GAME_STATUS_OVER:
                    //draw over:
                    drawGameOver(canvas);
                    break;
            }



        }
    }
    public  void updateScore(int delta){
        synchronized (this) {
            mScore += delta;
        }
    }
    public ArrayList<BaseObj> getObjs(){
        synchronized (mObjs){
            return mObjs;
        }
    }
    public void onTouch(MotionEvent event){
        Log.e(Settings.TAG,"Motion ---->"+event);
        synchronized (this) {
            switch (mGameStatus) {
                case GAME_STATUS_INIT:
                    if(event.getAction() == MotionEvent.ACTION_DOWN && mGameMenu.startTouched((int)event.getX(),(int)event.getY())) {
                        startGame();
                    }
                    break;
                case GAME_STATUS_RUNNING:
                    for (BaseObj obj : mObjs) {
                        obj.onTouch(event);
                    }
                    break;
                case GAME_STATUS_OVER:
                    if(event.getAction() == MotionEvent.ACTION_DOWN && mGameMenu.startTouched((int)event.getX(),(int)event.getY()))
                    startGame();
                    break;
            }
        }
        //return false;
    }
    private void startGame(){
        mScore = 0;
        synchronized (mObjs) {
            mObjs.clear();
        }
        addJet();
        addEmenyJet();
        addEmenyJet();
        addEmenyJet();
        mGameStatus = GAME_STATUS_RUNNING;
    }
    private void drawInitGame(Canvas canvas){
        //mPaint.setTextSize(50);
        //canvas.drawText("Touch to start",30,getmScreenHeight()/2,mPaint);
        mGameMenu.drawOnGameInit(canvas);
    }
    public void gameOver(){
        mGameStatus = GAME_STATUS_OVER;
    }
    private void drawGameOver(Canvas canvas) {
        mPaint.setTextSize(70);
        canvas.drawText("Score " + mScore, mScreenWidth / 2 - 100, getmScreenHeight() / 3, mPaint);
        mGameMenu.drawOnGameOver(canvas);
    }


}
