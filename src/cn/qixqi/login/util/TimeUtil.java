package cn.qixqi.login.util;

import java.util.Date;

public class TimeUtil {
	
	/**
	 * time1 - time2 的秒数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long timeInterval(Date time1, Date time2) {
		return (time1.getTime() - time2.getTime()) / 1000;
	}
	
	
	/**
	 * 判断验证码是否有效
	 * @param sendTime	发送时间
	 * @param interval	最大时间间隔（秒)
	 * @return
	 */
	public static boolean isValid(Date sendTime, int interval) {
		if (timeInterval(new Date(), sendTime) > interval) {
			return false;
		}
		return true;
	}
	
}
