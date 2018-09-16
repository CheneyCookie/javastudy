package com.cheney.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.junit.Test;

public class TestProxy {

	@Test
	public void testCalculator() {
		ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculatorImpl();
		arithmeticCalculator.add(1, 2);
	}

	@Test
	public void testProxy() {
		final ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculatorImpl2();
		/*
		 * classLoader:由动态代理产生的对象由哪个类加载器来加载。 通常情况下和被代理对象使用一样的类加载器
		 * Class<?>[]:由动态代理产生的对象必须需要实现的接口的Class数组
		 * InvocationHandeler:当具体调用代理对象时，将产生什么行为
		 */
		ArithmeticCalculator proxy = (ArithmeticCalculator) Proxy
				.newProxyInstance(arithmeticCalculator.getClass()
						.getClassLoader(),
						new Class[] { ArithmeticCalculator.class },
						new InvocationHandler() {
							/**
							 * proxy: method:正在被调用的方法 args:调用方法时传入的参数
							 */
							@Override
							public Object invoke(Object proxy, Method method,
									Object[] args) throws Throwable {
								System.out.println("proxy"+proxy.getClass());
								// System.out.println("method"+method);
								// System.out.println("args"+Arrays.asList(args));

								System.out.println("The method" + method
										+ "begins with" + args);
								// 调用被代理类的目标方法
								Object result = method.invoke(
										arithmeticCalculator, args);
								System.out.println("The method" + method
										+ "end with" + result);
								return result;
							}
						});
		proxy.mul(1, 2);
		int result = proxy.add(5, 2);
		System.out.println(result);
	}

	/*
	 * 关于动态代理的一些细节 1.需要一个被代理对象 2.一般地，Proxy.newInstance()的返回值一定是一个被代理对象实现的接口的类型
	 * 当然也可以是其他的接口的类型 3.类加载器通常和被代理对象使用相同的类加载器 注意：第二个参数必须是一个接口类型的数组
	 * 提示：若代理对象不需要实现被代理对象实现的接口以外的接口， 可以使用target.getClass().getInterfaces(),
	 * 4.InvocationHandel通常使用匿名内部类的方式：被代理对象需要是final类型的
	 * 5.InvocationHandel的invoke(
	 * )方法中的第一个参数Object类型的Proxy指的是正在被饭回的哪个代理对象，一般情况下不使用
	 */
	@Test
	public void testProxy2() {
		final ArithmeticCalculator target = new ArithmeticCalculatorImpl2();
		System.out.println(Arrays.asList(target.getClass().getInterfaces()));
		Object proxy = Proxy.newProxyInstance(target.getClass()
				.getClassLoader(),
		// new Class[]{ArithmeticCalculator.class,Validate.class},
				target.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						return method.invoke(target, args);
					}
				});
	}
	
	/* 开启事务
	 * 提交事务
	 * 事务回滚
	 * */
	@Test
	public void testPersonService(){
		Service target=new ServiceImpl();
		Service proxy=new PersonServiceProxy(target).getPersonServiceProxy();
		System.out.println(ServiceImpl.getPersons());
		proxy.addNew(new Person(1005,"CC"));
		System.out.println(ServiceImpl.getPersons());
		proxy.delete(1001);
		System.out.println(ServiceImpl.getPersons());
		proxy.update(new Person(1002,"MM"));
		System.out.println(ServiceImpl.getPersons());
	}

}
