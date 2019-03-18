package cn.autowired.tgh.service.impl;

import cn.autowired.tgh.dao.UserInfoMapper;
import cn.autowired.tgh.entity.UserInfo;
import cn.autowired.tgh.service.IUserInfoService;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
