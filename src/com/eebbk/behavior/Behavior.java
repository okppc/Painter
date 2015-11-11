package com.eebbk.behavior;

import java.io.Serializable;

import android.util.Log;



public class Behavior implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2506929508516363448L;
	
	//定义模式
	public interface Action{
		byte START = 0;
		byte MOVE = 1;
		byte END = 2;
		byte UNDO = 3;
		byte CLEAR = 4;
		byte PIC = 5;
	}
	
	
	private byte step = Action.START;
    private float x = 0.0f;
    private float y = 0.0f;

    public Behavior() {
    }

    public Behavior(byte step, float x, float y) {
        this.step = step;
        this.x = x;
        this.y = y;
    }

    public static String pack(Behavior t) {
        return String.format("%d:%f,%f;", t.getStep(), t.getX(), t.getY());
    }

    public static String packIndex(int index) {
        return String.format("5:%d,0;", index);
    }

    public static Behavior unpack(String data) {
        int sp1 = data.indexOf(":");
        if (sp1 <= 0) {
            return null;
        }
        int sp2 = data.indexOf(",");
        if (sp2 <= 2) {
            return null;
        }
        String step = data.substring(0, sp1);
        String x = data.substring(sp1 + 1, sp2);
        String y = data.substring(sp2 + 1);

        try {
            byte p1 = Byte.parseByte(step);
            if (p1 == 5) {
                Log.i("Action", "RECV DATA:" + x);
            } else {
                float p2 = Float.parseFloat(x);
                float p3 = Float.parseFloat(y);
                return new Behavior(p1, p2, p3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void make(byte step, float x, float y) {
        this.step = step;
        this.x = x;
        this.y = y;
    }

    public Behavior makeStart(float x, float y) {
        make(Action.START, x, y);
        return this;
    }

    public Behavior makeMove(float x, float y) {
        make(Action.MOVE, x, y);
        return this;
    }

    public Behavior makeEnd(float x, float y) {
        make(Action.END, x, y);
        return this;
    }

    public Behavior makeUndo() {
        make(Action.UNDO, 0.0f, 0.0f);
        return this;
    }

    public Behavior makeClear() {
        make(Action.CLEAR, 0.0f, 0.0f);
        return this;
    }


    public int getStep() {
        return step;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isPaint() {
        return !isUndo() && !isClear();
    }

    public boolean isUndo() {
        return step == Action.UNDO;
    }

    public boolean isClear() {
        return step == Action.CLEAR;
    }
    

}
