package cn.autowired.tgh.web.api;


import cn.autowired.tgh.base.ResultData;
import cn.autowired.tgh.common.BaseController;
import cn.autowired.tgh.common.enumcase.ErrorCode;
import cn.autowired.tgh.dto.Code2SessionRes;
import cn.autowired.tgh.dto.UserInfoDto;
import cn.autowired.tgh.entity.UserInfo;
import cn.autowired.tgh.service.IUserInfoService;
import cn.autowired.tgh.utils.IdUtils;
import cn.autowired.tgh.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;


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
    public IUserInfoService userInfoService;

    @Autowired
    public RedisUtil redisUtil;


    @GetMapping("/wxlogin")
    public ResultData wxLogin(@RequestParam(name = "js_code") String jsCode) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc1aa242dc37efcae&secret=0968b34ba6df2964787d5044032dd1f2&js_code="+jsCode+"&grant_type=authorization_code";
        ResponseEntity<Code2SessionRes> code2SessionResResponseEntity = restTemplate.getForEntity(url, Code2SessionRes.class);
        String key = "";
        String value = "";
        String openid ="";
        if (code2SessionResResponseEntity.getStatusCode().is2xxSuccessful()){
            Code2SessionRes code2SessionRes = code2SessionResResponseEntity.getBody();
            UserInfo userinfo = new UserInfo();
            openid = code2SessionRes.getOpenid();
            String session_key = code2SessionRes.getSession_key();
             value = IdUtils.enWxLoginSessionAndOpenId(session_key, openid);
             key = "sessionKey"+openid;
            //混合用户的sessionkey和openid存入redis 过期时间为2小时,将key返回给小程序作为登录态
            redisUtil.set(key,value,2*60*60L);
            UserInfo existsUser = userInfoService.getOne((Wrapper<UserInfo>) new QueryWrapper().eq("openid", openid));
            if (null != existsUser) {

            } else {
                userinfo.setOpenId(openid);
                userInfoService.saveOrUpdate(userinfo);
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("userkey", key);
        hashMap.put("openid",openid);
        return ResultData.ok(hashMap);
    }

    @PostMapping("/setUserInfo/{openid}")
    public ResultData saveOrUpdateUserInfo(@PathVariable String openid,@RequestBody UserInfoDto userInfoDto) {
        UserInfo existsUser = userInfoService.getOne((Wrapper<UserInfo>) new QueryWrapper().eq("openid", openid));
        if (userInfoDto != null) {
            if (null != existsUser) {
                existsUser.setCity(userInfoDto.getCity());
                existsUser.setCountry(userInfoDto.getCountry());
                existsUser.setSex(userInfoDto.getGender());
                existsUser.setAvatarUrl(userInfoDto.getAvatarUrl());
                existsUser.setNickname(userInfoDto.getNickName());
                existsUser.setProvince(userInfoDto.getProvince());
                userInfoService.saveOrUpdate(existsUser);
                return ResultData.ok();
            } else {
               return ResultData.bad(ErrorCode.WRONG_USERNAME_OR_PASSWORD);
            }
        }
        return ResultData.bad(ErrorCode.LOGOUT);
    }
}
