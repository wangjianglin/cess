package lin.core.spring.methodProcessor;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.security.AccessControlContext;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

public class LinBeanWrapperImpl implements BeanWrapper {

	private BeanWrapperImpl proxy;
	public LinBeanWrapperImpl() {
		proxy = new BeanWrapperImpl();
	}

	public LinBeanWrapperImpl(boolean registerDefaultEditors) {
		proxy = new BeanWrapperImpl(registerDefaultEditors);
	}

	public LinBeanWrapperImpl(Class<?> clazz) {
		proxy = new BeanWrapperImpl(clazz);
	}

	public LinBeanWrapperImpl(Object object, String nestedPath,
			Object rootObject) {
		proxy = new BeanWrapperImpl(object, nestedPath, rootObject);
	}

	public LinBeanWrapperImpl(Object object) {
		proxy = new BeanWrapperImpl(object);
	}

	private String _prefix;
	private String prefix;
	private String propertyPathRemovePrefix(String propertyPath) {
		
		if(propertyPath.startsWith(_prefix)){
			return propertyPath.substring(this._prefix.length());
		}
		return propertyPath;
	}
	private PropertyValue propertyPathRemovePrefix(PropertyValue pv) {
		
		return new PropertyValue(pv){
	
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
	
			@Override
			public String getName() {
				return propertyPathRemovePrefix(super.getName());
			}
			
		};
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		this._prefix = prefix;
		if(this._prefix == null || "".equals(this._prefix)){
			this._prefix = "";
		}else{
			this._prefix += ".";
		}
	}

	
	public final Object getWrappedInstance() {
		return proxy.getWrappedInstance();
	}

//	@Override
	public final Class<?> getWrappedClass() {
		return proxy.getWrappedClass();
	}

	/**
	 * Return the nested path of the object wrapped by this BeanWrapper.
	 */
	public final String getNestedPath() {
		return proxy.getNestedPath();
	}

	/**
	 * Return the root object at the top of the path of this BeanWrapper.
	 * @see #getNestedPath
	 */
	public final Object getRootInstance() {
		return proxy.getRootInstance();
	}

	/**
	 * Return the class of the root object at the top of the path of this BeanWrapper.
	 * @see #getNestedPath
	 */
	public final Class<?> getRootClass() {
		return proxy.getWrappedClass();
	}



	/**
	 * Specify a limit for array and collection auto-growing.
	 * <p>Default is unlimited on a plain BeanWrapper.
	 */
	@Override
	public void setAutoGrowCollectionLimit(int autoGrowCollectionLimit) {
		proxy.setAutoGrowCollectionLimit(autoGrowCollectionLimit);
	}

	/**
	 * Return the limit for array and collection auto-growing.
	 */
	@Override
	public int getAutoGrowCollectionLimit() {
		return proxy.getAutoGrowCollectionLimit();
	}

	/**
	 * Set the security context used during the invocation of the wrapped instance methods.
	 * Can be null.
	 */
	public void setSecurityContext(AccessControlContext acc) {
		proxy.setSecurityContext(acc);
	}

	/**
	 * Return the security context used during the invocation of the wrapped instance methods.
	 * Can be null.
	 */
	public AccessControlContext getSecurityContext() {
		return proxy.getSecurityContext();
	}


	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		return proxy.getPropertyDescriptors();
	}

	@Override
	public PropertyDescriptor getPropertyDescriptor(String propertyName) throws BeansException {
		return proxy.getPropertyDescriptor(propertyPathRemovePrefix(propertyName));
	}

	@Override
	public Class<?> getPropertyType(String propertyName) throws BeansException {
		return proxy.getPropertyType(propertyPathRemovePrefix(propertyName));
	}

	@Override
	public TypeDescriptor getPropertyTypeDescriptor(String propertyName) throws BeansException {
		return proxy.getPropertyTypeDescriptor(propertyPathRemovePrefix(propertyName));
	}

	@Override
	public boolean isReadableProperty(String propertyName) {
		return proxy.isReadableProperty(propertyPathRemovePrefix(propertyName));
	}

	@Override
	public boolean isWritableProperty(String propertyName) {
		return proxy.isWritableProperty(propertyPathRemovePrefix(propertyName));
	}

	/**
	 * Convert the given value for the specified property to the latter's type.
	 * <p>This method is only intended for optimizations in a BeanFactory.
	 * Use the {@code convertIfNecessary} methods for programmatic conversion.
	 * @param value the value to convert
	 * @param propertyName the target property
	 * (note that nested or indexed properties are not supported here)
	 * @return the new value, possibly the result of type conversion
	 * @throws TypeMismatchException if type conversion failed
	 */
	public Object convertForProperty(Object value, String propertyName) throws TypeMismatchException {
		return proxy.convertForProperty(value, propertyPathRemovePrefix(propertyName));
	}


	@Override
	public Object getPropertyValue(String propertyName) throws BeansException {
		return proxy.getPropertyValue(propertyPathRemovePrefix(propertyName));
	}


	@Override
	public void setPropertyValue(String propertyName, Object value) throws BeansException {
		proxy.setPropertyValue(propertyPathRemovePrefix(propertyName),value);
	}

	@Override
	public void setPropertyValue(PropertyValue pv) throws BeansException {
		proxy.setPropertyValue(propertyPathRemovePrefix(pv));
	}

	@Override
	public String toString() {
		return proxy.toString();
	}

	@Override
	public void setConversionService(ConversionService conversionService) {
		proxy.setConversionService(conversionService);
	}

	@Override
	public ConversionService getConversionService() {
		return proxy.getConversionService();
	}

	@Override
	public void setExtractOldValueForEditor(boolean extractOldValueForEditor) {
		proxy.setExtractOldValueForEditor(extractOldValueForEditor);
	}

	@Override
	public boolean isExtractOldValueForEditor() {
		return proxy.isExtractOldValueForEditor();
	}

	@Override
	public void setAutoGrowNestedPaths(boolean autoGrowNestedPaths) {
		proxy.setAutoGrowNestedPaths(autoGrowNestedPaths);
	}

	@Override
	public boolean isAutoGrowNestedPaths() {
		return proxy.isAutoGrowNestedPaths();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setPropertyValues(Map<?, ?> map) throws BeansException {
		Map newMap = null;
		if (map != null) {
			newMap = new HashMap();
			
//			this.propertyValueList = new ArrayList<PropertyValue>(original.size());
			for (Map.Entry<?, ?> entry : map.entrySet()) {
//				this.propertyValueList.add(new PropertyValue(entry.getKey().toString(), entry.getValue()));
				newMap.put(propertyPathRemovePrefix(entry.getKey().toString()), entry.getValue());
			}
		}
		proxy.setPropertyValues(newMap);
	}

	
	private PropertyValues propertyValueshRemovePrefix(PropertyValues pvs) {
		
		return new PropertyValues(){
	
			@Override
			public PropertyValue[] getPropertyValues() {
				PropertyValue[] values = pvs.getPropertyValues();
				if(values == null){
					return null;
				}
				PropertyValue[] newValues = new PropertyValue[values.length];
				for(int n=0;n<values.length;n++){
					newValues[n] = propertyPathRemovePrefix(values[n]);
				}
				return newValues;
				//return pvs.getPropertyValues();
			}

			@Override
			public PropertyValue getPropertyValue(String propertyName) {
				return pvs.getPropertyValue(_prefix + propertyName);
			}

			@Override
			public PropertyValues changesSince(PropertyValues old) {
				return pvs.changesSince(old);
			}

			@Override
			public boolean contains(String propertyName) {
				return pvs.contains(_prefix + propertyName);
			}

			@Override
			public boolean isEmpty() {
				return pvs.isEmpty();
			}
			
		};
	}

	@Override
	public void setPropertyValues(PropertyValues pvs) throws BeansException {
		proxy.setPropertyValues(propertyValueshRemovePrefix(pvs));
	}

	@Override
	public void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown)
			throws BeansException {
		proxy.setPropertyValues(propertyValueshRemovePrefix(pvs), ignoreUnknown);
	}

	@Override
	public void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown,
			boolean ignoreInvalid) throws BeansException {
		proxy.setPropertyValues(propertyValueshRemovePrefix(pvs), ignoreUnknown, ignoreInvalid);
	}

	@Override
	public void registerCustomEditor(Class<?> requiredType,
			PropertyEditor propertyEditor) {
		proxy.registerCustomEditor(requiredType, propertyEditor);
	}

	@Override
	public void registerCustomEditor(Class<?> requiredType,
			String propertyPath, PropertyEditor propertyEditor) {
		proxy.registerCustomEditor(requiredType, this.propertyPathRemovePrefix(propertyPath), propertyEditor);
	}

	@Override
	public PropertyEditor findCustomEditor(Class<?> requiredType,
			String propertyPath) {
		return proxy.findCustomEditor(requiredType, propertyPathRemovePrefix(propertyPath));
	}

	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType)
			throws TypeMismatchException {
		return proxy.convertIfNecessary(value, requiredType);
	}

	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType,
			MethodParameter methodParam) throws TypeMismatchException {
		return proxy.convertIfNecessary(value, requiredType, methodParam);
	}

	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType,
			Field field) throws TypeMismatchException {
		return proxy.convertIfNecessary(value, requiredType, field);
	}


//
//	/**
//	 * Inner class to avoid a hard dependency on Java 8.
//	 */
//	@UsesJava8
//	private static class OptionalUnwrapper {
//
//		public static Object unwrap(Object optionalObject) {
//			Optional<?> optional = (Optional<?>) optionalObject;
//			Assert.isTrue(optional.isPresent(), "Optional value must be present");
//			Object result = optional.get();
//			Assert.isTrue(!(result instanceof Optional), "Multi-level Optional usage not supported");
//			return result;
//		}
//
//		public static boolean isEmpty(Object optionalObject) {
//			return !((Optional<?>) optionalObject).isPresent();
//		}
//	}
}
