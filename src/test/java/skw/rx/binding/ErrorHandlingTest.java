package skw.rx.binding;

import static skw.rx.util.Dumper.dump;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

public class ErrorHandlingTest {
	private Observable<String> birds;
	private Observable<String> fish;
	private Observable<Object> birdsThenError;
	
	
	@Before
	public void before() {
		birds = Observable.from("robin", "chaffinch", "jay");
		fish = Observable.from("salmon", "perch", "trout");
		birdsThenError = Observable.concat(birds, Observable.error(new Exception("Bad stuff")));
	}
	
	@Test
	public void onErrorResumeNext() {
		dump(birdsThenError.onErrorResumeNext(fish));
	}
	
	@Test
	public void onErrorReturn() {
		dump(birdsThenError.onErrorReturn((error) -> "recovery item for " + error));
	}
}
