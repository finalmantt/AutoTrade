package autotrade;

public class TestThread extends Thread{
	private  long time = 100;
	private String name = "";
	public static void main(String args[]) {
		TestThread t1 = new TestThread();
		TestThread t2 = new TestThread();
		
		
		t1.T1(1000,"T1");
		t2.T2(0,"T2");
		
		t1.start();
		t1.waitThread();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
		
		t2.waitThread();
		
		
	}
	
	public  void T1(long time ,String name) {
		this.name = name;
		this.time=time;
	}
	
	
	public  void T2(long time, String name) {
		this.time = time;
		this.name = name;
		
	}

	public void waitThread() {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name);
	} 
	
	public void run() {
		
		
	}
}
