package com.cheney.triangle;

import java.util.Scanner;

@SuppressWarnings("resource")
public class Test{

	

	public static void main(String[] args){
		
		Scanner in=new Scanner(System.in);
		System.out.println("请输入要打印杨辉三角的行数：");
		int row=in.nextInt();
		YHTriangle yHTriangle=new YHTriangle();
		yHTriangle.triangle(row);
	}


}