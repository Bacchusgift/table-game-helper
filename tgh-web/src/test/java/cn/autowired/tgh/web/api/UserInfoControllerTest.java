package cn.autowired.tgh.web.api;

import cn.autowired.tgh.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 13:43
 * @version: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoControllerTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setTest() {
        redisUtil.set("name","youzi",60l);
    }

}