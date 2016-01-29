package lin.client.http1;

import java.util.Map;

import org.apache.http.message.AbstractHttpMessage;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:04
 *
 */
public interface HttpRequestHandle {
	
	Map<String,Object> getParams(AbstractHttpMessage httpMessage,lin.client.http1.HttpPackage pack);

    void response(lin.client.http1.HttpPackage pack, byte[] bytes, ResultListener listener);
}
