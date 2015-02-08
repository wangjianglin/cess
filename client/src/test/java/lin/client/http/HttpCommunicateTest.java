package lin.client.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class HttpCommunicateTest {

	private Object obj = new Object();
	private int c = 0;
	@Test
	public void testHttp() throws Exception{
		int M = 700;
		for(int n=0;n<M;n++){
			Thread thread = new Thread(()->{
				//System.out.println("thread:"+n);
				double N = 50;
				Date start = new Date();
				for(int m=0;m<N;m++){
					try {
						testHttpImpl();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Date end = new Date();
				//synchronized(HttpCommunicateTest.class.this){
				synchronized(obj){
					System.out.println("avg:"+(end.getTime()-start.getTime())/N);
					c++;
				}
			});
			thread.start();
		}
		while(c < M){
			Thread.sleep(100);
		}
	}
	private void testHttpImpl()throws IOException, URISyntaxException{
		TestPackage pack = new TestPackage();
		pack.setData("测试中文！");
		//pack.setData("test");
		HttpCommunicateImpl client = HttpCommunicate.get("clien");
		//client.setCommUrl(new URI("http://localhost:8080/web/__http_comm_protocol__"));
		client.setCommUrl(new URI("http://localhost/__http_comm_protocol__"));
		//client.setCommUrl(new URI("http://localhost/core/comm/test.action"));
		client.setCommUrl(new URI("http://localhost:8080/lin.demo"));
		client.request(pack, (Object obj, List<Error> warning)-> {
				//System.out.println("------------"+obj);
			},error -> {
				System.out.println("error:"+error.getCode());
			}
		).waitForEnd();
		
		
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
			HttpCommunicate.get("name"+n).request(sessionPack, new TmpResultListener("name"+n)).waitForEnd();
		}
		
		for(int n=0;n<10;n++){
			//HttpCommunicate.get("name"+n).setCommUrl(new URI("http://localhost:8080/sync-client"));
			HttpCommunicate.request(sessionPack, new TmpResultListener("Global")).waitForEnd();
		}
		
		System.out.println("end!");
	}
	
	@Test
	public void testComm()throws IOException, URISyntaxException{
//		TestPackage pack = new TestPackage();
		TestPackage2 pack = new TestPackage2();
//		pack.setData("测试中文！");
		pack.setData("test!");
		//pack.setData("test");
		HttpCommunicateImpl client = HttpCommunicate.get("clien");
		//client.setCommUrl(new URI("http://localhost:8080/web/__http_comm_protocol__"));
		//client.setCommUrl(new URI("http://localhost/__http_comm_protocol__"));
		//client.setCommUrl(new URI("http://localhost/core/comm/test.action"));
		client.setCommUrl(new URI("http://localhost:8080/lin.demo/"));
		client.request(pack, (Object obj, List<Error> warning)-> {
				System.out.println("------------"+obj);
			},error -> {
				System.out.println("error:"+error.getCode());
			}
		).waitForEnd();
		
		
		//System.in.read();
	}
}
