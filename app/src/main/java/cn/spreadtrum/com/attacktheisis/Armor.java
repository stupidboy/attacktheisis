package cn.spreadtrum.com.attacktheisis;

/**
 * Created by SPREADTRUM\joe.yu on 11/30/15.
 */
public class Armor {
    public  static int ARMOR_BUILDING = 0;
    public  static int ARMOR_HUM = 1;
    public  static int ARMOR_TANK = 2;
    public  static int ARMOR_CAR = 3;
    public  static int ARMOR_JET = 4;

    public  Armor(int type){

    }
    public  int takeDamage(int attackType,int damage){
        return damage; //default not reduce.
    }
}
