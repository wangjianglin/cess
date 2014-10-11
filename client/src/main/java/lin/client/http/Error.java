package lin.client.http;

/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:02:43
 *
 */
public class Error {

	//private long code;
	private String cause;// { get; internal set; }
	private long code;// { get; internal set; }
	private String message;// { get; internal set; }
	private String stackTrace;// { get; internal set; }

	private Object data ;//{ get; set; }

	private int dataType;// { get; set; }//数据类型,0、正常，1、后台验证错误
    
	public long getCode() {
		return code;
	}

	void setCode(long code) {
		this.code = code;
	}

	public String getCause() {
		return cause;
	}

	void setCause(String cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Object getData() {
		return data;
	}

	void setData(Object data) {
		this.data = data;
	}

	public int getDataType() {
		return dataType;
	}

	void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
}
