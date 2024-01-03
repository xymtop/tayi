package com.xymtop.tayi.core.p2p.network.pool;

import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;
import lombok.Data;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 17:16
 */

public class NodePoll {

    //为了传播的效率，每个节点只维护100个其他节点的信息
    static int MAX_NODE_NUM =100;

    //网络列表
   private static HashMap<String, NetNode> netNodes = new HashMap<>();


   public static int getNodeNum(){
       return netNodes.size();
   }


   public static synchronized  void setNode(NetNode netNode){
       if(netNodes.size()>=MAX_NODE_NUM){
          //清理不活跃的数据
           clearNodes();

           //再次获取数据
           if (netNodes.size()>=MAX_NODE_NUM){
               return;
           }
       }
       netNodes.put(netNode.getId(),netNode);
   }

    public static void clearNodes() {
       for (String key : netNodes.keySet()){
           if (netNodes.get(key).isStatus()==false){
               removeNode(netNodes.get(key));
           }
       }
    }

    public static synchronized  void removeNode(NetNode netNode){
       netNodes.remove(netNode.getId());
   }

   public static synchronized  NetNode getNode(NetNode netNode){
       return netNodes.get(netNode.getId());
   }


    public static synchronized Collection<NetNode> getNodeList(){
       return netNodes.values();
    }


    //开始检查
    public  static  synchronized  void   startCheck() throws IOException {
       for (String key : netNodes.keySet()){
           //默认是不活跃
           netNodes.get(key).setStatus(false);

           checkNode(netNodes.get(key));
       }
    }

    //检查状态
    public static synchronized  void checkNode(NetNode netNode) throws IOException {
        Socket socket = Sender.getSender(netNode.getIp(), netNode.getPort());
        MsgSender.sendPing(socket);
    }
}
