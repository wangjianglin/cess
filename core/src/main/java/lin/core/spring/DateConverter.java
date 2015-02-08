package lin.core.spring;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String,Date> {

	@Override
	public Date convert(String source) {
		System.out.println("ok.");
		return new Date();
	}

}
