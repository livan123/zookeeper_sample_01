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
			
//    	    zk�����仯ʱ��ִ��process����������֪ͨ��
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
		/*���ݵ���ɾ�Ĳ飺*/
		/*����1��Ҫ�����Ľڵ��·��������2���ڵ�Ĵ����ݣ�����3���ڵ��Ȩ�ޣ�����4���ڵ������*/
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
