package ssmProject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public  class TestCallableThread<V>  extends  EatTest implements Callable<V>{

	@SuppressWarnings("unchecked")
	@Override
	public V call() throws Exception {
		String test = "实现callable接口,通过futureTask创建线程";
		return (V) test;
	}
	
	public static void main(String[] args) {
		Callable<String> call  = new TestCallableThread<String>();
		FutureTask<String> futureTask = new FutureTask<String>(call);
		Thread thread = new Thread(futureTask);
		thread.start();
		try {
			String value = futureTask.get();
			System.out.println(value);	
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
}
