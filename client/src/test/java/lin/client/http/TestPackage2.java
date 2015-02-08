package lin.client.http;

import java.util.HashMap;
import java.util.Map;

public class TestPackage2 extends lin.client.http.Package{

	public TestPackage2(){
		super("/core/comm/test2.action");
		super.setRespType(null);
	}
	
	 @Override
	public Map<String, Object> getParams() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("data", data);
		return map;
	}

	private String data;// { get; set; }
//     public override IDictionary<string, object> GetParams()
//     {

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
         
}