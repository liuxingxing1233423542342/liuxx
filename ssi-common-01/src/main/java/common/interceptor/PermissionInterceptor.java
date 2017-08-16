package common.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import common.utils.JedisUtil;
import common.utils.JsonUtil;

public class PermissionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		

		HttpSession session = request.getSession();
		Object obj = session.getAttribute("userInfo");
		String json = new Gson().toJson(obj);
		int userID = new JsonParser().parse(json).getAsJsonObject().get("userId").getAsInt();
		//获取用户的访问uri，与权限的url做对比，匹配上就向后执行，匹配不上，重定向到无权限页面
		String uri = request.getRequestURI();
		//取出redis中保存的菜单列表
		String string = JedisUtil.getString(userID + "#tree_list");
		
		int flag = 0;
		//List<Map<String, Object>>
		List<Map<String, Object>> treeList = JsonUtil.fromJson(string, new ArrayList<Map<String, Object>>(){}.getClass());
		for (Map<String, Object> map : treeList) {
			Object href = map.get("url");
			if (null == href) {
				continue;
			} else if (uri.contains(href.toString())) {
				flag = 1;
				break;
			}
		}
		
		if (1 == flag) {
			return true;
		} else {
			return false;
		}
	}
}
