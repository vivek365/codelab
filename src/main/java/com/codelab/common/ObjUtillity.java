package com.codelab.common;

import java.util.Collection;

public class ObjUtillity {
	
	public static boolean isNotBlank(Object o){
		if(o==null)
			return false;
		if(o instanceof String && o.equals(""))
			return false;
		if(o instanceof Collection && ((Collection) o).size() == 0)
			return false;
		return true;
	}
	
	public static boolean isNull(Object o){
		return (o == null ?  true : false);
	}
	
	public static boolean isBlank(Object o){
		if(o==null)
			return true;
		if(o instanceof String && o.equals(""))
			return true;
		if(o instanceof Collection && ((Collection) o).size() == 0)
			return true;
		return false;
	}
	
	public static boolean isTrue(Object o){
		if(o==null)
			return false;
		if(o instanceof String && ((String) o).equalsIgnoreCase("true"))
			return true;
		if(o instanceof Boolean && ((Boolean) o).booleanValue())
			return true;
		return false;
	}
}
