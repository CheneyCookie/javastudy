package com.cheney.reflection;

/*
 * 反射小结
 * 1.Class:是一个类；一个描述类的类，封装了描述method的方法，描述字段的Field
 * 描述构造器的Constructor等属性
 * 
 * 2.如何得到Class对象
 * 	1.Person.class
 * 	2.person.getClass
 * 	3.Class.forName("com.cheney.reflection.Person")
 * 
 * 3.关于Method:
 * 	1.如何获取method
 * 		1.getDeclaredMethods:得到Method的数组
 * 		2.getDeclaredMethod(String methodName,Class... paramterTypes)
 * 	2.如何调用method
 * 		1.如果方法是private修饰的，需要先调用Method的setAccessible(true),使其变为可访问
 * 		2.method.invoke(obj,Object...args);
 * 
 * 4.关于Field:
 * 	1.如何获取Field：getField(String fieldName)
 * 	2.如何获取Field的值
 * 		1.setAccessible(true)
 * 		2.field.get(Object obj)
 * 	3.如何设置Field的值
 * 		field.set(Object obj,Object val)
 * 
 * 5.了解Constructor和Annotation
 * 
 * 6.反射和泛型
 * 	1.getGenericSuperClass:带泛型参数的父类，返回值为：BaseDao<Employee,String>
 * 	2.Type的子接口：ParamterizedType
 * 	3.可以调用ParamterizedType的getActually()获取泛型参数的数组
 * 
 * 7.搞定ReflectionUtil即可
 * */

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import org.junit.Test;

/*
 * getMethod()
 * invoke2()
 * */
public class TestReflection {

	/*
	 * 关于Class 1.Class是一个类 2.对象照镜子后可以得到的信息， 某个类的数据成员名、方法和构造器、某个类到底实现了哪些接口
	 * 3.对于每个类而言，JRE都为其保留了一个不变的Class类型的对象 一个Class对象包含了某些特定类的有关信息
	 * 4.Class对象只能由系统建立对象 5.一个类在JVM中只会有一个Class实例
	 */
	@Test
	public void testClass() throws ClassNotFoundException {
		Class clazz = null;

		// 1.得到Class对象
		// 1.直接通过.class的方式得到
		clazz = Person.class;
		// 2.通过对象调用getClass()方法获取
		// Person person=new Person();
		// clazz=person.getClass();
		Object object = new Person();
		clazz = object.getClass();
		// 3.通过全类名获取,用的比较多
		String className = "com.cheney.reflection.Person";
		clazz = Class.forName(className);

		// Field[] fields=clazz.getDeclaredFields();

		System.out.println(clazz);
	}

	/*
	 * Class类的newInstance方法
	 */
	@Test
	public void testNewInstance() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String className = "com.cheney.reflection.Person";
		Class clazz = Class.forName(className);

		// 利用Class对象的newInstance()方法来创建类的一个对象
		// 实际调用的是类的那个无参构造器
		// 一般地，一个类若声明了带参数的构造器，也要声明一个无参数的构造器，给反射用
		Object obj = clazz.newInstance();
		System.out.println(obj);

	}

	@Test
	public void testClassLoader() throws ClassNotFoundException, IOException {
		// 1.获取一个系统的类加载器
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		System.out.println(classLoader);

		// 2.获取系统类加载器的父类加载器
		classLoader = classLoader.getParent();
		System.out.println(classLoader);

		// 3.获取扩展类加载器的父类加载器
		classLoader = classLoader.getParent();
		System.out.println(classLoader);

		// 4.测试当前类由哪个类加载器进行加载
		classLoader = Class.forName("com.cheney.reflection.Person")
				.getClassLoader();
		System.out.println(classLoader);

		// 5.测试JDK提供的Object类由哪个类加载器负责加载
		classLoader = Class.forName("java.lang.Object").getClassLoader();
		System.out.println(classLoader);

		// 6.关于类加载器的一个主要方法
		// 调用getResourceAsStream获取类路径下的文件对应的输入流
		InputStream in = null;
		in = this.getClass().getClassLoader()
				.getResourceAsStream("com/cheney/reflection/test.properties");
		// new FileInputStream("test.properties");
		System.out.println(in);
	}

	/*
	 * Class是对一个类的描述 类的属性：Field 类的方法：Method 类的构造器：Constructor
	 * 
	 * Method：对应类中的方法。 1.获取Method: 1.获取类的方法的数组：clazz.getDeclaredMethods
	 * 2.获取类的指定的方法：clazz.getDeclaredMethod(String name,Class<?>...
	 * parameterTypes) name:方法名 parameterTypes:方法的参数类型（使用Class来描述）的列表 Method
	 * method=clazz.getDeclaredMethod("setName", String.class);
	 * 3.通过method对象执行方法： public Object invoke(Object obj,Object... args)
	 * obj:执行哪个对象的方法 args:执行方法时需要传入的参数
	 * 
	 * Object obj=clazz.newInstance(); method.invoke(obj, "cheney");
	 */
	@Test
	public void testMethod() throws Exception {
		Class clazz = Class.forName("com.cheney.reflection.Person");

		// 1.得到clazz对应的类中有哪些方法，不能获取私有private方法
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}

		// 2.获取所有的方法，包括private方法,且只获取当前类声明的方法
		Method[] methods2 = clazz.getDeclaredMethods();
		for (Method method : methods2) {
			System.out.println("  " + method.getName());
		}

		// 3.获取指定的方法
		Method method = clazz.getDeclaredMethod("setName", String.class);
		System.out.println(method);

		method = clazz.getDeclaredMethod("test");
		System.out.println(method);

		// 4.执行方法
		method = clazz.getDeclaredMethod("setName", String.class);
		Object obj = clazz.newInstance();
		method.invoke(obj, "cheney");
		System.out.println(obj);
	}

	public Object invoke(Object obj, String methodName, Object... args) {
		// 1.获取method对象
		Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();
			System.out.println(parameterTypes[i]);
		}

		try {
			Method method = obj.getClass()
					.getMethod(methodName, parameterTypes);
			// 2.执行method方法
			// 3.返回方法的返回值
			return method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 不可调用父类的私有方法
	public Object invoke(String className, String methodName, Object... args) {
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
			System.out.println(obj);
			return invoke(obj, methodName, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void testInvoke() {
		Object obj = new Person();
		invoke(obj, "setName", "cheney");
		System.out.println(obj);
		invoke("com.cheney.reflection.Person", "setName", "cookie");
		Object result = invoke("java.text.SimpleDateFormat", "format",
				new Date());
		System.out.println(result);
	}

	/*
	 * 获取当前类的父类 直接调用Class对象的getSuperClass()方法
	 */
	@Test
	public void testGetSuperClass() throws ClassNotFoundException {
		String className = "com.cheney.reflection.Student";
		Class clazz = Class.forName(className);
		Class superClazz = clazz.getSuperclass();
		System.out.println(superClazz);
	}

	/*
	 * 可调用父类的私有方法
	 */
	public Object invoke2(Object obj, String methodName, Object... args) {
		// 1.获取method对象
		Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();
			System.out.println(parameterTypes[i]);
		}

		try {
			Method method = getMethod(obj.getClass(), methodName,
					parameterTypes);
			method.setAccessible(true);
			// 2.执行method方法
			// 3.返回方法的返回值
			return method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void testInvoke2() {
		// Student类的method1()方法被调用，打印private void method1
		Object obj = new Student();
		invoke2(obj, "method1", 10);
		// Student类的父类的method2()方法被调用，返回值为private String method2
		Object result = invoke2(obj, "method2");
		System.out.println(result);
	}

	/*
	 * 若通过Method的invoke()方法调用方法，而访问权限不足， 则可以先使该方法变为可被访问的：
	 * method.setAccessible(true);
	 */
	@Test
	public void testPrvateMethod() throws Exception {
		Object obj = new Student();
		Class clazz = obj.getClass();
		Method method = clazz.getDeclaredMethod("method1", Integer.class);
		System.out.println(method);

		// 需要通过反射执行私有方法
		method.setAccessible(true);

		method.invoke(obj, 10);
	}

	/*
	 * 获取clazz的methodName方法，该方法可能是私有方法， 还可能在父类中（私有方法）
	 */
	public Method getMethod(Class clazz, String methodName,
			Class... parameterTypes) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Method method = clazz.getDeclaredMethod(methodName,
						parameterTypes);
				return method;
			} catch (NoSuchMethodException e) {
			}
		}
		return null;
	}

	@Test
	public void testGetMethod() throws Exception {
		Class clazz = Class.forName("com.cheney.reflection.Student");
		Method method = getMethod(clazz, "method1", Integer.class);
		System.out.println(method);

		method = getMethod(clazz, "method2");
		System.out.println(method);
	}

	@Test
	public void testReflectionUtil() {
		String className = "com.cheney.reflection.Student";
		String methodName = "method3";
		Object[] args = { "cheney", 22 };

		Object result2 = ReflectionUtil.invokeMethod(className, methodName,
				args);
		System.out.println(result2);
		Object obj = new Student();
		Object result = ReflectionUtil.invokeMethod(obj, methodName, args);
		System.out.println(result);
	}

	/*
	 * Field:封装了字段 1.获取字段 1.Field[] fields=clazz.getDeclaredFields(); 2.Field
	 * field2=clazz.getDeclaredField("age");
	 * 
	 * 2.获取指定对象的指定字段的值 public Object get(Object obj) obj为字段所在的对象 Object
	 * val=field.get(person) 注意：若该字段是私有的，需要调用setAccessible(true)方法
	 * 
	 * 3.设置指定字段的值 public void set(Object obj,Object value) obj:字段所在的对象
	 * value:要设置的值
	 */

	@Test
	public void testField() throws Exception {
		String className = "com.cheney.reflection.Person";
		Class clazz = Class.forName(className);

		// 1.获取字段，获取Field的数组
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}

		// 获取指定名字的Field
		Field field = clazz.getDeclaredField("name");
		System.out.println(field.getName());

		Person person = new Person("ABC", 12);
		// 2.获取指定对象的Field的值
		Object val = field.get(person);
		System.out.println(val);

		// 3.设置指定对象的Field的值
		field.set(person, "cheney");
		System.out.println(person.getName());

		// 4.若该字段是私有的，需要调用setAccessible(true)方法
		Field field2 = clazz.getDeclaredField("age");
		field2.setAccessible(true);
		System.out.println(field2.get(person));
	}

	@Test
	public void testClassField() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String className = "com.cheney.reflection.Student";
		String fieldName = "age";// 可能为私有，可能在其父类中
		Object val = 20;

		// 创建className对应类的对象，并为其fieldName赋值为val;
		Object obj = null;
		Class clazz = Class.forName(className);
		Field field = getField(clazz, fieldName);

		obj = clazz.newInstance();
		setFieldValue(obj, field, val);

		Student stu = (Student) obj;
		System.out.println(stu.getAge());
	}

	private void setFieldValue(Object obj, Field field, Object val)
			throws IllegalAccessException {
		field.setAccessible(true);
		field.set(obj, val);
	}

	public Field getField(Class clazz, String fieldName) {
		Field field = null;
		for (Class clazz2 = clazz; clazz2 != Object.class; clazz2 = clazz2
				.getSuperclass()) {
			try {
				field = clazz2.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return field;
	}

	public Object getFieldValue(Object obj, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		return field.get(obj);
	}

	/*
	 * Constructor:代表构造器
	 */
	@Test
	public void testConstructor() throws SecurityException,
			ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String className = "com.cheney.reflection.Person";
		Class<Person> clazz = (Class<Person>) Class.forName(className);

		// 1.获取Constructor对象
		Constructor<Person>[] constructors = (Constructor<Person>[]) Class
				.forName(className).getConstructors();
		for (Constructor<Person> constructor : constructors) {
			System.out.println(constructor);
		}

		Constructor<Person> constructor = clazz.getConstructor(String.class,
				int.class);
		System.out.println(constructor);

		// 2.调用构造器的newInstance()方法创建对象
		Object obj = constructor.newInstance("cheney", 1);
		System.out.println(obj);
	}

	/*
	 * Annotation和反射 1.获取Annotation
	 * 
	 * getAnnotation(Class<T> annotationClass) getDeclaredAnnotations()
	 */
	@Test
	public void testAnnotation() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		String className = "com.cheney.reflection.Person";
		Class clazz = Class.forName(className);
		Object obj = clazz.newInstance();
		Method method = clazz.getDeclaredMethod("setAge", int.class);
		System.out.println(method);
		int val = 6;
		Annotation annotation = method.getAnnotation(AgeValidator.class);
		if (annotation != null) {
			if (annotation instanceof AgeValidator) {
				AgeValidator ageValidator = (AgeValidator) annotation;
				if (val < ageValidator.min() || val > ageValidator.max()) {
					throw new RuntimeException("年龄非法");
				}
			}
		}
		method.invoke(obj, val);
		System.out.println(obj);

	}

	@Test
	public void testGenericAndReflection() {
		PersonDao personDao = new PersonDao();
		Person entity = new Person();
		personDao.save(entity);

		Person result = personDao.get(1);
	}

	/*
	 * 通过反射，获得定义Class时声明的父类的泛型参数的类型 如：public Employee extends
	 * BaseDao<Employee,String> clazz:子类对应的class对象 index:子类继承父类时，传入的泛型的索引，从0开始
	 */
	public static Class getSuperClassGenericType(Class clazz, int index) {
		Type type = clazz.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType parameterizedType = (ParameterizedType) type;
		System.out.println(parameterizedType);
		Type[] args = parameterizedType.getActualTypeArguments();

		if (index >= args.length || index < 0) {
			return null;
		}
		if (!(args[index] instanceof Class)) {
			return null;
		}

		return (Class) args[index];
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperGenericType(Class clazz) {
		return getSuperClassGenericType(clazz, 0);
	}

	@Test
	public void testgetSuperClassGenericType() {
		Class clazz = EmployeeDao.class;
		// Employee.class
		Class argClass = getSuperClassGenericType(clazz, 0);
		System.out.println(argClass);
		// String.class
		argClass = getSuperClassGenericType(clazz, 1);
		System.out.println(argClass);
	}
}
