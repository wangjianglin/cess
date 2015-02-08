package lin.core.spring.methodProcessor;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

public class LinExtendedServletRequestDataBinder extends ExtendedServletRequestDataBinder{

	public LinExtendedServletRequestDataBinder(Object target, String objectName) {
		super(target, objectName);
	}

	public LinExtendedServletRequestDataBinder(Object target) {
		super(target);
	}

	public void initBeanPropertyAccess() {
//		Assert.state(this.bindingResult == null,
//				"DataBinder is already initialized - call initBeanPropertyAccess before other configuration methods");
		BeanPropertyBindingResult bindingResult = new LinBeanPropertyBindingResult(
				getTarget(), getObjectName(), isAutoGrowNestedPaths(), getAutoGrowCollectionLimit());
//		if (this.conversionService != null) {
		if (this.getConversionService() != null){
			bindingResult.initConversion(this.getConversionService());
			try {
				java.lang.reflect.Field bindingResultField = DataBinder.class.getDeclaredField("bindingResult");
				bindingResultField.setAccessible(true);
				bindingResultField.set(this, bindingResult);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
//			this.setb
		}
	}
}
