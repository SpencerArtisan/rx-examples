package skw.rx.util;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.TimeUnit;

import javax.print.attribute.ResolutionSyntax;

import org.junit.Before;
import org.junit.Test;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

public class UtilityOperatorsTest {
	private Observable<Long> intervalObservable;
	private Observable<String> birds;
	private Observable<String> observableOnOtherThread;
	
	@Before
	public void before() {
		intervalObservable = Observable.interval(10, TimeUnit.MILLISECONDS);
		birds = Observable.from("robin", "chaffinch", "jay");
		observableOnOtherThread = Observable.just("alien", Schedulers.newThread());
	}
	
	@Test
	public void materialize() throws Exception {
		dump(birds.materialize());
	}
	
	@Test
	public void dematerialize() throws Exception {
		dump(birds.materialize().dematerialize());
	}
	
	@Test
	public void timestamp() throws Exception {
		dump(birds.timestamp());
	}
	
	@Test
	public void cache() throws Exception {
		Observable<Timestamped<String>> cached = birds.timestamp().cache();
		dump(cached);
		Thread.sleep(10);
		dump(cached);
	}
	
	@Test
	public void observeOn() throws Exception {
		dumpWithThreadId(observableOnOtherThread.observeOn(Schedulers.io()));
	}
	
	@Test
	public void subscribeOn() throws Exception {
		dumpWithThreadId(birds);
		dumpWithThreadId(birds.subscribeOn(Schedulers.computation()));
		dumpWithThreadId(birds.subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()));
	}
	
	@Test
	public void parallel() throws Exception {
		Observable<Integer> numbers = Observable.range(1, 20);
		dump(numbers.parallel((observable) -> 
			observable.map((item) -> observable.toString() + ": " + item)));
	}
	
	@Test
	public void doOnEach() throws Exception {
		dump(birds.doOnEach((item) -> System.out.println("Side effect for " + item)));
	}

	@Test
	public void doOnCompleted() throws Exception {
		dump(birds.doOnCompleted(() -> System.out.println("Side effect on completion")));
	}
	
	@Test
	public void doOnTerminate() throws Exception {
		dump(birds.doOnTerminate(() -> System.out.println("Side effect on terminate")));
	}
	
	@Test
	public void doOnFinally() throws Exception {
		dump(birds.finallyDo(() -> System.out.println("Side effect on finally")));
	}
	
	@Test
	public void doOnError() throws Exception {
		dump(Observable.error(new Exception("Bad stuff")).doOnError((e) -> System.out.println("Side effect on error " + e)));
	}
	
	@Test
	public void delay() throws Exception {
		dumpForTimePeriod(intervalObservable.delay(50, TimeUnit.MILLISECONDS), 100);
	}

	@Test
	public void delaySubscription() throws Exception {
		dumpForTimePeriod(intervalObservable.delaySubscription(50, TimeUnit.MILLISECONDS), 100);
	}
	
	@Test
	public void timeInterval() throws Exception {
		dumpForTimePeriod(intervalObservable.timeInterval(), 100);
	}
	
	@Test
	public void using() throws Exception {
		dump(Observable.using(() -> { return new TestResource(); }, (resource) -> Observable.from(1,2)));
	}
	
	@Test
	public void singleWhenOne() throws Exception {
		dump(Observable.just("one thing").single());
	}
	
	@Test
	public void singleWhenMany() throws Exception {
		dump(Observable.from("two", "things").single());
	}

	@Test
	public void singleWhenZero() throws Exception {
		dump(Observable.empty().single());
	}
	
	@Test
	public void singleOrDefaultWhenOne() throws Exception {
		dump(Observable.just("one thing").singleOrDefault("default"));
	}
	
	@Test
	public void singleOrDefaultWhenMany() throws Exception {
		dump(Observable.from("two", "things").singleOrDefault("default"));
	}
	
	@Test
	public void singleOrDefaultWhenZero() throws Exception {
		dump(Observable.empty().singleOrDefault("default"));
	}
	
	@Test
	public void singleByPredicate() throws Exception {
		dump(Observable.from("two", "things").single((item) -> item.endsWith("s")));
	}	
}

class TestResource implements Subscription {
	public boolean isUnsubscribed() {
		return false;
	}

	public void unsubscribe() {
		System.out.println("Disposing of resource");
	}
}
