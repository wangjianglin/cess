package lin.client.http;

import java.util.Map;

import org.apache.http.message.AbstractHttpMessage;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:04
 *
 */
public interface HttpRequestHandle {
	
	Map<String,String> getParams(AbstractHttpMessage httpMessage,lin.client.http.Package pack);

    void response(lin.client.http.Package pack, String resp, ResultListener listener);
}
