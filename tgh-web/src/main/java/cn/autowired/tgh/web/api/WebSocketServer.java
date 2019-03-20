package cn.autowired.tgh.web.api;

import cn.autowired.tgh.dto.WebsocketDto;
import cn.autowired.tgh.entity.UserInfo;
import cn.autowired.tgh.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/19 11:28
 * @version: 1.0.0
 */
@Component
@ServerEndpoint(value = "/websocket/{roomId}/{sid}")
public class WebSocketServer {


    //你要注入的service或者dao
    private  static RedisUtil redisUtil;

    @Autowired
    private void setRedisUtil(RedisUtil redisUtil) {
        WebSocketServer.redisUtil = redisUtil;
    }


    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static  int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收sid
     */
    private String sid="";

    /**
     * 接收roomid
     */
    private String roomId="";
    /**
     * 连接建立成功调用的方法*/




    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid,@PathParam("roomId")String roomId) {
        String id = session.getId();
        System.out.println(id+"建立了连接");
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("有房间开始监听:"+roomId+"的用户"+sid+",当前总在线人数为" + getOnlineCount());
        this.sid=sid;
        this.roomId = roomId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到来自房间"+roomId+"的"+sid+"的信息:"+message);
        Map<Object, Object> room = redisUtil.hmget(roomId);
        LinkedList<Map<String,UserInfo>> playerList = null;
        if (room != null) {
           playerList = (LinkedList) room.get("playerList");
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(playerList);
        for (WebSocketServer item : webSocketSet) {
            try {
                sendInfo(s,roomId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 实现服务器主动推送 WebsocketDto已实现默认toString方法
     */
    public void sendMessage(WebsocketDto websocketDto) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(websocketDto);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("room") String roomId) throws IOException {
        log.info("推送消息到窗口"+roomId+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(roomId==null) {
                    item.sendMessage(message);
                }else if(item.roomId.equals(roomId)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
