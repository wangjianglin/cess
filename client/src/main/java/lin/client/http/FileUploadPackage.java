package lin.client.http;

import java.io.File;
import java.util.Map;

import org.apache.http.message.AbstractHttpMessage;

/**
 * 
 * @author 王江林
 * @date 2013年12月27日 上午10:18:39
 *
 */
public class FileUploadPackage extends Package {

	private static class FileUploadHttpRequestListener implements HttpRequestHandle{

		@Override
		public Map<String, String> getParams(AbstractHttpMessage httpMessage,Package pack) {
			return null;
		}

		@Override
		public void response(Package pack, String resp, ResultListener listener) {
			
		}
		
	}
	private static HttpRequestHandle HttpRequestHandle = new FileUploadHttpRequestListener();
	
	private File file;
	
	 public FileUploadPackage(){
		 this(null);
	 }
	 
	 public FileUploadPackage(File file){
		 super("");
		 this.file = file;
		 this.setRequestHandle(HttpRequestHandle);
	 }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	 
}
