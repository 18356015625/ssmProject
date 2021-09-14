package ssmProject;

/*
 * static的使用,被static修饰的方法是类方法
 * 说明：在加载类的过程中，完成静态变量的内存分配，再执行静态代码块，两者是在创建对象之前执行的。
类属性、类方法可以通过类名和对象名访问，实例属性、实例方法只能通过对象名访问。
 */
public class TestStatic {
	public static final String name = "handy";
	static int j;
	static int s = 80;
	int i = 5;
	int k = 12;
	
	//静态代码块，执行且执行一次
	static {
		j = 10;
		System.out.println("静态块代码输出：" + j);
	}
	
	public String getNum() {
		String str = "测试getNum方法";
		return str;
	}
	
	//静态方法（类方法）
	public static void getValue() {
		System.out.println("测试静态方法");
	}
	
	public static void main(String[] args) {
		TestStatic test = new TestStatic();
		String a = test.getNum();
		System.out.println(a);
		TestStatic.getValue();
		System.out.println("----------------");
		System.out.println(TestStatic.s);
		System.out.println(test.i);
		System.out.println(test.k);
	}
	

}
