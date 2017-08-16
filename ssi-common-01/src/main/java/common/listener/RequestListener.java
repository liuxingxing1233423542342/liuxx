package common.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;



public class RequestListener implements ServletRequestListener {
	/* (non-Javadoc)    
	 * @see org.springframework.web.context.request.RequestContextListener#requestInitialized(javax.servlet.ServletRequestEvent)    
	 		创建一个request请求
	 */
	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		HttpServletRequest servletRequest = (HttpServletRequest) requestEvent.getServletRequest();
		System.out.println("用户创建了一个请求：" + servletRequest.getRequestURI());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		
	}
}
