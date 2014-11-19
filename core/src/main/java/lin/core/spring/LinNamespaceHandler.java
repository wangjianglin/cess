package lin.core.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 * @author lin
 * @date 2014年11月17日 上午12:15:58
 *
 */
public class LinNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("cxfServices", new CxfServicesBeanDefinitionParser());
		
		//this.registerBeanDefinitionDecorator("cxfServices", new CxfServicesBeanDefinitionDecorator());
	}

}
