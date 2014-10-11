package lin.client.http;

import java.util.Map;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:04
 *
 */
public interface HttpRequestHandle {
	
	Map<String,String> getParams(lin.client.http.packages.Package pack);

    void response(lin.client.http.packages.Package pack, String resp, ResultListener listener);
}
