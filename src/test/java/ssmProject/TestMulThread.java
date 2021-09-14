package ssmProject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author handy
 *
 */
public class TestMulThread {
	
	public static void main(String[] args) {
		//创建任务数
		int taskSize = 45;
		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		//创建多个有返回值的任务
		List<Future> lists = new ArrayList<Future>();
		for (int i = 0; i < taskSize; i++) {
			//静态方法不能直接访问非静态成员
			Callable able = new MyCallable(i);
		    Future t = pool.submit(able);
		    lists.add(t);
		}
		//关闭线程池  
		pool.shutdown();  
		//获取返回值
		for (Future Future: lists) {
			try {
				System.out.println(Future.get().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static class MyCallable implements Callable<Object> {
		
		private int taskNum;  
		  
		public MyCallable(int taskNum) {  
		   this.taskNum = taskNum;  
		}  

		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println(taskNum + "任务启动");
			Date dateTmp1 = new Date();  
			//Thread.sleep(1000);  
			Date dateTmp2 = new Date();  
			long time = dateTmp2.getTime() - dateTmp1.getTime();  
			System.out.println(taskNum + "任务终止");
			return taskNum + "任务返回运行结果为" + taskNum + ",运行时间为【" + time + "]";
		}  
		
		
		
	}
	

}
