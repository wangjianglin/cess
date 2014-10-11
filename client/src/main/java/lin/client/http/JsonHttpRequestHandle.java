package lin.client.http;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import lin.client.http.packages.Package;
import lin.util.json.JSONException;
import lin.util.json.JSONUtil;
import lin.util.reflect.PropertyOperator;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:03:13
 *
 */
public class JsonHttpRequestHandle implements HttpRequestHandle {

	public static class ResultData{
		private long code;
		private long sequeueid;// { get; set; }
	        //public object result { get; set; }
		private String message;// { get; set; }
		private List<Error> warning;// { get; set; }

		private String cause;// { get; set; }

		private String stackTrace;// { get; set; }

		private int dataType ;//{ get; set; }
	        
		public long getCode() {
			return code;
		}
		public void setCode(long code) {
			this.code = code;
		}
		public long getSequeueid() {
			return sequeueid;
		}
		public void setSequeueid(long sequeueid) {
			this.sequeueid = sequeueid;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List<Error> getWarning() {
			return warning;
		}
		public void setWarning(List<Error> warning) {
			this.warning = warning;
		}
		public String getCause() {
			return cause;
		}
		public void setCause(String cause) {
			this.cause = cause;
		}
		public String getStackTrace() {
			return stackTrace;
		}
		public void setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
		}
		public int getDataType() {
			return dataType;
		}
		public void setDataType(int dataType) {
			this.dataType = dataType;
		}
	}
	@Override
	public Map<String, String> getParams(Package pack) {
		StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"location\":");
        sb.append("\"" + pack.getUri() + "\",");
        sb.append("\"timestamp\":");
        sb.append(HttpUtils.getTimestamp());
        sb.append(",\"sequeueid\":");
        sb.append(HttpUtils.getSequeue());

        //增加版本信息
        sb.append(",\"version\":{\"major\":");
        sb.append(pack.getVersion().getMajor());
        sb.append(",\"minor\":");
        sb.append(pack.getVersion().getMinor());
        sb.append("}");

        //增加参数信息
        sb.append(",\"data\":");
        Map<String, Object> dict = pack.getParams();
        //AD.Core.Web.Json.DataContractJsonSerializer dc = new AD.Core.Web.Json.DataContractJsonSerializer(typeof(Dictionary<string, object>));
        ////String jsonString = "{}";
        //MemoryStream ms = new MemoryStream();
        //dc.WriteObject(ms, dict);
        //byte[] tmp = ms.ToArray();
        //sb.append(Encoding.UTF8.GetString(tmp, 0, tmp.Length));
        
            try {
				sb.append(JSONUtil.serialize(dict));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
        
        
        //sb.append("{\"data\":\"测试中文\"}");

        sb.append("}");
        
        String json = sb.toString();
        //json = new sun.misc.BASE64Encoder().encodeBuffer(json.getBytes());
        json = Base64.getEncoder().encodeToString(json.getBytes());
//        try {
//			json =  java.net.URLEncoder.encode(json, System.getProperty("sun.jnu.encoding"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
        
        //String b64s = System.Convert.ToBase64String(Encoding.Default.GetBytes(sb.ToString()));
        //Console.WriteLine("value:" + sb.ToString());
        //Console.WriteLine("b64s:" + b64s);
        //Console.WriteLine("coding:" + Encoding.Default.EncodingName);
       // return "jsonParam=" + HttpUtility.UrlEncode(b64s)+
        //    "&coding=" + Encoding.Default.BodyName;
        Map<String,String> map = new HashMap<>();
//        map.put("__jsonParam__", json);
//        map.put("__request_coding__", System.getProperty("file.encoding"));
//        map.put("__version__", "0.1");
//        map.put("__result__", "json");
        map.put(HttpRequest.JSON_PARAM, json);
        map.put(HttpRequest.REQUEST_CODING, System.getProperty("file.encoding"));
        //map.put(HttpRequest.VERSION, "0.1");
        //map.put(HttpRequest.URI, pack.getUri());
		return map;
	}

	@Override
	public void response(Package pack, String resp, ResultListener listener) {
		//System.out.println("result:"+resp);
		Object obj = null;
		List<Error> warning = null;
		Error error = null;
		try{
			obj  = lin.util.json.JSONUtil.deserialize(resp);
			ResultData resultData = (ResultData) lin.util.json.JSONUtil.deserialize(obj,ResultData.class);
			///ResultData resltData = (ResultData) ad.util.json.JSONUtil.deserialize(resp, ResultData.class);
			if(resultData.code <0){
				error = new Error();
				PropertyOperator.copy(resultData, error);
			}else{
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) obj;
				obj = lin.util.json.JSONUtil.deserialize(map.get("result"), pack.getRespType());
				warning = resultData.getWarning();
			}
		}catch(Throwable e){
			e.printStackTrace();
			error = new Error();
			error.setCode(-1);
			//return;
		}
		if(error != null){
			HttpUtils.fireFault(listener::fault, error);
		}else{
			HttpUtils.fireResult(listener::result,obj,warning);
		}
	}
	
	
}
