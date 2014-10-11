package lin.core.services;

import java.io.File;

import lin.core.entity.FileMap;

/**
 * 
 * @author 王江林
 * @date 2012-7-9 上午11:09:12
 *
 */
public interface FileService {

	public FileMap upload(File file,String fileName,String contentType);
	
	public FileMap download(String key);
	
	public String copy(String key);

	public void delete(String key);
}
