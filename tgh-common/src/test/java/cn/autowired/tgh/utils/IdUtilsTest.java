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

        for (int i = 0; i < 100; i++) {
            int random = (int) (Math.floor(Math.random()*10000))+10000;
            System.out.println(random);
        }
    }
}