package skw.rx.anamorphisms;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.util.async.Async;

public class ConnectableObservableTest {
	private ConnectableObservable<String> connectableFilms;
	private Observable<String> films;

	@Before
	public void before() {
		connectableFilms = Observable.from("suspicion", "notorious", "psycho").publish();
		films = Observable.from("Virgin Spring", "Wild Strawberries", "Seventh Seal");
	}

	@Test
	public void connect() throws Exception {
		dump(connectableFilms);
		dump(connectableFilms);
		connectableFilms.connect();
	}
	
	@Test
	public void publishLast() throws Exception {
		ConnectableObservable<String> publishLast = films.publishLast();
		dump(publishLast);
		publishLast.connect();
	}
	
	@Test
	public void replay() throws Exception {
		ConnectableObservable<Long> hot = Observable.interval(10, TimeUnit.MILLISECONDS)
				.take(3)
				.publish();
		
		hot.connect();
		Thread.sleep(10);
		ConnectableObservable<Long> observable = hot.replay();
		observable.connect();
		dumpForTimePeriod(observable, 10);
		dumpForTimePeriod(observable, 10);
	}
	
	@Test
	public void refCount() throws Exception {
		Observable<String> refCountedFilms = connectableFilms.refCount();
		dump(refCountedFilms);
	}
}
