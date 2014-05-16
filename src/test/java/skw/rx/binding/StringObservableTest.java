package skw.rx.binding;

import static org.junit.Assert.*;
import static skw.rx.util.Dumper.dump;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observables.StringObservable;

public class StringObservableTest {
	@Test
	public void byLine() throws Exception {
		Observable<String> lyrics = Observable.from(
				"Canyon bro\r\nyour life is quirked\r\n", 
				"dream about the witch trials\r\n", 
				"you get all too\r\nlies peppered in your forecast\r\n", 
				"Beneath the shady mezzanine\r\nkeep it when you wanna be loved\r\n");
		dump(StringObservable.byLine(lyrics));
	}
	
	@Test
	public void from() throws Exception {
		StringReader reader = new StringReader(
				"Rotten device, I'll say it twice" + 
				"I'm too much, I'm too much comforted here" +
				"Costs too much too much, we'll leave you" +
				"Everywhere eyes, nowhere to die");
		dump(StringObservable.from(reader, 10));
	}
	
	@Test
	public void join() throws Exception {
		Observable<String> fragments = Observable.from(
				"This", " is a song", " about lonely edward ", 
				"franklin ames,", " out of a job, confused.", 
				" steadies himself as a taker. has his ", "two children");
		dump(StringObservable.join(fragments, ""));
	}
	
	@Test
	public void split() throws Exception {
		Observable<String> fragments = Observable.from(
				"I knitt", "ed them for you", " it was a pri", "vate, private gift ",
				"did you tell your mother if she hears about it ", "she'll feel bad");
		dump(StringObservable.split(fragments, " "));
	}
	
	@Test
	public void stringConcat() throws Exception {
		Observable<String> fragments = Observable.from(
				"Pick out some Brazilian n", "uts For your ", "engagement Check ", 
				"that expirat", "ion date, man I", "t's later than you think");
		dump(StringObservable.stringConcat(fragments));
	}
}
