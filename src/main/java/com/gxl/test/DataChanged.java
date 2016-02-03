package com.gxl.test;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Watcher监听到节点数据发生变化后，更新实例数据
 * 
 * @author JohnGao
 */
@Component
public class DataChanged implements ApplicationContextAware {
	private ApplicationContext aContext;
	private Logger logger = LoggerFactory.getLogger(DataChanged.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.aContext = applicationContext;
	}

	/**
	 * 更新实例数据
	 * 
	 * @author JohnGao
	 */
	public void changed(String beanName, Map<String, String> values) {
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) aContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
				.getBeanFactory();
		if (defaultListableBeanFactory.isBeanNameInUse(beanName)) {
			Field[] fields = aContext.getBean(beanName).getClass().getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					field.set(aContext.getBean(beanName), values.get(field.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("beanName-->" + beanName + "重新更新实例数据");
		}
	}
}