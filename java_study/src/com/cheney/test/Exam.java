package com.cheney.test;

import java.math.BigDecimal;
import java.util.Scanner;

public class Exam {
	public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String s=sc.next();
        
        int m=1;
        for(int i=0;i<s.length()-1;i++){
            char c1=s.charAt(i);
            char c2=s.charAt(i+1);
            if(c1!=c2){
                m++;
            }
        }
        System.out.println(s.length());
        System.out.println(m);
        BigDecimal bd=new BigDecimal(s.length());
        BigDecimal mm=new BigDecimal(m);
        System.out.println(bd+"------");
        System.out.println(mm);
        System.out.println(bd.divide(mm,4));
    }
}
