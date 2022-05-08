package autotrade;
//https://www.javatpoint.com/completablefuture-in-java
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
	public static void main(String[] args) {
//		try {
//			List<Integer> list = Arrays.asList(5, 9, 14);
//			list.stream().map(num -> CompletableFuture.supplyAsync(() -> getNumber(num)))
//					.map(CompletableFuture -> CompletableFuture.thenApply(n -> n * n)).map(t -> t.join())
//					.forEach(s -> System.out.println(s));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
		
//		CompletableFuture.runAsync(() -> {
//			  // doSomething() void
//			});
//		
//		CompletableFuture.supplyAsync(() -> { 
//			   // return doSomething() return
//			});
		
//		https://developers.ascendcorp.com/java-%E0%B8%AA%E0%B8%A3%E0%B8%B8%E0%B8%9B%E0%B9%80%E0%B8%97%E0%B8%84%E0%B8%99%E0%B8%B4%E0%B8%84%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B9%83%E0%B8%8A%E0%B9%89-completablefuture-%E0%B9%81%E0%B8%9A%E0%B8%9A%E0%B8%AD%E0%B9%88%E0%B8%B2%E0%B8%99%E0%B8%81%E0%B9%88%E0%B8%AD%E0%B8%99%E0%B8%AA%E0%B8%AD%E0%B8%9A-a90974546182	
//		https://www.baeldung.com/java-completablefuture
//		https://dzone.com/articles/20-examples-of-using-javas-completablefuture
		Integer result = CompletableFuture.supplyAsync(() -> 1)  
		        .thenApply(response -> response + 4)
		        .thenApply(response -> response * 3)
		        .join();
		System.out.println(result); // 15
		
		CompletableFuture.supplyAsync(() -> 1)
        .thenApply(response -> response + 4)
        .thenApply(response -> response * 4)
        .thenAccept(response -> System.out.println(response));
		
		CompletableFuture.supplyAsync(() -> 1)
        .thenApply(response -> response + 4)
        .thenApply(response -> response * 3)
        .thenRun(() -> System.out.println("Success"));
		
		CompletableFuture<String> completableFuture = computeAmount()
				 .thenCompose(amount-> computePrice(amount))
				 .thenCompose(price->  computeSummary(price));System.out.println(completableFuture.join());
	}

	public static CompletableFuture<Integer>  computeAmount(){
	    return CompletableFuture.supplyAsync(()-> 3);
	}

	public static CompletableFuture<Double>  computePrice(Integer i){
	    return CompletableFuture.supplyAsync(()-> i * 111.11);
	}

	public static CompletableFuture<String>  computeSummary(Double d){
	    return CompletableFuture.supplyAsync(()-> "$"+d);
	}
}

//	public static void main(String[] args) {

//		CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
//		while (!completableFuture.isDone()) {
//		    System.out.println("CompletableFuture is not finished yet...");
//		}
//		long result = completableFuture.get();
//
//	}
//
//	public void run() {
//
//	}
////	private static void theOldWay() {
////	    doAThing()
////	            .thenCompose(Main::doAnotherThing)
////	            .thenAccept(Main::reportSuccess)
////	            .exceptionally(Main::reportFailure);
////	}
//
////	private static CompletableFuture<Void> theNewWay() {
////	    try {
////	        String intermediate = await(doAThing());
////	        String result = await(doAnotherThing(intermediate));
////	        reportSuccess(result);
////	    } catch (Throwable t) {
////	        reportFailure(t);
////	    }
////	    return completedFuture(null);
////	}
//	public void doAThing() {
//		
//	}
//	public void doAnotherThing( String intermediate) {
//		
//	}
//	
//
//}