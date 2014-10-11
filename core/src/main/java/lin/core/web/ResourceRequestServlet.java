package lin.core.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;

import lin.core.Constants;


/**
 * 用于把jar包的/WEB-INF/content下内容复制到<WebRoot>/context/WEB-INF/content目录下
 * @author lin
 * @date 2012-3-27 下午5:01:01
 *
 *
 */
public class ResourceRequestServlet extends HttpServlet {
//public class ResourceRequestServlet implements javax.servlet.Filter {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String servletPath = request.getServletPath();
		String rpath = location + uri.substring(contextPathLenth + servletPath.length()+1);
		if(isClasspathResource){
			writeClasspathResource(response,rpath);
		}else{
			request.getRequestDispatcher(rpath).forward(request, response);
		}
	}
	private int contextPathLenth = 0;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		contextPathLenth = this.getServletContext().getContextPath().length();
		location = config.getInitParameter(Constants.LOCATION_PARAM);
		if(location == null || "".equals(location.trim())){
			location = Constants.DEFAULT_LOCATION;
		}else{
			location = location.trim();
			if(!location.endsWith("/")){
				location += "/";
			}
		}
		if(location.startsWith("classpath:")){
			isClasspathResource = true;
			location = location.substring(10);
		}
	}

	private String location = null;
	private boolean isClasspathResource = false;
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		location = filterConfig.getInitParameter(Constants.LOCATION_PARAM);
//		if(location == null || "".equals(location.trim())){
//			location = Constants.DEFAULT_LOCATION;
//		}else{
//			location = location.trim();
//			if(!location.endsWith("/")){
//				location += "/";
//			}
//		}
//		if(location.startsWith("classpath:")){
//			isClasspathResource = true;
//			location = location.substring(10);
//		}
//	}
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest r = (HttpServletRequest)request;
////		System.out.println("url:"+r.getRequestURL());
////		System.out.println("context path:"+r.getContextPath());
////		System.out.println("servlet path:"+r.getServletPath());
//		String uri = r.getRequestURI();
//		String servletPath = r.getServletPath();
//		String rpath = location + uri.substring(0,uri.length()-servletPath.length());
//		if(isClasspathResource){
//			writeClasspathResource((HttpServletResponse)response,rpath);
//		}else{
//			request.getRequestDispatcher(rpath).forward(request, response);
//		}
////		chain.doFilter(request, response);
//	}
	
	private void writeClasspathResource(HttpServletResponse response,String rpath) throws IOException{
		String mimeType = this.getServletContext().getMimeType(rpath);
		response.setContentType(mimeType);
		try(InputStream in = this.getClass().getResourceAsStream(rpath);) {
			StreamUtils.copy(in, response.getOutputStream());
		}
	}
//	@Override
//	public void destroy() {
//		
//	}


}
