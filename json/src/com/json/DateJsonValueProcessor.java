package com.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor{

	private String format="yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat sdf=new SimpleDateFormat(format);
	
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	//需要处理日期的相关格式
	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		//System.out.println(arg0+","+arg1+","+arg2);
		if(arg1==null){
			return "";
		}else if(arg1 instanceof Date){
			return sdf.format(arg1);
		}else{
			return arg1.toString();
		}
	}

}
