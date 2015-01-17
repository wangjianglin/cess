package lin.client.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;


/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:00
 *
 */
public class HttpRequest {

	private lin.client.http.Package pack;
	private ResultListener listener;
	//private HttpCommunicateResult result;
	private HttpCommunicateImpl impl;
	/**
	 * json数据的参数名
	 */
//	public static final String JSON_PARAM = "__jsonParam__";
	/**
	 * 客户端请求数据的编码参数方式
	 */
//	public static final String REQUEST_CODING = "__request_coding__";
	
	/**
	 * uri的属性名
	 */
//	public static final String URI = "__uri__";
	
	//public static final String VERSION = "__version__";
//	public static final String VERSION = "0.1";
//	private static final String HTTP_COMM_PROTOCOL = "__http_comm_protocol__";
	public HttpRequest(HttpCommunicateImpl impl,lin.client.http.Package pack,ResultListener listener, HttpCommunicateResult result,CloseableHttpClient http){
		this.impl = impl;
		this.pack = pack;
		this.listener = listener;
		this.http = http;
		//this.result = result;
	}
	private HttpPost post = null;
	void abort(){
		//result.lock.lock();
		try{
			if(post != null){
				post.abort();
			}
		}
		finally{
			//result.lock.unlock();
		}
	}
	private CloseableHttpClient http;// = HttpClients.createDefault();
	public void request(){
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				//result.lock.lock();
					HttpResponse response;
					String jsonParam = null;
					long errorCode = 0;
					try {
						//HTTP请求
//						DefaultHttpClient http = new DefaultHttpClient();
						//CloseableHttpClient http = HttpClientBuilder.create().build();
//						http.setCookieStore(impl.getCookieStore());
						post = new HttpPost(HttpUtils.uri(impl,pack));
						//post.addHeader(Constants.HTTP_COMM_PROTOCOL, Constants.HTTP_VERSION);
						//http.setCookieSpecs(new CookieSpecRegistry());
						Map<String,String> postParams = pack.getRequestHandle().getParams(post,pack);
						List<NameValuePair> params = new 
								ArrayList<NameValuePair>();  
		
								                //params.add(new BasicNameValuePair
						if(postParams != null && postParams.size()>0){
							for(String key : postParams.keySet()){
								params.add(new BasicNameValuePair(key,postParams.get(key)));
							}
						}
						try {
							post.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(params));
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
					//entity = new 
						response = http.execute(post);
						HttpEntity entity = response.getEntity();
						StringBuffer buffer = new StringBuffer();
						InputStream _in = entity.getContent();
						byte bs[] = new byte[4096];
						int count = 0;
						while((count = _in.read(bs)) != -1){
							buffer.append(new String(bs,0,count));
						}
						jsonParam = buffer.toString();
//						jsonParam = URLDecoder.decode(jsonParam, "utf-8");
//						//jsonParam = jsonParam;
//						//byte[] tmpBs = new BASE64Decoder().decodeBuffer(jsonParam);
//						byte[] tmpBs = Base64.getDecoder().decode(jsonParam);
//						jsonParam = new String(tmpBs,"utf-8");
						//System.out.println("json:"+tmpJsonParams);
						//jsonParam = new String(jsonParam.getBytes(),System.getProperty("sun.jnu.encoding"));
						
						//System.out.println("data:"+buffer.toString());
						//System.out.println("result:"+jsonParam);
					} catch (Throwable e) {
						//e.printStackTrace();
						Error error = new Error();
						error.setCode(errorCode);
						HttpUtils.fireFault(listener::fault, error);
						return;
					}
						pack.getRequestHandle().response(pack, jsonParam, listener);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
