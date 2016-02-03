package com.gxl.test;

import org.apache.zookeeper.ZooKeeper;

/**
 * 注册节点接口
 * 
 * @author JohnGao
 */
public interface RegisterNode {
	/**
	 * 注册Znode
	 * 
	 * @author JohnGao
	 */
	public void register(ZooKeeper zk_client, String... paths) throws Exception;
}