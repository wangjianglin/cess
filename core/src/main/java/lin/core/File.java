package lin.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import lin.core.entity.FileMap;
import lin.core.services.FileService;

public class File {
	private FileService service;

	private static File file;

	static {
		file = new File();
//		AutoWiring.autoWiring(file);
	}

	private File() {

	}

	public static InputStream getFile(String key) {
		FileMap fileMap = file.service.download(key);
		InputStream sbs = new ByteArrayInputStream(fileMap.getData());
		return sbs;
	}

	public FileService getService() {
		return service;
	}

	public void setService(FileService service) {
		this.service = service;
	}

	public static File getFile() {
		return file;
	}

	public static void setFile(File file) {
		File.file = file;
	}
}
