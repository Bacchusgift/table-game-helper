package cn.autowired.tgh.web.api;

import cn.autowired.tgh.base.ResultData;
import cn.autowired.tgh.common.enumcase.ErrorCode;
import cn.autowired.tgh.dto.RoomInfoDto;
import cn.autowired.tgh.entity.UserInfo;
import cn.autowired.tgh.service.IUserInfoService;
import cn.autowired.tgh.utils.IdUtils;
import cn.autowired.tgh.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 17:23
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public WebSocketServer webSocketServer;

    @Autowired
    public IUserInfoService userInfoService;
    public String setRoomId(){
        String roomId = IdUtils.roomId();
        boolean hasKey = redisUtil.hasKey(roomId);
        if (hasKey) {
            setRoomId();
        }
        return roomId;
    }

    /**
     * 创建房间
     * @param roomInfo
     * @return
     */
    @PostMapping("/createRoom")
    public ResultData createRoom(@RequestBody RoomInfoDto roomInfo){
        String roomId = setRoomId();
        String ownerOpenid = roomInfo.getOwner();
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("openid", ownerOpenid));
        HashMap<String, Object> room = Maps.newHashMap();
        LinkedList playerList = roomInfo.getPlayerList();
        HashMap<String,Object> playerMap = Maps.newHashMap();
        if (null != userInfo) {
            playerMap.put("openid",ownerOpenid);
            playerMap.put("userInfo",userInfo);
            playerList.push(playerMap);
        }
        room.put("playerList",playerList);
        room.put("roomSize",roomInfo.getSize());
        room.put("gameId",roomInfo.getGameId());
        room.put("owner", ownerOpenid);
        redisUtil.hmset(roomId,room,2*60*60L);
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("roomId",roomId);

        return ResultData.ok(map);
    }

    /**
     * 加入房间
     * @param roomId
     * @param openId
     * @return
     */
    @GetMapping("/joinRoom/{roomId}")
    public ResultData joinRoom(@PathVariable String roomId,@RequestParam String openId) {
        //从redis里取出房间
        Map<Object, Object> room = redisUtil.hmget(roomId);
        if (room.size() == 0) {
            return ResultData.bad(ErrorCode.HAS_NO_ROOM);
        }
        LinkedList<Map<String, Object>> playerList = (LinkedList<Map<String, Object>>) room.get("playerList");
        boolean flag = true;
        if (playerList.size() >= (int)room.get("roomSize")){
            return ResultData.ok(ErrorCode.ROOM_FULL);
        }
        for (Map userinfos : playerList) {
            if (openId.equals(userinfos.get("openid"))) {
                flag = false;
            }
        }
        if (flag) {
            HashMap map = new HashMap();
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("openid", openId));
            if (null != userInfo) {
                map.put("openid",openId);
                map.put("userInfo",userInfo);
                playerList.add(map);
            }
            room.put("playerList",playerList);
            redisUtil.hmset(roomId,room);
        }
        return ResultData.ok(room);
    }

    @GetMapping("/exitRoom/{roomId}")
    public ResultData exitRoom(@PathVariable String roomId,@RequestParam String openid) {
        Map<Object, Object> room = redisUtil.hmget(roomId);
        if (room.size() == 0) {
            return ResultData.bad(ErrorCode.HAS_NO_ROOM);
        }
        LinkedList<Map<String, Object>> playerList = (LinkedList<Map<String, Object>>) room.get("playerList");

        for (Map<String, Object> stringUserInfoDtoMap : playerList) {
            String playerOpenid = (String) stringUserInfoDtoMap.get("openid");
            if (openid.equals(playerOpenid)) {

            }
        }
        return ResultData.ok();
    }

    @GetMapping("/prepareData/{roomId}")
    public ResultData prepareData(@PathVariable String roomId) throws IOException {
        Map<Object, Object> room = redisUtil.hmget(roomId);
        LinkedList<Map<String,Object>> playerList = (LinkedList) room.get("playerList");
        return ResultData.ok(playerList);
    }
}
