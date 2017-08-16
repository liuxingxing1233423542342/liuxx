package com.jk.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jk.model.user.UserRequest;
import com.jk.model.user.UserResponse;
import com.jk.service.UserService;

@Component
public class MskTask {
	@Resource
	private UserService userService;
	//  设置时间  秒 分  时  周  月 年
	@Scheduled(cron = "0 51 11 * * ?")
	public void sayHello(){		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("葫芦娃，救爷爷>>"+sdf.format(new Date()));
	}
	//  每隔三秒钟 触发一次  , 项目启动 5 秒后触发
	@Scheduled(fixedRate = 3000, initialDelay = 5000)
	public void sendMsg(){
		UserRequest userRequest = new UserRequest();
		int selectUserCount = userService.selectUserCount(userRequest);
		userRequest.setTotalCount(selectUserCount);
		userRequest.calculate();
		List<UserResponse> selectUserList = userService.selectUserList(userRequest);
		for (UserResponse userResponse : selectUserList) {
			System.out.println(userResponse.getUserName());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("你的朋友,小哪吒>>"+sdf.format(new Date()));
	}
	
}
