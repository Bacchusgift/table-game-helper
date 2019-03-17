
package cn.autowired.tgh.common.enumcase;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/8 14:56
 * @version: 1.0.0
 */
public enum Error {
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
    HAS_NO_AUTH(-2,"没有权限");

    /**
     * 错误的代码
     */
    private final int errCode;

    /**
     * 错误的默认提示信息
     */
    private final String errInfo;

    Error(int errCode, String errInfo) {
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

