package skw.rx.util;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.observables.GroupedObservable;

public class Dumper {
	public static void dump(Observable<?> observable, long milliseconds) throws InterruptedException {
//		System.out.println();
//		System.out.println(getCallingMethod());
//		
		Subscription subscription = dumpObservable(observable);
		Thread.sleep(milliseconds);
		subscription.unsubscribe();
	}
	
	public static void dump(Observable<?> observable) {
//		System.out.println();
//		System.out.println(getCallingMethod());
//		
		dumpObservable(observable);
	}

	private static Subscription dumpObservable(Observable<?> observable) {
		return observable.subscribe((value) -> dumpValue(value),
							 (e) -> System.out.println(" ✖ " + e.getMessage()),
							 () -> System.out.println(" ‾"));
	}

	private static void dumpValue(Object value) {
		if (value instanceof Observable) {
			dumpNestedObservable((Observable) value);
		} else {
			System.out.println(" • " + value);
		}
	}

	private static Subscription dumpNestedObservable(Observable<?> observable) {
		return observable.reduce(new ArrayList<String>(), (a, b) -> {
			a.add(toString(b));
			return a;
		}).subscribe((value) -> dumpArrayAsObservable(value));	
	}

	private static String toString(Object value) {
		if (value instanceof GroupedObservable<?, ?>) {
			return "Grouped Observable";
		}
		return value.toString();
	}

	private static void dumpArrayAsObservable(ArrayList<String> values) {
		System.out.println(" |→•" + String.join("→•", values) + "→|");
	}

	private static StackTraceElement getCallingMethod() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		return stackTraceElements[3];
	}
}