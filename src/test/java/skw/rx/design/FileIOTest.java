package skw.rx.design;

import java.io.FileReader;
import java.net.URL;

import org.junit.Test;

import rx.observables.StringObservable;

public class FileIOTest {
	@Test
	public void readFile() throws Exception {
		URL resource = getClass().getResource("test.txt");
		FileReader fileReader = new FileReader(resource.getPath());
		StringObservable
			.from(fileReader)
			.subscribe((x) -> System.out.println(x.toUpperCase()));
	}
}
