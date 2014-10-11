package lin.core.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lin.core.entity.FileMap;
import lin.core.jpa.CommonQuery;
import lin.core.services.FileService;
import lin.util.IDGener;

/**
 * 
 * @author 王江林
 * @date 2012-7-9 上午11:17:23
 *
 */
@Component
@Transactional
public class FileServiceImpl implements FileService {

private EntityManager jpa;
	
	//@PersistenceContext
	public void setJpa(EntityManager jpa){
		this.jpa = jpa;
	}
	
	@Override
	public FileMap upload(File file, String fileName, String contentType) {
		try{
			FileMap fileMap = new FileMap();
			fileMap.setRef(1);
			InputStream in = new FileInputStream(file);
			ByteArrayOutputStream _out = new ByteArrayOutputStream();
			byte[] bs = new byte[1024];
			int count = -1;
			while((count = in.read(bs)) != -1){
				_out.write(bs,0,count);
			}
			fileMap.setData(_out.toByteArray());
			in.close();
			_out.close();
			fileMap.setKey("key-"+IDGener.next());
			fileMap.setFileName(fileName);
			fileMap.setContentType(contentType);
			return CommonQuery.update(jpa, fileMap);
		}catch(Exception e){}
		return null;
	}

	@Override
	public FileMap download(String key) {
		Collection<?> files = CommonQuery.nameQuery(jpa, "cloud_fileSelectByKey", key);
		if(files != null && files.size() > 0){
			return (FileMap) files.iterator().next();
		}
		return null;
	}

	@Override
	public String copy(String key) {
		FileMap file = download(key);
		file.setRef(file.getRef() + 1);
		file = CommonQuery.update(jpa,file);
		if(file != null){
			return file.getKey();
		}
		CommonQuery.update(jpa, "cloud_fileDelete", key);
		return key;
	}

	@Override
	public void delete(String key) {
		FileMap file = download(key);
		if(file != null){
			file.setRef(file.getRef() - 1);
			if(file.getRef() < 1){
				jpa.remove(file);
			}else{
				CommonQuery.update(jpa, file);
			}
		}
	}

}
