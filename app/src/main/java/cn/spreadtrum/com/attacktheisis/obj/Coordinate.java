package cn.spreadtrum.com.attacktheisis.obj;

import android.util.Log;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Coordinate {
    private int posX;
    private int posY;

    public Coordinate(int x, int y) {
        posX = x;
        posY = y;
    }

    public int getPosX() {
        synchronized (this) {
            return posX;
        }
    }

    public int getPosY() {
        synchronized (this) {
            return posY;
        }
    }

    public void setPosX(int posX) {
        synchronized (this) {
            this.posX = posX;
        }
    }

    public void setPosY(int posY) {
        synchronized (this) {
            this.posY = posY;
        }
    }
    public void moveDelta(int deltaX, int deltaY){
        synchronized(this) {
            this.posX += deltaX;
            this.posY += deltaY;
           // Log.e(Settings.TAG, "moveDelat----> PosX = " + posX + "PosY = " + posY + " deltax = " + deltaX + "delta y =" + deltaY);
        }
    }

    public Coordinate(Coordinate from) {
        this.posX = Integer.valueOf(from.getPosX())  ;
        this.posY = Integer.valueOf(from.getPosY());
    }
    public boolean outOfScreen(){
        synchronized (this) {
            return (posX <= 0 || posX >= Settings.SCREEN_WIDTH || posY >= Settings.SCREEN_HEIGHT || posY <= 0);
        }
    }

    @Override
    public String toString() {
        return "x= "+posX+" y = "+posY;
    }
}
