package com.kevin.mock;

import com.kevin.mock.dao.mapper.UserMapper;
import com.kevin.mock.dao.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@MapperScan(value = "com.kevin.mock.dao.mapper")
@SpringBootTest
class MockProjectApplicationTests {

	@Autowired
	private static UserMapper userMapper;

	public static void main(String[] args) {
		userMapper.queryUserByEmail("wqeqwe@qq.com");
//		userMapper.registerUser(new User());
	}

}
