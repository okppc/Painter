package com.eebbk.shape;

import java.util.ArrayList;
import java.util.List;

public class ShapeHelper {
	
	public enum Flag{
		USER,
		USERBAK,
		REMOTE,
		REMOTEBAK
	}
	
	
	/**
	 * 用户端的绘制库
	 * 
	 */
	private List<Shape> mUser = new ArrayList<Shape>();
	
	/**
	 * 用户备份绘制库
	 * 
	 */
	private List<Shape> mUserBak = new ArrayList<Shape>();
	
	/**
	 * 远程端的绘制库
	 * 
	 */
	private List<Shape> mRemote = new ArrayList<Shape>();
	
	/**
	 * 远程端备份绘制库
	 * 
	 */
	private List<Shape> mRemoteBak = new ArrayList<Shape>();
	
	
	public void addShape(Shape shape, Flag flag){
		if(shape == null){
			return;
		}
		List<Shape> lists = null;
		if(flag == Flag.USER){
			lists = mUser;
		}else if(flag == Flag.USERBAK){
			lists = mUserBak;
		}else if( flag == Flag.REMOTE ){
			lists = mRemote;
		}else{
			lists = mRemoteBak;
		}
		if(lists == null){
			lists = new ArrayList<Shape>();
		}
		lists.add(shape);
	}
	
	public void delShape(Shape shape, Flag flag){
		if(shape == null){
			return;
		}
		List<Shape> lists = null;
		if(flag == Flag.USER){
			lists = mUser;
		}else if(flag == Flag.USERBAK){
			lists = mUserBak;
		}else if( flag == Flag.REMOTE ){
			lists = mRemote;
		}else{
			lists = mRemoteBak;
		}
		if(lists == null){
			lists = new ArrayList<Shape>();
			return;
		}
		lists.remove(shape);
	}
	
	
	public void delShape(int location, Flag flag){
		List<Shape> lists = null;
		if(flag == Flag.USER){
			lists = mUser;
		}else if(flag == Flag.USERBAK){
			lists = mUserBak;
		}else if( flag == Flag.REMOTE ){
			lists = mRemote;
		}else{
			lists = mRemoteBak;
		}
		if(lists == null){
			lists = new ArrayList<Shape>();
			return;
		}
		
		if(location<lists.size()){
			lists.remove(location);
		}
		
		
	}
	
	
	
	public List<Shape> getAll(Flag flag){
		List<Shape> lists = null;
		if( flag == Flag.USER ){
			lists = mUser;
		}else if( flag == Flag.USERBAK ){
			lists = mUserBak;
		}else if( flag == Flag.REMOTE ){
			lists = mRemote;
		}else{
			lists = mRemoteBak;
		}
		return lists;
	}
	
	
	/******  备份的shape信息    *******/
	
	public void clearAll(Flag flag){
		List<Shape> lists = null;
		if( flag == Flag.USER ){
			lists = mUser;
		}else if( flag == Flag.USERBAK ){
			lists = mUserBak;
		}else if( flag == Flag.REMOTE ){
			lists = mRemote;
		}else{
			lists = mRemoteBak;
		}
		if(lists == null){
			return;
		}
		lists.clear();
	}
	
	//比较用户库和备份库的大小
	public boolean isUsrBakBig(){
		if(mUserBak == null || mUser == null){
			return false;
		}
		if( mUserBak.size() > mUser.size() ){
			return true;
		}
		return false;
	}
	
	//比较远程库和备份库的大小
		public boolean isRemoteBakBig(){
			if(mRemote == null || mRemoteBak == null){
				return false;
			}
			if( mRemoteBak.size() > mRemote.size() ){
				return true;
			}
			return false;
		}
	
	
	
	
	
	
}
