package autotrade;

public class Counter {

	private int value = 0;

	public synchronized void increase() {

		for (int i = 0; i < 10; i++) {

			value = value + 1;

			System.out.println("value => " + value + " : thread => " + Thread.currentThread().getName());

		}

	}

}