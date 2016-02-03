package com.gxl.test;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

/**
 * 注册与UserinfoBean相关的节点
 * 
 * @author JohnGao
 */
@Component
public class RegisterUserNode implements RegisterNode {
	@Resource
	private UserWatcher userWatcher;
	@Resource
	private RegisterBean registerBean;

	@Override
	public void register(ZooKeeper zk_client, String... paths) throws Exception {
		final String usernamePath = paths[0];
		final String passwordPath = paths[1];
		userWatcher.init(zk_client, usernamePath, passwordPath);
		/* 注册节点 */
		Map<String, String> maps = new HashMap<String, String>();
		if (null != zk_client.exists(usernamePath, userWatcher))
			maps.put("username", new String(zk_client.getData(usernamePath, false, null)));
		if (null != zk_client.exists(passwordPath, userWatcher))
			maps.put("password", new String(zk_client.getData(passwordPath, false, null)));
		/* 动态注册UserinfoBean */
		registerBean.register("userinfoBean", UserinfoBean.class, maps);
	}
}