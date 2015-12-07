package cn.spreadtrum.com.attacktheisis.obj;

import java.util.ArrayList;

/**
 * Created by SPREADTRUM\joe.yu on 12/7/15.
 */
public class FireQueue <E>{


    private Object[] items;
    private int mIndex = -1;
    private int mCapacity = 0;
    public FireQueue(int capacity){
        items = new  Object[capacity];
        mCapacity = capacity;
    }
    //add the obj to the tail of the queue
    public void enqueue(E object) {
        synchronized (this) {
            if(mIndex < 0) mIndex = 0;
            items[mIndex] = object;
            mIndex++;
        }
    }
    //return the first obj in the queue
    public E dequeue() {
        synchronized (this) {
            if (mIndex < 0)
                return null;
            E x = (E)items[0];
            for(int i = 0;i < mCapacity-1;i++){
                if(items[i+1] != null) {
                    items[i] = items[i+1];
                }
            }
            mIndex --;
            return x;
        }


    }
    //return counts of objs in queue;
    public int length() {
        return mIndex+1 ;
    }
    public  boolean  isEmpty(){
        return  mIndex < 0;
    }
}
