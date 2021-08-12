package com.kevin.mock.dao.mapper;

import com.kevin.mock.dao.model.User;
import com.kevin.mock.dto.req.UpdateMsgReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @description: 用户Dao层
 * @author: kevinLiu
 * @date: 2021/8/4
 */
@Mapper
public interface UserMapper {

    /**
     * @param: user 注册传入的用户
     * @description: 注册用户是否成功
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: java.lang.Integer
     */
    Integer registerUser(User user);
    /**
     * @param: email
     * @description: 根据邮箱查询用户
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dao.model.User
     */
    User queryUserByEmail(String email);
    /**
     * @param: userId
     * @description: 根据userId查询用户
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    User queryUserByUserId(String userId);
    /**
     * @param: userId
     * @param: nickname
     * @param: address
     * @description: 根据id对信息进行更新
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    Integer updateUserById(UpdateMsgReqDTO updateMsgReqDTO);
    /**
     * @param: updatePwdReqDTO
     * @description: 根据id对密码进行更新
     * @author: kevinLiu
     * @date: 2021/8/12
     * @throws
     * @return: java.lang.Integer
     */
    Integer updatePasswordById(UpdatePwdReqDTO updatePwdReqDTO);

}
