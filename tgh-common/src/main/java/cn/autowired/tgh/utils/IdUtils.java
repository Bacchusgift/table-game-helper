package cn.autowired.tgh.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @Description: 生成各类key的工具
 * @author: Bacchusgift
 * @Date: 2019/3/18 13:57
 * @version: 1.0.0
 */
public class IdUtils {



    public static String roomId(){
        int random = (int) (Math.floor(Math.random()*10000))+10000;
        return String.valueOf(random);
    }




    /**
     * 得到sessionkey的id
     * @return
     */
    public static String sessionKey(){
        String sessionKey = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0,7);
        return sessionKey;
    }



    /**
     * 将sessionkey和openid混合
     * @param sessionKey
     * @param openid
     * @return
     */
    public static String enWxLoginSessionAndOpenId(String sessionKey,String openid) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sessionKey", sessionKey);
        hashMap.put("openid",openid);
        String enSessionKey = JacksonUtils.mapToJson(hashMap);
        return enSessionKey;
    }

    /**
     * 将混合后的sessionkey解开得到一个map
     * @param enSessionKey
     * @return
     * @throws IOException
     */
    public static Map<String,String> deWxLoginSessionAndOpenId(String enSessionKey) throws IOException {
        Map map = JacksonUtils.jsonToMap(enSessionKey);
        return map;
    }


}
