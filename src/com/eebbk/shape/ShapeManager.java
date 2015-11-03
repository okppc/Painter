package com.eebbk.shape;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

public class ShapeManager {
	
	private List<Shape> shapes = new ArrayList<Shape>();
	
	private OnePath path;
	
	public void addPath(){
		if(path == null){
			return;
		}
		
		if(shapes == null){
			shapes = new ArrayList<Shape>();
		}
		
		shapes.add(path);
		path = null;
	}
	
	
	public void remove(Shape shape){
		if(shape == null || shapes == null || shapes.size() <= 0){
			return;
		}
		
		shapes.remove(shapes.size()-1);
		
	}
	
	
	public void initPath(int color ,int size){
		if(path != null){
			path = null;
		}
		path = new OnePath(color, size);
		
	}
	
	
	public void onPathMove(float x ,float y ,Canvas canvas){
		
		if(!path.isNewPoint(x, y)){
			return;
		}
		path.LineTo(x, y);
		path.drawPath(canvas);
		
	}
	
	
	public List<Shape> getAll(){ 
		
		if(shapes == null || shapes.isEmpty() ){
			return null;
		}
		
		return shapes;
		
	}
	
	
	
	
	
	
}
