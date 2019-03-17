package cn.autowired.tgh.web.api;


import cn.autowired.tgh.common.BaseController;
import cn.autowired.tgh.service.IUserinfoService;
import cn.autowired.tgh.dto.Code2SessionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;


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


    @GetMapping("/wxlogin")
    public String wxLogin(@RequestParam(name = "js_code") String jsCode){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc1aa242dc37efcae&secret=0968b34ba6df2964787d5044032dd1f2&js_code="+jsCode+"&grant_type=authorization_code";
        ResponseEntity<Code2SessionRes> code2SessionResResponseEntity = restTemplate.getForEntity(url, Code2SessionRes.class);
        if (code2SessionResResponseEntity.getStatusCode().is2xxSuccessful()){
            Code2SessionRes code2SessionRes = code2SessionResResponseEntity.getBody();

        }

        return "success";
    }





}
