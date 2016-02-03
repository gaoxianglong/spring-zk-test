package com.gxl.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 动态注册bean
 * 
 * @author JohnGao
 */
@Component
public class RegisterBean implements ApplicationContextAware {
	public ApplicationContext aContext;
	private Logger logger = LoggerFactory.getLogger(RegisterBean.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.aContext = applicationContext;
	}

	/**
	 * 动态注册bean
	 * 
	 * @author JohnGao
	 */
	public <T> void register(String beanName, Class<T> classType, Map<String, String> values) {
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) aContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
				.getBeanFactory();
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(classType);
		for (String key : values.keySet())
			beanDefinitionBuilder.addPropertyValue(key, values.get(key));
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
		logger.info("beanName-->" + beanName + "成功注册");
	}
}