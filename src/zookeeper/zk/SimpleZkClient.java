package zookeeper.zk;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class SimpleZkClient {
	
	private static final String connectString="192.168.120.135:2181";
    private static final int sessionTimeout=2000;
    
    ZooKeeper zkClient = null;
    
    @Before
    public void init() throws Exception{
    	    zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher(){
			
//    	    zk发生变化时会执行process方法，用来通知。
    	    @Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getType()+"----"+event.getPath());
				try {
					zkClient.getChildren("/", true);
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
    	
	public void testCreate() throws KeeperException, InterruptedException{	
		/*数据的增删改查：*/
		/*参数1：要创建的节点的路径；参数2：节点的大数据；参数3：节点的权限；参数4：节点的类型*/
 		zkClient.create("/zk2", "hellozk".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
 		
	}
	
	@Test
	public void getChildren() throws KeeperException, InterruptedException{
		List<String> children = zkClient.getChildren("/", true);
		for(String child:children){
			System.out.println(child);
		}
		
		Thread.sleep(Long.MAX_VALUE);
		
	}
	
}
