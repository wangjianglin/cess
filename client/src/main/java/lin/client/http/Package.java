package lin.client.http;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 上午11:58:23
 *
 */
public abstract class Package {
	public static final HttpRequestHandle JSON = new JsonHttpRequestHandle();
    public static final HttpRequestHandle NONE = new NoneHttpRequestHandle();
    private UrlType urlType = UrlType.RELATIVE;// { get; protected set; }
    
    //private static final Class<?> _DefautlRespType = String.class;

    static
    {
        
        //如果没有配置主版，则默认为无版本信息

    }

    /// <summary>
    /// 是否启用缓存，默认不启用
    /// </summary>
    //[DefaultValue(false)]
    private boolean enableCache;// { get; protected set; }
    public Package()
    {
        //EnableCache = false;
//        UrlType = UrlType.RELATIVE;
//        this.RequestHandle = JSON;
//        EnableCache = false;
//        //HasParams = true;
//        this.RespType = _DefautlRespType;
//        this.Version = new Version();
//        this.Version.Major = 0;
//        this.Version.Minor = 0;
    }
    private String uri;//{ get; set; }
    private Type respType  = String.class;//{ get;protected set; }

    /// <summary>
    /// 数据包的版本号
    /// </summary>
    private Version version = new Version(0,0);// { get; protected set; }


    /// <summary>
    /// 表示是否需要进行参数设置
    /// </summary>
    //public virtual bool HasParams { get; protected set; }

    private HttpRequestHandle requestHandle = JSON;
    public HttpRequestHandle getRequestHandle() { 
    	return requestHandle;
}
    protected void setRequestHandle(HttpRequestHandle handle) {
		this.requestHandle = handle;
	}
    public Map<String, Object> getParams()
    {
        return null;
    }
	public UrlType getUrlType() {
		return urlType;
	}
	public void setUrlType(UrlType urlType) {
		this.urlType = urlType;
	}
	public boolean isEnableCache() {
		return enableCache;
	}
	protected void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}
	public String getUri() {
		return uri;
	}
	protected void setUri(String uri) {
		this.uri = uri;
	}
	public Type getRespType() {
		return respType;
	}
	protected void setRespType(Type respType) {
		this.respType = respType;
	}
	public Version getVersion() {
		return version;
	}
	protected void setVersion(Version version) {
		this.version = version;
	}
}
