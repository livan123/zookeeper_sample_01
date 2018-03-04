package zookeeper.zkser;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class DistributedServer {

	private static final String connectString="192.168.120.135:2181";
    private static final int sessionTimeout=2000;
    private static final String parentNode="/servers";
	
	private ZooKeeper zk =null;
	
	public void getConnect() throws Exception{
		
			zk = new ZooKeeper(connectString, sessionTimeout, new Watcher(){
			
            //zk发生变化时会执行process方法，用来通知。
    	    @Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getType()+"----"+event.getPath());
				try {
					zk.getChildren("/", true);
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	//向zk集群注册信息
	public void registerServer(String hostname) throws Exception{
		String create = zk.create(parentNode+"/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(hostname+"is online.."+create);
	}
	
	//业务功能：
	public void handleBussiness(String hostname) throws Exception{
		System.out.println(hostname+"start working……………………");
		Thread.sleep(Long.MAX_VALUE);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//获取zk连接：
		DistributedServer server = new DistributedServer();
		server.getConnect();
		
        //利用zk连接注册服务器信息
		server.registerServer(args[0]);
		
		//启动业务功能
		server.handleBussiness(args[0]);
		
	}

}



