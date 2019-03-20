package cn.autowired.tgh.dto;

import cn.autowired.tgh.dto.base.BaseGameDto;
import cn.autowired.tgh.utils.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;

/**
 * @Description: 用于websocket传输的数据对象,主要由操作命令code 和 数据传输对象 data(pojo) 构成
 * @author: Bacchusgift
 * @Date: 2019/3/20 14:27
 * @version: 1.0.0
 */
@Data
public class WebsocketDto<T extends BaseGameDto> {

    /**
     * 发起通讯客户端的用户id  一般为 openid
     */
    @JsonProperty
    private String userId;

    /**
     * 发起通讯客户端的roomId
     */
    @JsonProperty
    private String roomId;

    /**
     *  继承自BaseGameDto的不同游戏数据传输对象
     */
    @JsonProperty
    private T data;


    @Override
    public String toString() {
        String jsonStr = null;
        try {
             jsonStr = JacksonUtils.bean2Json(this.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
