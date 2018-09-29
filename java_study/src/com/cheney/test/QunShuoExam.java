package com.cheney.test;

import java.util.Scanner;

public class QunShuoExam {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入信用卡号");
		String code=sc.next();
		int len=code.length();
		int singleSum=0;
		int doubleSum=0;
		
		for(int i=len-1;i>=0;i-=2){
			String a = code.substring(i, i+1);
			singleSum+=Integer.parseInt(a);
		}
		
		for(int i=len-2;i>=0;i-=2){
			String a = code.substring(i, i+1);
			int dou=Integer.parseInt(a)*2;
			if(dou>=10){
				dou=dou-9;
			}
			doubleSum+=dou;
		}
		System.out.println(singleSum);
		System.out.println(doubleSum);
		if((singleSum+doubleSum)%10==0){
			System.out.println("成功");
		}else{
			System.out.println("失败");
		}
	}
	
}
