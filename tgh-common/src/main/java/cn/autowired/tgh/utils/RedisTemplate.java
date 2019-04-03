package cn.autowired.tgh.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 9:45
 * @version: 1.0.0
 */

public class RedisTemplate extends org.springframework.data.redis.core.RedisTemplate {

    public static ThreadLocal<Integer> indexdb = ThreadLocal.withInitial(() -> 0);

    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        try {
            Integer dbIndex = indexdb.get();
            //如果设置了dbIndex
            if (dbIndex != null) {
                if (connection instanceof JedisConnection) {
                    if (((JedisConnection) connection).getNativeConnection().getDB().intValue() != dbIndex) {
                        connection.select(dbIndex);
                    }
                } else {
                    connection.select(dbIndex);
                }
            } else {
                connection.select(0);
            }
        } finally {
            indexdb.remove();
        }
        return super.preProcessConnection(connection, existingConnection);
    }


}

