package lin.client.http;


import java.util.List;

/**
 * 
 * @author lin
 * @date 2014年5月15日 下午9:13:14
 *
 */
@FunctionalInterface
public interface ResultFunction {

	public void result(Object obj, List<Error> warning);
}
