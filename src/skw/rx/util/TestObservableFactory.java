package skw.rx.util;

import rx.Observable;

public class TestObservableFactory {
	public String value = "old value";
	
	public Observable<String> createObservable() {
		return Observable.just(value);
	}
}