package lin.client.http;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:02:38
 *
 */
class HttpUtils {


 static void fireResult(ResultFunction listener,Object obj,List<Error> warning){
	if(listener != null){
		listener.result(obj, warning);
	}
}
 static void fireProgress(ProgressFunction listener,long count,long total){
	if(listener != null){
		listener.progress(count, total);
	}
}
 static void fireFault(FaultFunction listener,Error error){
	if(listener != null){
		listener.fault(error);
	}
}

	private static long _Sequeue = 0;
    /// <summary>
    /// 序列号
    /// </summary>
    public synchronized static long getSequeue() { 
    	_Sequeue++; 
    	return (long)((1L + _Sequeue + Long.MAX_VALUE) % (Long.MAX_VALUE + 1L));
    }
    //private static final long offset = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc).Ticks;
    /// <summary>
    /// 时间戳，以伦敦时间1970-01-01 00:00:00.000为基准的毫秒数
    /// </summary>
    public static long getTimestamp() { return new Date().getTime(); }
    
	public static String uri(HttpCommunicateImpl impl,lin.client.http.packages.Package pack){
		URI commUri = impl.getCommUri();
		String uri = null;
		String commUriString = null;
		//String uriPath = pack.getLocation();
//		String uriPath = "__http_comm_protocol__";
//		 if (pack.getUrlType() == UrlType.RELATIVE)
//         {
             commUriString = commUri.toString();

             if(pack.getUri().endsWith("/")){
            	 commUriString += pack.getUri().substring(1);
             }else{
            	 commUriString += pack.getUri();
             }
             if (!pack.isEnableCache())
             {
                 if (commUriString.contains("?"))
                 {
                     uri = commUriString  + "&_time_stamp_" + (new Date()).getTime() + "=1";
                 }
                 else
                 {
                     uri = commUriString +  "?_time_stamp_" + (new Date()).getTime() + "=1";
                 }
             }
//         }
//         else
//         {
//             //HttpCommunicate.CommUri.
//             String tmpUrl = null;
//             if (pack.getLocation().startsWith("/"))
//             {
//                 tmpUrl = commUri.getScheme() + "://" + commUri.getHost() + ":" + commUri.getPort() + pack.getLocation();
//             }
//             else
//             {
//                 tmpUrl = pack.getLocation();
//             }
//             if (pack.isEnableCache())
//             {
//                 uri = tmpUrl;
//             }
//             else
//             {
//                 if (pack.getLocation().endsWith("?"))
//                 {
//                     uri = tmpUrl + "&_time_stamp_" + (new Date()).getTime() + "=1";
//                 }
//                 else
//                 {
//                     uri = tmpUrl + "?_time_stamp_" + (new Date()).getTime() + "=1";
//                 }
//             }
//         }
		return uri;
	}
}
