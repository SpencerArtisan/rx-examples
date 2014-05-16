package skw.rx.catamorphisms;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observables.BlockingObservable;
import rx.observables.ConnectableObservable;

public class BlockingOperatorTest {
	private BlockingObservable<String> blockingLyrics;
	private BlockingObservable<Object> empty;
	private Observable<String> lyrics;
	private BlockingObservable<String> oneThing;
	
	@Before
	public void before() {
		lyrics = Observable.from("eating", "her", "fingers", "like", "they're", "just", "another", "meal");
		blockingLyrics = lyrics.toBlockingObservable();
		empty = Observable.empty().toBlockingObservable();
		oneThing = Observable.just("unique").toBlockingObservable();
	}
	
	@Test
	public void forEach() throws Exception {
		blockingLyrics.forEach(System.out::println);
	}
	
	@Test
	public void first() throws Exception {
		System.out.println(blockingLyrics.first());
	}
	
	@Test
	public void firstOrDefault() throws Exception {
		System.out.println(empty.firstOrDefault("default"));
	}
	
	@Test
	public void last() throws Exception {
		System.out.println(blockingLyrics.last());
	}
	
	@Test
	public void lastOrDefault() throws Exception {
		System.out.println(empty.lastOrDefault("default"));
	}
	
	@Test
	public void mostRecent() throws Exception {
		ConnectableObservable<Long> hot = Observable.interval(2, TimeUnit.MILLISECONDS).take(3).publish();
		hot.connect();
				
		for (Long value : hot.toBlockingObservable().mostRecent(-1L)) {
			System.out.println(value);
		}
	}
	
	@Test
	public void next() throws Exception {
		ConnectableObservable<Long> hot = Observable.interval(10, TimeUnit.MILLISECONDS).take(5).publish();
		hot.connect();
		for (Long value: hot.toBlockingObservable().next()) {
			System.out.println(value);
			Thread.sleep(10);
		}
	}

	@Test
	public void latest() throws Exception {
		ConnectableObservable<Long> hot = Observable.interval(10, TimeUnit.MILLISECONDS).take(5).publish();
		hot.connect();
		for (Long value: hot.toBlockingObservable().latest()) {
			System.out.println(value);
			Thread.sleep(10);
		}
	}
	
	@Test
	public void single() throws Exception {
		System.out.println(oneThing.single());
	}

	@Test(expected=NoSuchElementException.class)
	public void singleWhenNone() throws Exception {
		empty.single();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void singleWhenMany() throws Exception {
		blockingLyrics.single();
	}
	
	@Test
	public void singleOrDefault() throws Exception {
		System.out.println(oneThing.single());
	}
	
	@Test
	public void singleOrDefaultWhenNone() throws Exception {
		System.out.println(empty.singleOrDefault("default"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void singleOrDefaultWhenMany() throws Exception {
		blockingLyrics.singleOrDefault("default");
	}
	
	@Test
	public void singleWithPredicateWhenMany() throws Exception {
		System.out.println(blockingLyrics.single((word) -> word.endsWith("t")));
	}
	
	@Test
	public void toIterable() throws Exception {
		blockingLyrics.toIterable().forEach(System.out::println);
	}
	
	@Test
	public void toIterator() throws Exception {
		Iterator<String> iterator = blockingLyrics.getIterator();
		iterator.forEachRemaining(System.out::println);
	}
	
	@Test
	public void toFutureWhenOne() throws Exception {
		Future<List<String>> future = lyrics.toList().toBlockingObservable().toFuture();
		System.out.println(future.get());
	}

	@Test(expected=ExecutionException.class)
	public void toFutureWhenMany() throws Exception {
		blockingLyrics.toFuture().get();
	}
}
