package com.eebbk.behavior;

import java.util.ArrayList;
import java.util.List;

import com.eebbk.behavior.Behavior.Action;

public class BehaviorManager {

	private List<Behavior> behaviors = new ArrayList<Behavior>();



	public void add(byte step,float x,float y){

		Behavior behavior = new Behavior(step, x, y);

		if(behaviors == null){
			behaviors = new ArrayList<Behavior>();
		}

		behaviors.add(behavior);

		behavior = null;
		
	}
	
	
	public List<Behavior> getAll(){
		if(behaviors == null || behaviors.isEmpty()){
			return null;
		}
		return behaviors;
		
	}
	
	
	
	
	
}
