package com.kevin.mock.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import static com.kevin.mock.constant.ConstantField.*;

/**
 * @description: 请求的更新信息DTO
 * @author: kevinLiu
 * @date: 2021/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMsgReqDTO implements Serializable {

    private static final long serialVersionUID = 9109828165676599139L;
    /**
     * @description: 用户Id
     */
    private String userId;
    /**
     * @description: 昵称
     */
    @Length(max = 32, message = NICKNAME_THROUGH_LONG)
    @NotEmpty(message = UPDATE_MSG_EMPTY_MESSAGE)
    private String nickname;

    /**
     * @description: 地址
     */
    @Length(max = 255, message = ADDRESS_THROUGH_LONG)
    @NotEmpty(message = UPDATE_ADDRESS_EMPTY_MESSAGE)
    private String address;



}
