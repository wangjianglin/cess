package lin.core.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import lin.LinException;
import lin.core.Constants;
import lin.util.JsonUtil;


/**
 * 
 * @author lin
 * @date 2014年9月1日 上午12:29:18
 *
 */

public class LinMessageConverter extends AbstractHttpMessageConverter<Object> {

    public final static Charset UTF8     = Charset.forName("UTF-8");

    private Charset             charset  = UTF8;

//    private SerializerFeature[] features = new SerializerFeature[0];

    public LinMessageConverter(){
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

//    public SerializerFeature[] getFeatures() {
//        return features;
//    }
//
//    public void setFeatures(SerializerFeature... features) {
//        this.features = features;
//    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
                                                                                               HttpMessageNotReadableException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
//        return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
//        String s = new String(bytes,0,bytes.length,charset.newDecoder().charset());
        return JsonUtil.deserialize(new String(bytes,0,bytes.length,charset.newDecoder().charset()), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
                                                                             HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
//        String text = JSON.toJSONString(obj, features);
        ResultObj resultObj = new ResultObj();
        resultObj.setResult(obj);
        List<LinException> warnings = LinException.get();
        if(warnings != null){
        	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//    		if("xml".equals(request.getParameter("__result__"))){
//    			return xmlconverter;
//    		}
        	boolean isDebug = request.getHeader(Constants.HTTP_COMM_PROTOCOL_DEBUG) != null;
        	
	        List<Error> errors = new ArrayList<Error>();
	        Error error;
	        for(LinException warning : warnings){
	        	error = new Error();
	        	errors.add(error);
	        	if(warning.getCause() != null){
	        		error.setCause(warning.getCause().getMessage());
	        	}
	        	if(isDebug){
	        		error.setStackTrace(stackTrace(warning));
	        	}
	        	error.setMessage(warning.getMessage());
	        	error.setCode(warning.getCode());
	        }
	        resultObj.setWarnings(errors);
        }
        String text = JsonUtil.serialize(resultObj);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }
    
    private String stackTrace(Throwable e){
    	if(e == null){
    		return null;
    	}
    	ByteArrayOutputStream _out = new ByteArrayOutputStream();
    	e.printStackTrace(new PrintStream(_out));
    	
    	return _out.toString();
    	
    }
    
    public class ResultObj{
    	private Object result;
    	private long code;
    	private List<Error> warnings;
		public Object getResult() {
			return result;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public long getCode() {
			return code;
		}
		public void setCode(long code) {
			this.code = code;
		}
		public List<Error> getWarnings() {
			return warnings;
		}
		public void setWarnings(List<Error> warnings) {
			this.warnings = warnings;
		}
    	
    	
    }
    
    public class Error{
    	/**
    	 * 产生错误的具体原因，程序层面的
    	 */
    	private String cause;// { get; internal set; }//
    	private long code;// { get; internal set; }
    	/**
    	 * 产生的错误消息，用于展示给用户的
    	 */
    	private String message;// { get; internal set; }
    	/**
    	 * 产生错误的堆栈信息
    	 */
    	private String stackTrace;// { get; internal set; }

    	private Object data ;//{ get; set; }

    	private int dataType;// { get; set; }//数据类型,0、正常，1、后台验证错误

		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}

		public long getCode() {
			return code;
		}

		public void setCode(long code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getStackTrace() {
			return stackTrace;
		}

		public void setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public int getDataType() {
			return dataType;
		}

		public void setDataType(int dataType) {
			this.dataType = dataType;
		}
    	
    }

}

//public class LinMessageConverter implements HttpMessageConverter<Object>{
//
//	public LinMessageConverter(){
//	}
//	//extends org.springframework.http.converter.json.MappingJackson2HttpMessageConverter{
//	//private HttpMessageConverter<Object> jsonconverter = new MappingJackson2HttpMessageConverter();
//	private HttpMessageConverter<Object> jsonconverter = new JsonMessageConverter();
//	private HttpMessageConverter<Object> xmlconverter = new XMLMessageConverter();
//	private HttpMessageConverter<Object> converter(){
//		//ServletRequestAttributes a = null;
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		if("xml".equals(request.getParameter("__result__"))){
//			return xmlconverter;
//		}
//		return jsonconverter;
//	}
//	@Override
//	public boolean canRead(Class<?> clazz, MediaType mediaType) {
//		//return converter().canRead(clazz, mediaType);
//		//return jsonconverter.canRead(clazz, mediaType);
//		return true;
//	}
//
//	@Override
//	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//		//return converter().canWrite(clazz, mediaType);
//		//return jsonconverter.canWrite(clazz, mediaType);
//		return true;
//	}
//
//	@Override
//	public List<MediaType> getSupportedMediaTypes() {
//		//return converter().getSupportedMediaTypes();
//		ArrayList<MediaType> types = new ArrayList<MediaType>();
//		types.addAll(jsonconverter.getSupportedMediaTypes());
//		types.addAll(xmlconverter.getSupportedMediaTypes());
//		return Collections.unmodifiableList(types);
//	}
//
//	@Override
//	public Object read(Class<? extends Object> clazz,
//			HttpInputMessage inputMessage) throws IOException,
//			HttpMessageNotReadableException {
//		return converter().read(clazz, inputMessage);
//	}
//
//	@Override
//	public void write(Object t, MediaType contentType,
//			HttpOutputMessage outputMessage) throws IOException,
//			HttpMessageNotWritableException {
//		//converter().write(t, contentType, outputMessage);
//		converter().write(t, new MediaType("application","json",Charset.forName("UTF-8")), outputMessage);
//	}
//
//
//	private class JsonMessageConverter extends MappingJackson2HttpMessageConverter{
//
//		private JsonMessageConverter(){
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
//			this.setObjectMapper(mapper);
////			mapper.convertValue(fromValue, toValueType)
//		}
//		@Override
//		protected void writeInternal(Object object,
//				HttpOutputMessage outputMessage) throws IOException,
//				HttpMessageNotWritableException {
//			
//			
//			
//			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//			String protocol = request.getHeader(Constants.HTTP_COMM_PROTOCOL);
//			if(protocol == null){
//				super.writeInternal(object, outputMessage);
//				return;
//			}
//			ByteArrayOutputStream _out = new ByteArrayOutputStream();
//			_out.write("{\"code\":0,\"dataType\":0,\"result\":".getBytes());
//			super.writeInternal(object, new HttpOutputMessage(){
//
//				@Override
//				public HttpHeaders getHeaders() {
//					return outputMessage.getHeaders();
//				}
//
//				@Override
//				public OutputStream getBody() throws IOException {
//					return _out;
//				}});
//			_out.write("}".getBytes());
////			String json = Base64.getEncoder().encodeToString(_out.toByteArray());
//			String json = _out.toString();
//	        //json =  java.net.URLEncoder.encode(json, "ascii");
//			outputMessage.getBody().write(json.getBytes());
//		}
//		
//	}
//	
//	private class XMLMessageConverter extends Jaxb2RootElementHttpMessageConverter{
//		
//	}
//}
