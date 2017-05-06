package com.cheney.math;


import java.util.Random;

import org.junit.Test;
import static java.lang.Math.*;

/* Random中封装了随机相关的方法：返回随机的基本数据类型的值
 * Math中封装了常用的数学方法
 * 静态导入:import static java.lang.Math.*;
 * 导入指定类的静态属性和静态方法
 */
public class TestRandom {

	@Test
	public void testRandom() {
		Random random=new Random();
		System.out.print(18);
		System.out.print(random.nextInt(1000000000));
		//System.out.println(random.nextInt(10));
		//System.out.println(random.nextBoolean());
	}

	@Test
	public void testMath(){
		System.out.println(Math.sin(Math.PI/6));
		System.out.println(sin(PI/3));
	}
}
