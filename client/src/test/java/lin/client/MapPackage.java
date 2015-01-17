package lin.client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapPackage extends lin.client.http.Package {

	public MapPackage(){
		super("/web/action/map!test.action");
		//Class<?> type = datas.getClass();//datas.getClass().getGenericSuperclass()
		//TypeVariable tv                                                                                                                                                                                                                                                                                                                                                      ;
		//System.out.println("type:"+t);
		this.setRespType(new ParameterizedType() {
			
			@Override
			public Type getRawType() {
				return Map.class;
			}
			
			@Override
			public Type getOwnerType() {
				return null;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[]{String.class,String.class};
			}
		});
		
	}
	
	public Map<String,String> datas = new HashMap<String, String>();
	private String data;

	
	
	public Map<String, String> getDatas() {
		return datas;
	}



	public void setDatas(Map<String, String> datas) {
		this.datas = datas;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	@Override
	public Map<String, Object> getParams() {
		Map<String, Object> params = new HashMap<>();
		params.put("datas", datas);
		params.put("data", data);
		return params;
	}
}
