package ssmProject;
/**
 * 线程实现方法
 * @author handy
 *
 */
public class Test extends Thread {
     
	public void run() {
		System.out.println("线程测试");
	}
	
	public static void main (String[] args) {
		Test test = new Test();
		test.start();
        String name  = TestStatic.name;
        System.out.println(name);
        System.out.println(10%3*2);
	}
}

