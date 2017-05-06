package com.cheney.enumtest;
/**
 * 关于枚举：
 * 1.定义：一个类的个数有限，且固定，属性固定
 * 
 * 2.手工定义枚举类：
 * 		1.私有化构造器
 * 		2.属性使用final来修饰
 * 		3.在类的内部创建枚举类对象，且使用public static final修饰
 * 		4.提供些工具方法：values(), valueof()...;
 * 
 * 3.使用enum关键字定义枚举类
 * 		1.使用enum替代class声明一个类
 * 		2.枚举类对象的声明必须放在枚举类的第一行。声明对象的同时即时创建枚举类对象的过程
 * 			SPRING("春天","春风又绿江南岸"),
 *			SUMMER("夏天","映日荷花别样红"),
 *			AUTUMN("秋天","秋水共长天一色"),
 *			WINTER("冬天","窗含西岭千秋雪");
 * 		3.若有属性需要使用static final修饰，使其变为常量
 * 		4.构造器默认使用private修饰
 * 
 * 4.枚举类常用的方法
 * 		1.values()得到所有的枚举对象的集合
 * 		2.valueof()(Class<T> enumType,String name):根据传入的字符串的到对应的enumType类型的枚举类对象
 * 
 * 5.实现接口的枚举类
 *		1.统一在一个方法中提供各个枚举对象的实现，可以使用switch
 */
public enum Season4 implements Info {
	// 枚举类的实例要在第一行列出
	SPRING("春天", "春风又绿江南岸") {
		@Override
		public String getInfo() {
			return "AA";
		}
	},
	SUMMER("夏天", "映日荷花别样红") {
		@Override
		public String getInfo() {
			return "BB";
		}
	},
	AUTUMN("秋天", "秋水共长天一色") {
		@Override
		public String getInfo() {
			return "CC";
		}
	},
	WINTER("冬天", "窗含西岭千秋雪") {
		@Override
		public String getInfo() {
			return "DD";
		}
	},
	;

	final String SEASON_NAME;
	final String SEASON_DESC;

	private Season4(String seasonName, String seasonDesc) {
		this.SEASON_NAME = seasonName;
		this.SEASON_DESC = seasonDesc;
	}

}
