package lin.core.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lin.core.Constants;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author lin
 * @date 2014年9月1日 上午12:29:18
 *
 */
public class LinMessageConverter implements HttpMessageConverter<Object>{

	public LinMessageConverter(){
	}
	//extends org.springframework.http.converter.json.MappingJackson2HttpMessageConverter{
	//private HttpMessageConverter<Object> jsonconverter = new MappingJackson2HttpMessageConverter();
	private HttpMessageConverter<Object> jsonconverter = new JsonMessageConverter();
	private HttpMessageConverter<Object> xmlconverter = new XMLMessageConverter();
	private HttpMessageConverter<Object> converter(){
		//ServletRequestAttributes a = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if("xml".equals(request.getParameter("__result__"))){
			return xmlconverter;
		}
		return jsonconverter;
	}
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		//return converter().canRead(clazz, mediaType);
		//return jsonconverter.canRead(clazz, mediaType);
		return true;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		//return converter().canWrite(clazz, mediaType);
		//return jsonconverter.canWrite(clazz, mediaType);
		return true;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		//return converter().getSupportedMediaTypes();
		ArrayList<MediaType> types = new ArrayList<MediaType>();
		types.addAll(jsonconverter.getSupportedMediaTypes());
		types.addAll(xmlconverter.getSupportedMediaTypes());
		return Collections.unmodifiableList(types);
	}

	@Override
	public Object read(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return converter().read(clazz, inputMessage);
	}

	@Override
	public void write(Object t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		//converter().write(t, contentType, outputMessage);
		converter().write(t, new MediaType("application","json",Charset.forName("UTF-8")), outputMessage);
	}


	private class JsonMessageConverter extends MappingJackson2HttpMessageConverter{

		@Override
		protected void writeInternal(Object object,
				HttpOutputMessage outputMessage) throws IOException,
				HttpMessageNotWritableException {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String protocol = request.getHeader(Constants.HTTP_COMM_PROTOCOL);
			if(protocol == null || "".equals(protocol)){
				super.writeInternal(object, outputMessage);
				return;
			}
			ByteArrayOutputStream _out = new ByteArrayOutputStream();
			_out.write("{\"code\":0,\"dataType\":0,\"result\":".getBytes());
			super.writeInternal(object, new HttpOutputMessage(){

				@Override
				public HttpHeaders getHeaders() {
					return outputMessage.getHeaders();
				}

				@Override
				public OutputStream getBody() throws IOException {
					return _out;
				}});
			_out.write("}".getBytes());
			String json = Base64.getEncoder().encodeToString(_out.toByteArray());
	        json =  java.net.URLEncoder.encode(json, "ascii");
			outputMessage.getBody().write(json.getBytes());
		}
		
	}
	
	private class XMLMessageConverter extends Jaxb2RootElementHttpMessageConverter{
		
	}
}
