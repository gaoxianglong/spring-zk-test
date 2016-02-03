package com.gxl.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:root-context.xml")
public class Main {
	@Resource
	private UserinfoBean userinfoBean;
	private Logger logger = LoggerFactory.getLogger(Main.class);

	public @Test void testMethod() throws InterruptedException {
		for (;;) {
			logger.info("username-->" + userinfoBean.getUsername() + "\t" + "password-->" + userinfoBean.getPassword());
			Thread.sleep(5000);
		}
	}
}