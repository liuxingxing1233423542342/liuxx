/** 
 * <pre>项目名称:ssi-common-01 
 * 文件名称:JedisUtil.java 
 * 包名:common.util 
 * 创建日期:2017年7月28日上午10:26:55 
 * Copyright (c) 2017, chenjh123@gmail.com All Rights Reserved.</pre> 
 */  
package common.utils;

import redis.clients.jedis.Jedis;

/**
 * 
 * 项目名称：ssi-common-01    
 * 类名称：JedisUtil    
 * 类描述：    双层判定锁  连接redis
 * 创建人：刘行行 278875075@qq.com    
 * 创建时间：2017年7月28日 下午8:05:57    
 * 修改人：刘行行 278875075@qq.com     
 * 修改时间：2017年7月28日 下午8:05:57    
 * 修改备注：       
 * @version
 */
public class JedisUtil {
	
	private static Jedis jedis = null;
	
	private static Jedis getJedis() {
		if (null == jedis) {
			synchronized (JedisUtil.class) {
				if (null == jedis) {
					jedis = new Jedis("192.168.254.128", 6379);
					
				}
			}
		}
		return jedis;
	}
	
	/**
	 * setString(这里用一句话描述这个方法的作用)   
	 * 创建人：刘行行 278875075@qq.com    
	 * 创建时间：2017年7月28日 下午8:07:00    
	 * 修改人：刘行行 278875075@qq.com     
	 * 修改时间：2017年7月28日 下午8:07:00    
	 * 修改备注： 
	 * @param k
	 * @param v
	 * @param seconds
	 * @return
	 */

	public static String setString(String k, String v, int seconds) {
		Jedis jedis = getJedis();
		String s = jedis.set(k, v);
		if (0 <= seconds) {
			jedis.expire(k, seconds);
		}
		return s;
	}
	
	/**
	 * getString(这里用一句话描述这个方法的作用)   
	 * 创建人：刘行行 278875075@qq.com    
	 * 创建时间：2017年7月28日 下午8:07:10    
	 * 修改人：刘行行 278875075@qq.com     
	 * 修改时间：2017年7月28日 下午8:07:10    
	 * 修改备注： 
	 * @param k
	 * @return
	 */
	public static String getString(String k) {
		Jedis jedis = getJedis();
		String v = jedis.get(k);
		return v;
	}

}
