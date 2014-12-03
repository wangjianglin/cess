package lin.client.http;

import java.util.Map;

/**
 * 
 * @author 王江林
 * @date 2013年12月27日 上午10:19:00
 *
 */
public class FileDownloadPackage extends Package{
	private static class FileDownloadHttpRequestListener implements HttpRequestHandle{

		@Override
		public Map<String, String> getParams(Package pack) {
			return null;
		}

		@Override
		public void response(Package pack, String resp, ResultListener listener) {
			
		}
		
	}
	private static HttpRequestHandle HttpRequestHandle = new FileDownloadHttpRequestListener();
	
	private String key;
	
	public FileDownloadPackage(){
		this(null);
	}
	
	public FileDownloadPackage(String key){
		this.key = key;
		this.setRequestHandle(HttpRequestHandle);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
