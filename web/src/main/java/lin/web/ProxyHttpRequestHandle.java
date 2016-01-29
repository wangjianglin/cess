package lin.web;


import lin.client.http1.AbstractHttpRequestHandle;
import lin.client.http1.HttpPackage;
import lin.client.http1.HttpUtils;
import lin.client.http1.ResultListener;


/**
 * 
 * @author lin
 * @date Mar 8, 2015 3:01:41 PM
 *
 *
 */
public class ProxyHttpRequestHandle extends AbstractHttpRequestHandle{


	@Override
	public void response(HttpPackage pack, byte[] bytes, ResultListener listener) {
//	public void response(lin.client.http1.HttpPackage pack, String resp, ResultListener listener){
//		Object obj = null;
//		List<Error> warning = null;
//		Error error = null;
//		try{
//			String resp = new String(bytes,"utf-8");
//			obj  = lin.util.json.JSONUtil.deserialize(resp);
////			ResultData resultData = (ResultData) lin.util.json.JSONUtil.deserialize(obj,ResultData.class);
//			///ResultData resltData = (ResultData) ad.util.json.JSONUtil.deserialize(resp, ResultData.class);
//			if(resultData.code <0){
//				error = new Error();
////				error.setCode(resultData.code);
////				error.setCause(resultData.cause);
////				error.setMessage(resultData.message);
////				error.setStackTrace(resultData.stackTrace);
//				PropertyOperator.copy(resultData, error);
//			}else{
//				@SuppressWarnings("unchecked")
//				Map<String,Object> map = (Map<String, Object>) obj;
//				obj = lin.util.json.JSONUtil.deserialize(map.get("result"), pack.getRespType());
//				warning = resultData.getWarning();
//			}
//		}catch(Throwable e){
//			e.printStackTrace();
//			error = new Error();
////			error.setCode(-1);
//			//return;
//		}
//		if(error != null){
////			HttpUtils.fireFault(listener::fault, error);
//		}else{
			HttpUtils.fireResult(listener::result,bytes,null);
//		}
	}


//	@Override
//	public void response(HttpPackage pack, byte[] bytes, ResultListener listener) {
//		// TODO Auto-generated method stub
//		
//	}

}
