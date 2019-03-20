package cn.autowired.tgh.dto.base;

import java.util.LinkedList;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/20 21:31
 * @version: 1.0.0
 */
public class WolfGameDto extends BaseGameDto {

    public WolfGameDto(String gameCode, String status, LinkedList playerList) {
        super("0", status, playerList);
    }
}
