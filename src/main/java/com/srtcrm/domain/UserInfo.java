package com.srtcrm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String openid;
    private Integer permissions;
    private String name;
    private String phone;
}
