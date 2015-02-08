package lin.core.spring.methodProcessor;

import org.springframework.beans.BeanWrapper;
import org.springframework.validation.BeanPropertyBindingResult;

public class LinBeanPropertyBindingResult extends BeanPropertyBindingResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LinBeanPropertyBindingResult(Object target, String objectName,
			boolean autoGrowNestedPaths, int autoGrowCollectionLimit) {
		super(target, objectName, autoGrowNestedPaths, autoGrowCollectionLimit);
	}

	public LinBeanPropertyBindingResult(Object target, String objectName) {
		super(target, objectName);
	}
	protected BeanWrapper createBeanWrapper() {
//		Assert.state(this.target != null, "Cannot access properties on null bean instance '" + getObjectName() + "'!");
//		return PropertyAccessorFactory.forBeanPropertyAccess(this.target);
		LinBeanWrapperImpl beanWraper = new LinBeanWrapperImpl(this.getTarget());
		beanWraper.setPrefix(this.getObjectName());
		return beanWraper;
	}
}
