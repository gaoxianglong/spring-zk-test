package com.gxl.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:root-context.xml")
public class Main {
	// @Resource
	// private UserinfoBean userinfoBean;
	private Logger logger = LoggerFactory.getLogger(Main.class);

	static ApplicationContext aContext;

	public @BeforeClass static void init() {
		aContext = new ClassPathXmlApplicationContext(
				"classpath:root-context.xml");
	}

	public @Test void testMethod() throws InterruptedException {
		for (;;) {
			UserinfoBean userinfoBean = (UserinfoBean) aContext
					.getBean("userinfoBean");
			IdinfoBean idinfoBean = (IdinfoBean) aContext.getBean("idinfoBean");
			logger.info("id-->" + idinfoBean.getId() + "\tusername-->"
					+ userinfoBean.getUsername() + "\t" + "password-->"
					+ userinfoBean.getPassword());
			Thread.sleep(5000);
		}

	}
}
