package com.cheney.enumtest;

public enum Season3 implements Info{
	//枚举类的实例要在第一行列出
	SPRING("春天","春风又绿江南岸"),
	SUMMER("夏天","映日荷花别样红"),
	AUTUMN("秋天","秋水共长天一色"),
	WINTER("冬天","窗含西岭千秋雪"),
	;
	
	final String SEASON_NAME;
	final String SEASON_DESC;
	
	private Season3(String seasonName,String seasonDesc){
		this.SEASON_NAME=seasonName;
		this.SEASON_DESC=seasonDesc;
	}

	@Override
	public String getInfo() {
		switch(this){
		case SPRING:
			return "A";
		case SUMMER:
			return "B";
		case AUTUMN:
			return "C";
		case WINTER:
			return "D";
		}
		return null;
	}
	
	
	
}
