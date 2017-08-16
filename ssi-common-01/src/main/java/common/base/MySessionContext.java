package common.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonParser;



public class MySessionContext {
	
private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	
	public static void addSession(String userID, HttpSession session) {
		sessionMap.put(userID, session);
	}
	
	public static void removeSession(String userID,HttpSession session) {
		HttpSession httpSession = sessionMap.get(userID);
		if (null != httpSession) {
			//使原来存在的session失效
			//判断session是否失效，没有失效的让他失效
			String json = new Gson().toJson(httpSession);
			boolean valid = new JsonParser().parse(json).getAsJsonObject()
					.get("session").getAsJsonObject()
					.get("isValid").getAsBoolean();
			if (valid && !httpSession.getId().equals(session.getId())) {
				httpSession.invalidate();
			}
		}
	}
	
	public static HttpSession getSession(String userID) {
		return sessionMap.get(userID);
	}
}
