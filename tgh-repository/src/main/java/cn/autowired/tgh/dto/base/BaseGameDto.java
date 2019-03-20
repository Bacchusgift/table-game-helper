package cn.autowired.tgh.dto.base;

import cn.autowired.tgh.entity.UserInfo;
import lombok.Data;

import java.util.LinkedList;
import java.util.Map;


/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/20 21:14
 * @version: 1.0.0
 */
@Data
public class BaseGameDto {

    /**
     *  游戏代码
     *  0: wolf killer 狼人杀
     *  1:
     *  2:
     */
    protected String gameCode;

    /**
     *  状态代码
     *  0: 未准备
     *  1: 已准备
     *  2:
     *
     */
    protected String status;

    /**
     * 当前玩家列表
     */
    protected LinkedList<Map<String, UserInfo>> playerList;


    public BaseGameDto(String gameCode,String status,LinkedList playerList) {
        this.gameCode = gameCode;
        this.playerList = playerList;
        this.status = status;
    }



}
