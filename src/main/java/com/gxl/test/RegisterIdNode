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
public class RegisterIdNode implements RegisterNode {
	@Resource
	private IdWatcher idWatcher;
	@Resource
	private RegisterBean registerBean;

	@Override
	public void register(ZooKeeper zk_client, String... paths) throws Exception {
		final String idPath = paths[0];
		idWatcher.init(zk_client, idPath);
		/* 注册节点 */
		Map<String, String> maps = new HashMap<String, String>();
		if (null != zk_client.exists(idPath, idWatcher))
			maps.put("id", new String(zk_client.getData(idPath, false, null)));
		/* 动态注册IdinfoBean */
		registerBean.register("idinfoBean", IdinfoBean.class, maps);
	}
}
