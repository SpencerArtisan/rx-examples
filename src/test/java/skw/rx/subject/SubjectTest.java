package skw.rx.subject;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.util.async.Async;

public class SubjectTest {
	private Observable<String> villages;

	@Before
	public void before() {
		villages = Observable.from("Malpas", "Binsted", "Brailes");
	}

	@Test
	public void asyncSubject() throws Exception {
		AsyncSubject<String> subject = AsyncSubject.create();
		subject.onNext("hello");
		subject.onNext("world");
		subject.onCompleted();
		dump(subject);
		dump(subject);
	}
	
	@Test
	public void behaviourSubject() throws Exception {
		BehaviorSubject<String> subject = BehaviorSubject.create("default");
		subject.onNext("hello");
		subject.onNext("world");
		dump(subject);
		subject.onCompleted();
	}
	
	@Test
	public void publishSubject() throws Exception {
		PublishSubject<String> subject = PublishSubject.create();
		dump(subject);
		subject.onNext("hello");
		subject.onNext("world");
		subject.onCompleted();
		dump(subject);
	}
	
	@Test
	public void replaySubject() throws Exception {
		ReplaySubject<String> subject = ReplaySubject.create();
		dump(subject);
		subject.onNext("hello");
		subject.onNext("world");
		subject.onCompleted();
		dump(subject);
	}
}
