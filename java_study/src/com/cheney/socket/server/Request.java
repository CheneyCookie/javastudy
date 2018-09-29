package com.cheney.socket.server;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
	private BufferedReader bufferedReader;
	private String[] str;
	private String endString;
	private int length;

	public Request(BufferedReader bufferedReader) {
		this.bufferedReader=bufferedReader;
		init();
	}

	//初始化
	public void init() {
		try {
			str=new String[3];
			str=bufferedReader.readLine().split(" ");
			if(str[0].equals("POST")){
				String strRed=null;
				while((strRed=bufferedReader.readLine())!=null){
					System.out.println(strRed);
					if(strRed.contains("Length")){
						String lenStr=strRed.split(" ")[1];
						length=Integer.parseInt(lenStr);
					}
					if(strRed.equals("")){
						char[] endChar=new char[length];
						bufferedReader.read(endChar);
						System.out.println(endChar);
						StringBuffer stringBuffer=new StringBuffer();
						for(int i=0;i<length;i++){
							stringBuffer.append(endChar[i]);
						}
						endString=stringBuffer.toString();
						break;
					}
				}
			}
			System.out.println(str[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//获取提交方式
	public String getMethod() {
		return str[0];
	}

	//获取资源路径
	public String getUrl() {
		return str[1].substring(1).split("\\?")[0];
	}
	
	//获取用户信息
	public String getEndString(){
		return endString;
	}
}
