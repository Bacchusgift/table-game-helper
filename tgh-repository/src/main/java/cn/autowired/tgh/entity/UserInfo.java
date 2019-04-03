package cn.autowired.tgh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author bacchus
 * @since 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 用户性别 1是男 2是女 0是未知
     */
    private Integer sex;

    /**
     * 用户是否关注此公众号
     */
    private Integer subscribe;

    /**
     * 用户最后一次关注此公众号的时间
     */
    private String subscribeTime;

    /**
     * 用户国家
     */
    private String country;

    /**
     * 用户省份
     */
    private String province;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 用户手机号
     */
    private String tel;

    /**
     * 用户昵称
     */
    private String nickname;

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
     * 当前所在房间ID
     */
    @TableField("roomId")
    private Integer roomId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }


}
