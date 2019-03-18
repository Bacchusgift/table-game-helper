package cn.autowired.tgh.dto;

import cn.autowired.tgh.entity.Userinfo;
import lombok.Data;

/**
 * @Description: 微信登录换取session的json对象
 * @author: Bacchusgift
 * @Date: 2019/3/15 11:44
 * @version: 1.0.0
 */
@Data
public class Code2SessionRes {

    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;

}
