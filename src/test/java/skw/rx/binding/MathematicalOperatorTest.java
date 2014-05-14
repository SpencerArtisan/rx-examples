package skw.rx.binding;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

public class MathematicalOperatorTest {
	private Observable<Double> raceTimes;
	private Observable<String> firstVerse;
	private Observable<String> secondVerse;

	@Before
	public void before() {
		raceTimes = Observable.from(50.7, 49.5, 49.9, 51.1, 47.7, 49.1);
		firstVerse = Observable.from("this", "is", "the", "first", "verse");
		secondVerse = Observable.from("this", "is", "the", "second", "verse");
	}
	
	@Test
	public void concat() throws Exception {
		dump(Observable.concat(firstVerse, secondVerse));
	}
	
	@Test
	public void count() throws Exception {
		dump(firstVerse.count());
	}
	
	@Test
	public void reduce() throws Exception {
		dump(firstVerse.reduce((agg, word) -> agg + " " + word));
	}
	
	@Test
	public void toList() throws Exception {
		dump(firstVerse.toList());
	}
	
	@Test
	public void toSortedList() throws Exception {
		dump(firstVerse.toSortedList());
	}
	
	@Test
	public void toMap() throws Exception {
		dump(firstVerse.toMap((word) -> word.substring(0, 1)));
	}
	
	@Test
	public void toMultiMap() throws Exception {
		dump(firstVerse.toMultimap((word) -> word.substring(0, 1)));
	}
}
