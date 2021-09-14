package ssmProject;

public class TestThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("通过实现runnable接口实现线程");
	}
	
	public static void main(String[] args) {
		TestThread thread = new TestThread();
		Thread thread1 = new Thread(thread);
		thread1.start();
		
	}

}
