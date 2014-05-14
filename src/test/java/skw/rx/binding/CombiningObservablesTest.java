package skw.rx.binding;

import static skw.rx.util.Dumper.dump;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

public class CombiningObservablesTest {
	private Observable<String> helloWorld;
	private Observable<String> colours;
	private Observable<Observable<Integer>> observableOfObservables;
	
	@Before
	public void before() {
		helloWorld = Observable.from("hello", "world");
		colours = Observable.from("red", "blue", "green");
		observableOfObservables = Observable.range(0, 20).window(4);
	}
	
	@Test
	public void startWith() throws Exception {
		dump(helloWorld.startWith("always", "start", "with"));
	}
	
	@Test
	public void mergeWithListOfObservables() throws Exception {
		dump(Observable.merge(helloWorld, colours));
	}

	@Test
	public void mergeWithObservableOfObservables() throws Exception {
		dump(Observable.merge(observableOfObservables));
	}
	
	@Test
	public void mergeDelayError() throws Exception {
		dump(Observable.mergeDelayError(Observable.error(new Exception("bad thing")), helloWorld));
	}
	
	@Test
	public void parallelMerge() throws Exception {
		dump(Observable.parallelMerge(observableOfObservables, 2));
	}
	
	@Test
	public void zip() throws Exception {
		dump(helloWorld.zip(colours, (h, c) -> h + c));
	}
	
	@Test
	public void zipWithObservableOfObservables() throws Exception {
		dump(Observable.zip(observableOfObservables, (items) -> Arrays.toString(items)));
	}
	
	@Test
	public void combineLatest() throws Exception {
		dump(Observable.combineLatest(helloWorld, colours, (a,b) -> a + b));
	}
	
	@Test
	public void switchOnNext() throws Exception {
		dump(Observable.switchOnNext(observableOfObservables));
	}
	
	
}
