package lin.client.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lin.util.json.JSONException;
import lin.util.json.JSONToParameters;
import lin.util.json.JSONUtil;

import org.junit.Test;

public class HttpEntityTest {

	@Test
	public void test() throws URISyntaxException{
		HttpEntityTestPackge pack = new HttpEntityTestPackge();
		TestEntity entity = new TestEntity();
		entity.setId(3l);
		entity.setName("name");
		entity.setValue("value");
		entity.setDate(new Date());
		
		TestEntity data = new TestEntity();
		data.setId(3l);
		data.setName("name");
		data.setValue("value");
		data.setDate(new Date());
		entity.setData(data);
		pack.setEntity(entity);
		
		
//		Map<String,Object> map = pack.getParams();
		try {
			String json = JSONUtil.serialize(pack.getParams());
			Object obj = JSONUtil.deserialize(json);
			Map<String,String> params = new HashMap<String, String>();
			JSONToParameters.processesParameters(obj,params,null);
			for(String key : params.keySet()){
//				System.out.println("entity."+
				System.out.println("pv = new PropertyValue(\"" + key + "\",\"" + params.get(key) +"\");");
				System.out.println("bean.setPropertyValue(pv);");
				System.out.println();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		HttpCommunicateImpl client = HttpCommunicate.get("clien");
		//client.setCommUrl(new URI("http://localhost:8080/web/__http_comm_protocol__"));
		client.setCommUrl(new URI("http://localhost/__http_comm_protocol__"));
		//client.setCommUrl(new URI("http://localhost/core/comm/test.action"));
		client.setCommUrl(new URI("http://localhost:8080/lin.demo"));
		client.request(pack, (Object obj, List<Error> warning)-> {
				System.out.println("------------"+obj);
			},error -> {
				System.out.println("error:"+error.getCode());
			}
		).waitForEnd();
	}
}


class HttpEntityTestPackge extends lin.client.http.Package{

	public HttpEntityTestPackge() {
		super("test/save/6.action");
		this.setRespType(TestEntity.class);
	}

	private TestEntity entity;
	@Override
	public Map<String, Object> getParams() {
		Map<String,Object> map = new HashMap<>();
		map.put("entity",entity);
		return map;
	}
	public TestEntity getEntity() {
		return entity;
	}
	public void setEntity(TestEntity entity) {
		this.entity = entity;
	}
	
}