package cn.autowired.tgh.web.api;


import cn.autowired.tgh.base.ResultData;
import cn.autowired.tgh.common.BaseController;
import cn.autowired.tgh.common.enumcase.ErrorCode;
import cn.autowired.tgh.entity.Userinfo;
import cn.autowired.tgh.service.IUserinfoService;
import cn.autowired.tgh.dto.Code2SessionRes;
import cn.autowired.tgh.utils.IdUtils;
import cn.autowired.tgh.utils.JacksonUtils;
import cn.autowired.tgh.utils.RedisUtil;
import cn.autowired.tgh.web.configuration.RedisConfig;
import cn.autowired.tgh.web.configuration.RestTemplateConfig;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Maps;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bacchus
 * @since 2019-03-12
 */
@RestController
@RequestMapping("/user")
public class UserInfoController extends BaseController {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public IUserinfoService userinfoService;

    @Autowired
    public RedisUtil redisUtil;


    @GetMapping("/wxlogin")
    public ResultData wxLogin(@RequestParam(name = "js_code") String jsCode) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc1aa242dc37efcae&secret=0968b34ba6df2964787d5044032dd1f2&js_code="+jsCode+"&grant_type=authorization_code";
        ResponseEntity<Code2SessionRes> code2SessionResResponseEntity = restTemplate.getForEntity(url, Code2SessionRes.class);
        String key = "";
        String value = "";
        if (code2SessionResResponseEntity.getStatusCode().is2xxSuccessful()){
            Code2SessionRes code2SessionRes = code2SessionResResponseEntity.getBody();
            Userinfo userinfo = new Userinfo();
            String openid = code2SessionRes.getOpenid();
            String session_key = code2SessionRes.getSession_key();
             value = IdUtils.enWxLoginSessionAndOpenId(session_key, openid);
             key = IdUtils.sessionKey();
            //混合用户的sessionkey和openid存入redis 过期时间为2小时,将key返回给小程序作为登录态
            redisUtil.set(key,value,2*60*60L);
            userinfo.setOpenId(openid);
            userinfoService.saveOrUpdate(userinfo);
        }
        HashMap hashMap = new HashMap();
        hashMap.put(key, value);

        return ResultData.ok(hashMap);
    }

}
