package lin.demo.services;

import java.util.List;

import lin.demo.entity.TestEntity;

public interface TestService {

//	public TestEntity add(TestEntity entity);
	
	public TestEntity get(long id);
	
	public TestEntity save(TestEntity entity);
	
	public TestEntity delete(long id);
	
	public List<TestEntity> list();
}
