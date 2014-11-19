package lin.web;

/**
 * 
 * @author 王江林
 * @date 2012-7-3 上午10:50:03
 * 一些常用常量
 */
public class Constants {

//	/**
//	 * 
//	 */
//	public static final String CloudResult = "__result__";
//	
//	/**
//	 * 
//	 */
//	public static final String  ErrorCode = "exceptionCode";
	
	
	/**
	 * json数据的参数名
	 */
	public static final String JSON_PARAM = "__jsonParam__";
	/**
	 * 响应数据的类型，默认为html，支持html、json、xml
	 */
	//private static final String RESULT = "__result__";
	
	/**
	 * 数据通信协议版本的参数，无此参数，则表示不采用此种通信协议进行通信
	 */
	//private static final String VERSION = "__version__";
	/**
	 * 客户端请求数据的编码参数方式
	 */
	public static final String REQUEST_CODING = "__request_coding__";
	
	/**
	 * 客户端要求响应数据的编码方式，默认为utf-8
	 */
	//private static final String RESPONSE_CODING = "__response_coding__";
	
	public static final String HTTP_COMM_PROTOCOL = "__http_comm_protocol__";
	public static final String HTTP_COMM_PROTOCOL_VERSION = "/__http_comm_protocol__/__version__";
	public static final String VERSION = "0.1";
	
	public static final String DEFAULT_LOCATION = "/WEB-INF/content/";
	public static final String LOCATION_PARAM = "location";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 public static final String JSP_SERVLET_BASE = System.getProperty("org.apache.jasper.Constants.JSP_SERVLET_BASE", "org.apache.jasper.runtime.HttpJspBase");

	  public static final String SERVICE_METHOD_NAME = System.getProperty("org.apache.jasper.Constants.SERVICE_METHOD_NAME", "_jspService");
	  public static final String SERVLET_CONTENT_TYPE = "text/html";
	  public static final String[] STANDARD_IMPORTS = { "javax.servlet.*", "javax.servlet.http.*", "javax.servlet.jsp.*" };

	  public static final String SERVLET_CLASSPATH = System.getProperty("org.apache.jasper.Constants.SERVLET_CLASSPATH", "org.apache.catalina.jsp_classpath");

	  public static final String JSP_FILE = System.getProperty("org.apache.jasper.Constants.JSP_FILE", "org.apache.catalina.jsp_file");
	  public static final int DEFAULT_BUFFER_SIZE = 8192;
	  public static final int DEFAULT_TAG_BUFFER_SIZE = 512;
	  public static final int MAX_POOL_SIZE = 5;
	  public static final String PRECOMPILE = System.getProperty("org.apache.jasper.Constants.PRECOMPILE", "jsp_precompile");

	  public static final String JSP_PACKAGE_NAME = System.getProperty("org.apache.jasper.Constants.JSP_PACKAGE_NAME", "org.apache.jsp");

	  public static final String TAG_FILE_PACKAGE_NAME = System.getProperty("org.apache.jasper.Constants.TAG_FILE_PACKAGE_NAME", "org.apache.jsp.tag");
	  public static final String INC_SERVLET_PATH = "javax.servlet.include.servlet_path";
	  public static final String TMP_DIR = "javax.servlet.context.tempdir";
	  public static final String ALT_DD_ATTR = System.getProperty("org.apache.jasper.Constants.ALT_DD_ATTR", "org.apache.catalina.deploy.alt_dd");
	  public static final String TAGLIB_DTD_PUBLIC_ID_11 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";
	  public static final String TAGLIB_DTD_RESOURCE_PATH_11 = "/javax/servlet/jsp/resources/web-jsptaglibrary_1_1.dtd";
	  public static final String TAGLIB_DTD_PUBLIC_ID_12 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";
	  public static final String TAGLIB_DTD_RESOURCE_PATH_12 = "/javax/servlet/jsp/resources/web-jsptaglibrary_1_2.dtd";
	  public static final String WEBAPP_DTD_PUBLIC_ID_22 = "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN";
	  public static final String WEBAPP_DTD_RESOURCE_PATH_22 = "/javax/servlet/resources/web-app_2_2.dtd";
	  public static final String WEBAPP_DTD_PUBLIC_ID_23 = "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
	  public static final String WEBAPP_DTD_RESOURCE_PATH_23 = "/javax/servlet/resources/web-app_2_3.dtd";
	  public static final String[] CACHED_DTD_PUBLIC_IDS = { "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN", "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN", "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" };

	  public static final String[] CACHED_DTD_RESOURCE_PATHS = { "/javax/servlet/jsp/resources/web-jsptaglibrary_1_1.dtd", "/javax/servlet/jsp/resources/web-jsptaglibrary_1_2.dtd", "/javax/servlet/resources/web-app_2_2.dtd", "/javax/servlet/resources/web-app_2_3.dtd" };
	  public static final String NS_PLUGIN_URL = "http://java.sun.com/products/plugin/";
	  public static final String IE_PLUGIN_URL = "http://java.sun.com/products/plugin/1.2.2/jinstall-1_2_2-win.cab#Version=1,2,2,0";
	  public static final String TEMP_VARIABLE_NAME_PREFIX = System.getProperty("org.apache.jasper.Constants.TEMP_VARIABLE_NAME_PREFIX", "_jspx_temp");

	  /** @deprecated */
	  public static final char ESC = '\033';

	  /** @deprecated */
	  public static final String ESCStr = "'\\u001b'";
	  public static final boolean IS_SECURITY_ENABLED = System.getSecurityManager() != null;

	  public static final String SESSION_PARAMETER_NAME = System.getProperty("org.apache.catalina.SESSION_PARAMETER_NAME", "jsessionid");
	  public static final String XML_VALIDATION_TLD_INIT_PARAM = "org.apache.jasper.XML_VALIDATE_TLD";
	  public static final String XML_VALIDATION_INIT_PARAM = "org.apache.jasper.XML_VALIDATE";
	  public static final String XML_BLOCK_EXTERNAL_INIT_PARAM = "org.apache.jasper.XML_BLOCK_EXTERNAL";
}
