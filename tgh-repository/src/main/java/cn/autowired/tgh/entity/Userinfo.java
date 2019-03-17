package cn.autowired.tgh.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author bacchus
 * @since 2019-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Userinfo extends Model<Userinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户openId
     */
    @TableField("openId")
    private String openId;

    /**
     * 用户unionId
     */
    @TableField("unionId")
    private String unionId;

    /**
     * 房间ID
     */
    @TableField("roomId")
    private Integer roomId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
