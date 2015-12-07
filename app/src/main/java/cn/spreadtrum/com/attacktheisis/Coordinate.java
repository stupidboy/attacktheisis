package cn.spreadtrum.com.attacktheisis;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Coordinate {
    private int posX;
    private int posY;

    public Coordinate(int x, int y){
        posX = x;
        posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public   Coordinate(Coordinate from){
        posX = from.getPosX();
        posY = from.getPosX();
    }
}
