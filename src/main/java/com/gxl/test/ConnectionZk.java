package com.gxl.test;

import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 与Zookeeper服务器建立session会话
 * 
 * @author JohnGao
 */
public class ConnectionZk {
	@Resource(name = "registerUserNode")
	private RegisterNode registerUserNode;
	@Resource(name = "registerIdNode")
	private RegisterNode registerIdNode;
	private String zk_address;
	private int session_timeout;
	public ZooKeeper zk_client;
	private String usernamePath;
	private String passwordPath;
	private String id;
	private CountDownLatch countDownLatch;
	private Logger logger = LoggerFactory.getLogger(ConnectionZk.class);

	private ConnectionZk(String zk_address, int session_timeout,
			String usernamePath, String passwordPath, String id) {
		this.zk_address = zk_address;
		this.session_timeout = session_timeout;
		this.usernamePath = usernamePath;
		this.passwordPath = passwordPath;
		this.id = id;
		countDownLatch = new CountDownLatch(1);
	}

	/**
	 * 初始化方法
	 *
	 * @author JohnGao
	 */
	public void init() {
		connection();
	}

	/**
	 * 连接zookeeper
	 * 
	 * @author JohnGao
	 */
	private void connection() {
		try {
			zk_client = new ZooKeeper(zk_address, session_timeout,
					new Watcher() {
						@Override
						public void process(WatchedEvent event) {
							final KeeperState STATE = event.getState();
							switch (STATE) {
							case SyncConnected:
								countDownLatch.countDown();
								logger.info("成功连接zookeeper服务器");
								break;
							case Disconnected:
								logger.warn("与zookeeper服务器断开连接");
								break;
							case Expired:
								logger.error("session会话失效...");
								break;
							default:
								break;
							}
						}
					});
			countDownLatch.await();
			/* 注册与UserinfoBean相关的节点 */
			registerUserNode.register(zk_client, usernamePath, passwordPath);
			/* 注册与IdinfoBean相关的节点 */
			registerIdNode.register(zk_client, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
