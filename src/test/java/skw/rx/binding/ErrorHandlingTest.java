package skw.rx.binding;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.plaf.ListUI;

import jdk.nashorn.internal.ir.LiteralNode.ArrayLiteralNode.ArrayUnit;
import skw.rx.util.TestObservableFactory;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

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
