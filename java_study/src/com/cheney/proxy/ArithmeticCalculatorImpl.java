package com.cheney.proxy;

public class ArithmeticCalculatorImpl implements ArithmeticCalculator{

	@Override
	public int add(int i, int j) {
		System.out.println("The method add begins with "+i+","+j);
		int result=i+j;
		System.out.println("The method add end with "+result);
		return result;
	}

	@Override
	public int sub(int i, int j) {
		System.out.println("The method sub begins with "+i+","+j);
		int result=i-j;
		System.out.println("The method sub end with "+result);
		return result;

	}

	@Override
	public void mul(int i, int j) {
		System.out.println("The method mul begins with "+i+","+j);
		int result=i*j;
		System.out.println(result);
		System.out.println("The method mul end with "+result);

	}

	@Override
	public void div(int i, int j) {
		System.out.println("The method div begins with "+i+","+j);
		int result=i/j;
		System.out.println(result);
		System.out.println("The method div end with "+result);

	}

}
