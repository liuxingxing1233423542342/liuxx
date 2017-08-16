package common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		String id = se.getSession().getId();
		System.out.println("创建了一个session会话：" );
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		
	}

}
