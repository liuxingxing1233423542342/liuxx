/**
 *  
 */
package common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  
 * 项目名称：ssi-common-01    
 * 类名称：JsonUtil    
 * 类描述：    
 * 创建人：刘行行 278875075@qq.com    
 * 创建时间：2017年7月28日 下午8:07:30    
 * 修改人：刘行行 278875075@qq.com     
 * 修改时间：2017年7月28日 下午8:07:30    
 * 修改备注：       
 * @version
 */
 
public class JsonUtil {
	
	private static Gson gson = new Gson();
	
	/**
	 * toJsonString(这里用一句话描述这个方法的作用)   
	 * 创建人：刘行行 278875075@qq.com    
	 * 创建时间：2017年7月28日 下午8:07:57    
	 * 修改人：刘行行 278875075@qq.com     
	 * 修改时间：2017年7月28日 下午8:07:57    
	 * 修改备注： 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		if (null == obj) {
			throw new NullPointerException();
		}
		String json = gson.toJson(obj);
		return json;
	}
	
	/**
	 * fromJson(这里用一句话描述这个方法的作用)   
	 * 创建人：刘行行 278875075@qq.com    
	 * 创建时间：2017年7月28日 下午8:08:03    
	 * 修改人：刘行行 278875075@qq.com     
	 * 修改时间：2017年7月28日 下午8:08:03    
	 * 修改备注： 
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> t) {
		if (null == json) {
			throw new NullPointerException();
		}
		T obj = gson.fromJson(json, new TypeToken<T>(){}.getType());
		return obj;
	}

}
