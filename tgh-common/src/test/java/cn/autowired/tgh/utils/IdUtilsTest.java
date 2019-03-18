package cn.autowired.tgh.utils;

import static org.junit.Assert.*;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 18:50
 * @version: 1.0.0
 */
public class IdUtilsTest {

    @org.junit.Test
    public void roomId() {
        int random = (int) (Math.round(Math.random()))*1000+10000;
    }
}