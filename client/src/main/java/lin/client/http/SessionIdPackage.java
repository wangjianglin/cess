package lin.client.http;

import java.util.Map;

public class SessionIdPackage extends lin.client.http.Package{

	public SessionIdPackage(){
		super("/core/comm/sessionId.action");
	}
	
	 @Override
	public Map<String, Object> getParams() {
		return null;
	}

}
