package skw.rx.binding;

import static skw.rx.util.Dumper.dump;
import static skw.rx.util.Dumper.dumpForTimePeriod;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class TransformingObservablesTest {
	private Observable<String> family;
	private Observable<String> people;
	private Observable<String> morePeople;
	Func1<String, String> surname;
	
	@Before
	public void before() {
		family = Observable.from("Spencer", "Mitchell", "Sue");
		people = Observable.from("Spencer Ward", "Leon Jones", "Mitchell Ward", 
								 "Sue Ward", "Camden Jones", "Cynthia Jones",
								 "Bailey Jones", "Mia Ward");
		morePeople = Observable.from("Luke Jones", "David Ward", "Ann Ward");
		surname = (name) -> name.substring(name.indexOf(" "));
	}
	
	@Test
	public void map() throws Exception {
		dump(family.map((name) -> name + " Ward"));
	}
	
	@Test
	public void flatMap() throws Exception {
		Observable<String> bands = Observable.from("Radiohead", "Sparklehorse");
		dump(bands.flatMap((band) -> getSongs(band)));
	}
	
	@Test
	public void scan() throws Exception {
		dump(family.scan((a, b) -> a + "," + b));
	}
	
	@Test
	public void groupBy() throws Exception {
		dump(people.groupBy(surname));
	}

	@Test
	public void pivot() throws Exception {
		Observable<GroupedObservable<String, String>> groupedPeople 
			= people.groupBy(surname);
		Observable<GroupedObservable<String, String>> groupedMorePeople 
			= morePeople.groupBy(surname);
		Observable<GroupedObservable<String, GroupedObservable<String, String>>> groups 
			= Observable.from(GroupedObservable.from("people", groupedPeople), 
							  GroupedObservable.from("morePeople", groupedMorePeople));
		dump(Observable.pivot(groups));
	}
	
	@Test
	public void buffer() throws Exception {
		dump(people.buffer(3));
	}
	
	@Test
	public void bufferByTime() throws Exception {
		dumpForTimePeriod(Observable.interval(10, TimeUnit.MILLISECONDS).buffer(25, TimeUnit.MILLISECONDS), 50);
	}

	@Test
	public void window() throws Exception {
		dump(people.window(3));
	}
	
//	@Test
//	public void windowByTime() throws Exception {
//		// rxJava defect - unsubscribe does not work in this case.
////		dump(Observable.interval(10, TimeUnit.MILLISECONDS).window(25, TimeUnit.MILLISECONDS), 50);
//	}
	
	@Test
	public void cast() throws Exception {
		class Turnip {}
		dump(people.cast(Turnip.class));
	}

	
	public Observable<String> getSongs(String band) {
		switch(band) {
			case "Radiohead":
				return Observable.from("Creep", "Paranoid Android");
			case "Sparklehorse":
				return Observable.from("King of Nails", "Happy Man");
			default:
				return Observable.empty();
		}
	}
}
