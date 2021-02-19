package com.hinz.mybatis;

import com.hinz.mybatis.bean.user.UserInfo;
import com.hinz.mybatis.service.user.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class MybatisApplicationTests {

	@Resource
	private UserInfoService userInfoService;

	@Test
	void contextLoads() {
	}

	@Test
	public void fun(){


		/*UserInfo info = UserInfo.builder()
				.account("hinzzz")
				.email("157957329@qq.com")
				.inviteCode("888888")
				.password("wlqqhz1234")
				.passwordSalt("wlqqhz1234")
				.mobileNo("15989197190")
				.nickName("红妆")
				.createTime(LocalDateTime.now()).build();
		boolean save = userInfoService.save(info);
		System.out.println("save = " + save);
		System.out.println("info = " + info);*/

		UserInfo userInfo = userInfoService.getById(1);
		System.out.println("userInfo = " + userInfo);

	}
}
