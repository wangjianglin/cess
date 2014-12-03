package lin.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import lin.client.http.Error;
import lin.client.http.HttpCommunicate;
import lin.client.http.ResultListener;

public class MapPackageTest {

	@Test
	public void testMapPackage() throws URISyntaxException{
		HttpCommunicate.setCommUrl(new URI("http://localhost:8080/web"));
		MapPackage pack = new MapPackage();
		Map<String,String> datas = new HashMap<>();
		datas.put("data", "ok");
		pack.setData("data");
		pack.setDatas(datas);
		
		HttpCommunicate.request(pack, new ResultListener(){

			@Override
			public void result(Object obj, List<Error> warning) {
				System.out.println("ok.");
			}

			@Override
			public void fault(Error error) {
				System.out.println("fault.");
			}

			@Override
			public void progress(long count, long total) {
				
			}}).waitForEnd();;
	}
}
