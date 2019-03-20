
package cn.autowired.tgh.common.enumcase;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/8 14:56
 * @version: 1.0.0
 */
public enum ErrorCode {
    /**
     * 成功提示
     */
    SUCCESS(0,"成功!"),
    /**
     * 网络连接失败提示
     */
    DISCONNECTED(-1,"网络连接出现中断,请稍候重试"),

    /**
     * 用户没有权限
     */
    HAS_NO_AUTH(-2,"没有权限"),

    /**
     *  登录出现用户名或密码错误
     */
    WRONG_USERNAME_OR_PASSWORD(-3,"用户名或密码错误"),

    /**
     * 登录信息过期
     */
    LOGOUT(-4,"登录信息已过期"),

    HAS_NO_ROOM(-5,"查无此房间"),

    ROOM_FULL(-6,"房间已经满员");

    /**
     * 错误的代码
     */
    @JsonProperty
    private final int errCode;

    /**
     * 错误的默认提示信息
     */
    @JsonProperty
    private final String errInfo;

    ErrorCode(int errCode, String errInfo) {
        this.errCode = errCode;
        this.errInfo = errInfo;
    }

    /**
     * 默认toString方法改为返回错误代码
     * @return 错误代码
     */
    @Override
    public String toString(){
        return String.valueOf(this.errCode);
    }

    /**
     * 获取错误提示
     * @return
     */
    public String getErrInfo() {
        return this.errInfo;
    }
}

