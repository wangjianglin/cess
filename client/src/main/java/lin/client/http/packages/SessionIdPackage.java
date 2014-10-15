package lin.client.http.packages;

import java.util.Map;

public class SessionIdPackage extends lin.client.http.packages.Package{

	public SessionIdPackage(){
		this.setUri("/core/comm/sessionId.action");
	}
	
	 @Override
	public Map<String, Object> getParams() {
		return null;
	}

}
