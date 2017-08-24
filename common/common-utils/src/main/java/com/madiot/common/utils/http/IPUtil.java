package com.madiot.common.utils.http;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Yi.Wang2 on 2016/11/9.
 */
public class IPUtil {

	/**
	 * 获取本机所有IP地址
	 *
	 * @return
	 */
	public static List<String> getAllLocalHostIp() {
		List<String> ips = new ArrayList<String>();

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					String ip = addresses.nextElement().getHostAddress();
					if (ip.indexOf(":") == -1) {
						ips.add(ip);
					}
				}
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ips;
	}

    /**
     * enaddr
     *
     * @param strIp
     *          strIp
     * @return long
     */
    public static long enaddr(String strIp) {
        String[] iparr = strIp.split("\\.");
        if (iparr.length != 4) {
            return 0;
        }
        int[] ip = new int[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Integer.parseInt(strIp.substring(0, position1));
        ip[1] = Integer.parseInt(strIp.substring(position1 + 1, position2));
        ip[2] = Integer.parseInt(strIp.substring(position2 + 1, position3));
        ip[3] = Integer.parseInt(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * deaddr
     *
     * @param longIp
     *          longIp
     * @return String
     */
    public static String deaddr(long longIp) {
        int intIP = (int) longIp;
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((intIP >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((intIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((intIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((intIP & 0x000000FF)));
        return sb.toString();
    }
}
