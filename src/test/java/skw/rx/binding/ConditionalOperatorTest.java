package skw.rx.binding;

import static skw.rx.util.Dumper.dump;
import static skw.rx.util.Dumper.dumpForTimePeriod;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

public class ConditionalOperatorTest {
	private Observable<String> firstOut;
	private Observable<String> secondOut;
	private Observable<String> lyrics;
	
	@Before
	public void before() {
		firstOut = Observable.from("I", "am", "first").delay(10, TimeUnit.MILLISECONDS);
		secondOut = Observable.from("I", "am", "second").delay(20, TimeUnit.MILLISECONDS);
		lyrics = Observable.from("Zurich", "is", "stained", "and", "it's", "not", "my", "fault");
	}
	
	@Test
	public void amb() throws Exception {
		dumpForTimePeriod(Observable.amb(firstOut, secondOut), 50);
	}
	
	@Test
	public void defaultIfEmpty() throws Exception {
		dump(Observable.empty().defaultIfEmpty("default"));
	}
	
	@Test
	public void skipUntil() throws Exception {
		Observable<String> emitAfter10 = Observable.just("some value").delay(10, TimeUnit.MILLISECONDS);
		dumpForTimePeriod(Observable.interval(1, TimeUnit.MILLISECONDS).skipUntil(emitAfter10), 20);
	}
	
	@Test
	public void skipWhile() throws Exception {
		dump(lyrics.skipWhile((word) -> !word.equals("and")));
	}
	
	@Test
	public void skipWhileWithIndex() throws Exception {
		dump(lyrics.skipWhileWithIndex((word, i) -> i < 2));
	}
	
	@Test
	public void takeUntil() throws Exception {
		Observable<String> emitAfter10 = Observable.just("some value").delay(10, TimeUnit.MILLISECONDS);
		dumpForTimePeriod(Observable.interval(1, TimeUnit.MILLISECONDS).takeUntil(emitAfter10), 20);
	}
	
	@Test
	public void takeWhile() throws Exception {
		dump(lyrics.takeWhile((word) -> !word.equals("and")));
	}
	
	@Test
	public void takeWhileWithIndex() throws Exception {
		dump(lyrics.takeWhileWithIndex((word, i) -> i < 2));
	}
	
	@Test
	public void all() throws Exception {
		dump(lyrics.all((word) -> word.length() < 10));
	}
	
	@Test
	public void contains() throws Exception {
		dump(lyrics.contains("and"));
	}
	
	@Test
	public void isEmpty() throws Exception {
		dump(Observable.empty().isEmpty());
	}
	
	@Test
	public void exists() throws Exception {
		dump(lyrics.exists((word) -> word.endsWith("s")));
	}
	
	@Test
	public void sequenceEqual() throws Exception {
		dump(Observable.sequenceEqual(lyrics, lyrics));
	}
}
