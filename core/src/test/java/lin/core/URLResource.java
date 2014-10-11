package lin.core;

import java.util.regex.Pattern;

import org.junit.Test;

public class URLResource {

	@Test
	public void testURL(){
		Pattern pattern = Pattern.compile("^/client/dotnet.*");
		
		System.out.println("r:"+pattern.matcher("/client/dotnet/HealthDevicesPlatform.zip").matches());
	}
}
