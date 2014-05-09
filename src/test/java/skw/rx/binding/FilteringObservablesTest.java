package skw.rx.binding;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import skw.rx.util.TestObservableFactory;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class FilteringObservablesTest {
	private Observable<Double> examScores;
	
	@Before
	public void before() {
		examScores = Observable.from(91.8, 100.0, 64.2, 89.0, 74.9, 38.0);
	}
	
	@Test
	public void testy() {
		System.out.print("hello");
	}
//    @Test
//    public void deliberateFail() {
//        fail("bad stuff");
//    }
//
	@Test
	public void filter() {
		dump(examScores.filter((score) -> score > 70));
	}
	
	@Test
	public void takeLast() throws Exception {
		dump(examScores.takeLast(2));
	}
	
	@Test
	public void last() throws Exception {
		dump(examScores.last());
	}
	
	@Test
	public void lastOrDefault() throws Exception {
		dump(Observable.empty().lastOrDefault("default"));
	}
	
	@Test
	public void lastOrDefaultByPredicate() throws Exception {
		dump(examScores.lastOrDefault(0.0, (score) -> score < 20));
	}
	
	@Test
	public void firstOrDefault() throws Exception {
		dump(Observable.empty().firstOrDefault("default"));
	}
	
	@Test
	public void firstOrDefaultByPredicate() throws Exception {
		dump(examScores.firstOrDefault(0.0, (score) -> score < 20));
	}
	@Test
	public void takeLastBuffer() throws Exception {
		dump(examScores.takeLastBuffer(2));
	}
	
	@Test
	public void skip() throws Exception {
		dump(examScores.skip(2));
	}
	
	@Test
	public void skipLast() throws Exception {
		dump(examScores.skipLast(2));
	}
	
	@Test
	public void take() throws Exception {
		dump(examScores.take(2));
	}
	
	@Test
	public void first() throws Exception {
		dump(examScores.first());
	}
	
	@Test
	public void firstByPredicate() throws Exception {
		dump(examScores.first((score) -> score < 70));
	}
	
	@Test
	public void takeFirst() throws Exception {
		dump(examScores.takeFirst((score) -> score < 70));		
	}
	
	@Test
	public void firstByPredicateWhenNone() throws Exception {
		dump(Observable.<Integer>empty().first((score) -> score < 70));
	}
	
	@Test
	public void takeFirstWhenNone() throws Exception {
		dump(Observable.<Integer>empty().takeFirst((score) -> score < 70));		
	}
	
	@Test
	public void elementAt() throws Exception {
		dump(examScores.elementAt(4));
	}
}
