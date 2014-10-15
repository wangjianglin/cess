package lin.client.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import lin.client.http.packages.SessionIdPackage;
import lin.client.http.packages.TestPackage;

public class HttpCommunicateTest {

	@Test
	public void testHttp() throws IOException, URISyntaxException{
		TestPackage pack = new TestPackage();
		pack.setData("测试中文！");
		//pack.setData("test");
		HttpCommunicateImpl client = HttpCommunicate.get("clien");
		//client.setCommUrl(new URI("http://localhost:8080/web/__http_comm_protocol__"));
		client.setCommUrl(new URI("http://localhost/__http_comm_protocol__"));
		//client.setCommUrl(new URI("http://localhost/core/comm/test.action"));
		client.setCommUrl(new URI("http://localhost:8080/"));
		client.request(pack, (Object obj, List<Error> warning)-> {
				System.out.println("------------"+obj);
			},error -> {
				System.out.println("error!");
			}
		).WaitForEnd();
		
		
		//System.in.read();
	}
	
	@Test
	public void testSessionId()throws Exception{
		SessionIdPackage sessionPack = new SessionIdPackage();
		//pack.setData("测试中文！");
		//pack.setData("test");
		//HttpCommunicate.setCommUrl(new URI("http://10.100.3.28:8080/web"));
		class TmpResultListener implements ResultListener {
			//HttpCommunicate.request(sessionPack, new ResultListener() {
			private String name = "";
			public TmpResultListener(String name){
				this.name = name;
			}
			@Override
			public void result(Object obj, List<Error> warning) {
				System.out.println(name+":"+obj);
			}
			
			@Override
			public void progress(long count, long total) {
				
			}
			
			@Override
			public void fault(Error error) {
				System.out.println("error!");
			}
		};
		HttpCommunicate.setCommUrl(new URI("http://localhost:8080"));
		for(int n=0;n<10;n++){
			HttpCommunicate.get("name"+n).setCommUrl(new URI("http://localhost:8080/"));
			HttpCommunicate.get("name"+n).request(sessionPack, new TmpResultListener("name"+n)).WaitForEnd();
		}
		
		for(int n=0;n<10;n++){
			//HttpCommunicate.get("name"+n).setCommUrl(new URI("http://localhost:8080/sync-client"));
			HttpCommunicate.request(sessionPack, new TmpResultListener("Global")).WaitForEnd();
		}
		
		System.out.println("end!");
	}
}
