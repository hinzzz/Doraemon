package com.hinz.shiro;

import com.hinz.shiro.entity.User;
import com.hinz.shiro.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ShiroApplicationTests {

	@Autowired
	private IUserService userService;

	@Test
	void contextLoads() {
		User user = userService.findUserByUserName("brucelee");

		assert user.getDescription().equals("龙的传人");
	}

}
