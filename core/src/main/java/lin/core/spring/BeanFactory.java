//package lin.core.spring;
//
//import java.beans.PropertyDescriptor;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.TypeConverter;
//import org.springframework.beans.factory.UnsatisfiedDependencyException;
//import org.springframework.beans.factory.config.DependencyDescriptor;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.PriorityOrdered;
//
//public class BeanFactory extends DefaultListableBeanFactory{
//
//	public BeanFactory() {
//		super();
//	}
//
//	public BeanFactory(
//			org.springframework.beans.factory.BeanFactory parentBeanFactory) {
//		super(parentBeanFactory);
//	}
//
//	protected void autowireByType(
//			String beanName, AbstractBeanDefinition mbd, BeanWrapper bw, MutablePropertyValues pvs) {
//		
//		//super.autowireByType(beanName, mbd, bw, pvs);
//		TypeConverter converter = getCustomTypeConverter();
//		if (converter == null) {
//			converter = bw;
//		}
//
//		Set<String> autowiredBeanNames = new LinkedHashSet<String>(4);
//		String[] propertyNames = unsatisfiedNonSimpleProperties(mbd, bw);
//		String className = null;
//		for (String propertyName : propertyNames) {
//			try {
//				PropertyDescriptor pd = bw.getPropertyDescriptor(propertyName);
//				// Don't try autowiring by type for type Object: never makes sense,
//				// even if it technically is a unsatisfied, non-simple property.
//				if (!Object.class.equals(pd.getPropertyType())) {
//					MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
//					className = methodParam.getMethod().getDeclaringClass().getName();
//					/////////////////////////////////////////////////////////////////////////////////////////////////////
//					if(className.startsWith("com.opensymphony.xwork") || className.startsWith("org.apache.struts")){
//						continue;
//					}
//					/////////////////////////////////////////////////////////////////////////////////////////////////////
//					// Do not allow eager init for type matching in case of a prioritized post-processor.
//					boolean eager = !PriorityOrdered.class.isAssignableFrom(bw.getWrappedClass());
//					DependencyDescriptor desc = new AutowireByTypeDependencyDescriptor(methodParam, eager);
//					Object autowiredArgument = resolveDependency(desc, beanName, autowiredBeanNames, converter);
//					if (autowiredArgument != null) {
//						pvs.add(propertyName, autowiredArgument);
//					}
//					for (String autowiredBeanName : autowiredBeanNames) {
//						registerDependentBean(autowiredBeanName, beanName);
//						if (logger.isDebugEnabled()) {
//							logger.debug("Autowiring by type from bean name '" + beanName + "' via property '" +
//									propertyName + "' to bean named '" + autowiredBeanName + "'");
//						}
//					}
//					autowiredBeanNames.clear();
//				}
//			}
//			catch (BeansException ex) {
//				throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, propertyName, ex);
//			}
//		}
//	}
//	
//	/**
//	 * Special DependencyDescriptor variant for autowire="byType".
//	 * Always optional; never considering the parameter name for choosing a primary candidate.
//	 */
//	private static class AutowireByTypeDependencyDescriptor extends DependencyDescriptor {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//
//		public AutowireByTypeDependencyDescriptor(MethodParameter methodParameter, boolean eager) {
//			super(methodParameter, false, eager);
//		}
//
//		@Override
//		public String getDependencyName() {
//			return null;
//		}
//	}
//}
