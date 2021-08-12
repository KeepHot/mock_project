package com.kevin.mock.dao.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @description: 对应数据库表中的User实体类
 * @author: kevinLiu
 * @date: 2021/8/6
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 3702345123998767205L;
    /**
     * @description: user的主键
     */
    private Integer userId;
    /**
     * @description: user的邮箱
     */
    private String email;
    /**
     * @description: user的盐值
     */
    private String pwdSalt;
    /**
     * @description: user的密码
     */
    private String password;
    /**
     * @description: user的昵称
     */
    private String nickname;
    /**
     * @description: user的地址
     */
    private String address;
    /**
     * @description: user的创建时间
     */
    private LocalDateTime createAt;
    /**
     * @description: user的更新时间
     */
    private LocalDateTime updateAt;
}
