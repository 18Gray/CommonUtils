package com.eighteengray.commonutillibrary;


/**
 * 地理位置相关工具类
 */
public class LBSUtil
{
	/**
	 * 计算弧度
	 * @param d 直径
	 * @return
     */
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}


	/**
	 * 根据两个经纬度计算距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
     * @return
     */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378.137;
		s = Math.round(s * 10000);
		s /= 10000;
		return s;
	}

}
