//package lin.core.spring;
//
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//
///**
// * 
// * @author 王江林
// * @date 2012-7-24 下午2:13:50
// *
// */
//public class LinWebApplicationContext extends XmlWebApplicationContext{
//
//	public LinWebApplicationContext(){
//	}
//	
//	@Override
//	protected DefaultListableBeanFactory createBeanFactory() {
//		//return new DefaultListableBeanFactory(getInternalParentBeanFactory());
//		return new BeanFactory(getInternalParentBeanFactory());
//	}
//}
