package lin.client.http;

import java.util.Map;

import lin.client.http.packages.Package;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:17
 *
 */
public class NoneHttpRequestHandle implements HttpRequestHandle {

	@Override
	public Map<String, String> getParams(Package pack) {
		return null;
	}

	@Override
	public void response(Package pack, String resp, ResultListener listener) {
		
	}

}
