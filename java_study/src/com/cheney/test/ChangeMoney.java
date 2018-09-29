package com.cheney.test;

import java.util.Scanner;

public class ChangeMoney {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入金额：");
		String money=sc.next();
		String[] moneyStrs=money.split("[.]");
		
		String intMoney=moneyStrs[0];
		String floatMoney=moneyStrs[1];
		
		
		int intLen=intMoney.length();
		int n=0;
		
		StringBuilder moneyChina=new StringBuilder();
		for(int i=intLen-1;i>=0;i--){
			if(n==0&&Integer.parseInt(intMoney.substring(i, i+1))!=0){
				moneyChina.insert(0, intMoney.substring(i, i+1)+"元");
			}
			if(n%4==1&&Integer.parseInt(intMoney.substring(i, i+1))!=0){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"拾");
			}
			if(n%4==2&&Integer.parseInt(intMoney.substring(i, i+1))!=0){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"佰");
			}
			if(n%4==3&&Integer.parseInt(intMoney.substring(i, i+1))!=0){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"仟");
			}
			if(n==4){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"万");
			}
			if(n==8&&Integer.parseInt(intMoney.substring(i, i+1))!=0){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"亿");
			}
			if(n==12){
				moneyChina.insert(0,intMoney.substring(i, i+1)+"兆");
			}
			n++;
		}
		String moneyChange=moneyChina.toString();
		moneyChange=change(moneyChange);
		System.out.println(moneyChange);
	}

	public static String change(String money) {
		money=money.replace("0", "零");
		money=money.replace("1", "壹");
		money=money.replace("2", "贰");
		money=money.replace("3", "叁");
		money=money.replace("4", "肆");
		money=money.replace("5", "伍");
		money=money.replace("6", "陆");
		money=money.replace("7", "柒");
		money=money.replace("8", "捌");
		money=money.replace("9", "玖");
		return money;
	}
}
