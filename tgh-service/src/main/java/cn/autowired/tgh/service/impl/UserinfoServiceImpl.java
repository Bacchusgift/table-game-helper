package cn.autowired.tgh.service.impl;

import cn.autowired.tgh.service.IUserinfoService;
import cn.autowired.tgh.dao.UserinfoMapper;
import cn.autowired.tgh.entity.Userinfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bacchus
 * @since 2019-03-12
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

}
