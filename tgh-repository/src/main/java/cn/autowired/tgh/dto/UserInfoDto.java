package cn.autowired.tgh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 22:58
 * @version: 1.0.0
 */
@Data
public class UserInfoDto implements Serializable {

    private String province;
    private String city;
    private String avatarUrl;
    private String country;
    private String language;
    private Integer gender;
    private String nickName;

}
