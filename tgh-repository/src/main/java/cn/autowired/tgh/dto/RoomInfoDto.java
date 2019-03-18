package cn.autowired.tgh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 17:51
 * @version: 1.0.0
 */
@Data
@ToString
public class RoomInfoDto implements Serializable {

    /**
     * 房间大小
     */
    @JsonProperty
    private int size;

    /**
     * 房间游戏类别
     */
    @JsonProperty
    private int gameId;

    /**
     * 房间玩家列表
     */
    @JsonProperty
    private ArrayList playerList;

    /**
     * 房主
     */
    @JsonProperty
    private String owner ;


}
