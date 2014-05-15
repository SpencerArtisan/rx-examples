package skw.rx.anamorphisms;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.util.async.Async;

public class AsyncOperatorTest {
	@Test
	public void start() throws Exception {
		dumpWithThreadId(Async.start(() -> "one thing"));
	}
	
	@Test
	public void toAsync() throws Exception {
		Func0<String> func0 = () -> "one thing";
		dumpWithThreadId(Async.toAsync(func0).call());
	}
	
//	@Test
//	public void startFuture() throws Exception {
//		CompletableFuture<String> future = new CompletableFuture<>();
//		new Thread(new Runnable() {
//			public void run() {
//				future.complete("teleportation");
//			}
//		}).start();
//		dumpWithThreadId(Async.startFuture(() -> future));
//	}
	
//	@Test
//	public void deferFuture() throws Exception {
//		CompletableFuture<Observable<String>> future = new CompletableFuture<>();
//		new Thread(new Runnable() {
//			public void run() {
//				future.complete(Observable.just("rocket boots"));
//			}
//		}).start();
//		dump(Async.deferFuture(() -> future));
//	}
	
	@Test
	public void forEachFuture() throws Exception {
		Observable<Integer> source = Observable.from(1, 2);
		FutureTask<Void> forEachFuture = Async.forEachFuture(source, (s) -> {System.out.println(s);} );
	}
	
	@Test
	public void fromAction() throws Exception {
		dump(Async.fromAction(() -> {}, 42));
	}
	
	@Test
	public void fromCallable() throws Exception {
		dump(Async.fromCallable(() -> "result"));
	}
	
	@Test
	public void fromRunnable() throws Exception {
		dump(Async.fromRunnable(() -> {}, "value"));
	}	
}
