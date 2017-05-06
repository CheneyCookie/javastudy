package com.cheney.enumtest;


import org.junit.Test;

public class TestEnum {

	@Test
	public void testSeason() {
		System.out.println(Season.SPRING);
		System.out.println(Season.SUMMER);
		System.out.println(Season.AUTUMN);
		System.out.println(Season.WINTER);
	}

	@Test
	public void TestSeason2Enum(){
		//Season2.AUTUMN.SEASON_DESC="ABC";
		System.out.println(Season2.AUTUMN.SEASON_DESC);
	}
	
	@Test
	public void TestEnumMethod(){
		//1.遍历枚举类的方法:每个枚举类都有一个values()方法，返回枚举类对象的数组
		Season2[] seasons=Season2.values();
		
		for(Season2 season:seasons){
			System.out.println(season);
		}
		
		//把一个字符串转为对应的枚举类对象
		String input="SPRING";
		Season2 s=Enum.valueOf(Season2.class, input);
		System.out.println(s);
	}
	
	@Test
	public void TestSeason3(){
		System.out.println(Season3.SUMMER.getInfo());
	}
	
	@Test
	public void TestSeason4(){
		Season4[] seasons=Season4.values();
		for(Season4 season:seasons){
			System.out.println(season.getInfo());
		}
	}
}
