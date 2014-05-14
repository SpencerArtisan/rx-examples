package skw.rx.anamorphisms;

import static skw.rx.util.Dumper.*;

import java.util.concurrent.TimeUnit;

import skw.rx.util.TestObservableFactory;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

public class CreatingObservablesTest {
	@Test
	public void from() {
		dump(Observable.from(1, 2, 3));
	}
	
	@Test
	public void just() {
		dump(Observable.just("one more thing"));
	}
	
	@Test
	public void create() throws Exception {
		dump(Observable.create((Subscriber<? super String> subscriber) -> {
			subscriber.onNext("wibble");
			subscriber.onError(new Exception("bad thing"));
		}));
	}
	
	@Test
	public void repeat() throws Exception {
		dump(Observable.just("ground hog day").repeat(2));
	}
	
//	@Test
//	public void defer() throws Exception {
//		TestObservableFactory factory = new TestObservableFactory();
//		Observable<String> deferred = Observable.<String>defer(factory::createObservable);
//		dump(deferred);
//		factory.value = "new value";
//		dump(deferred);
//	}
	
	@Test
	public void range() throws Exception {
		dump(Observable.range(1, 3));
	}
	
	@Test
	public void interval() throws Exception {
		dumpForTimePeriod(Observable.interval(10, TimeUnit.MILLISECONDS), 100);
	}
	
	@Test
	public void timer() throws Exception {
		dumpForTimePeriod(Observable.timer(10, TimeUnit.MILLISECONDS), 100);
	}
	
	@Test
	public void empty() throws Exception {
		dump(Observable.empty());
	}
	
	@Test
	public void error() throws Exception {
		dump(Observable.error(new Exception("bad thing")));
	}
	
	@Test
	public void never() throws Exception {
		dump(Observable.never());
	}
}

