package cn.autowired.tgh.web.api;

import cn.autowired.tgh.base.ResultData;
import cn.autowired.tgh.common.enumcase.ErrorCode;
import cn.autowired.tgh.dto.RoomInfoDto;
import cn.autowired.tgh.utils.IdUtils;
import cn.autowired.tgh.utils.RedisUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
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

    public String setRoomId(){
        String roomId = IdUtils.roomId();
        String value = (String) redisUtil.get(roomId, 0);
        if (value != null) {
            setRoomId();
        } else {
            redisUtil.set(roomId,"",2*60*60L);
        }
        return roomId;
    }

    @PostMapping("/createRoom")
    public ResultData createRoom(@RequestBody RoomInfoDto roomInfo){
        String roomId = setRoomId();
        HashMap<String, Object> room = Maps.newHashMap();
        room.put("playerList",roomInfo.getPlayerList());
        room.put("roomSize",roomInfo.getSize());
        room.put("gameId",roomInfo.getGameId());

        redisUtil.hmset(roomId,room,2*60*60L);
        HashMap<Object, Object> map = Maps.newHashMap();

        return ResultData.ok(map);
    }

    @GetMapping("/joinRoom/{roomId}")
    public ResultData joinRoom(@PathVariable String roomId) {
        Map<Object, Object> room = redisUtil.hmget(roomId);
        if (null == room) {
          return ResultData.bad(ErrorCode.HAS_NO_ROOM);
        }
        return ResultData.ok(room);
    }

    @GetMapping("/exitRoom/{roomId}")
    public ResultData exitRoom(@PathVariable String roomId) {
        redisUtil.del(roomId);
        return ResultData.ok();
    }
}
