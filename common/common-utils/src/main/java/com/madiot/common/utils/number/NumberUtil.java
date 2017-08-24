package com.madiot.common.utils.number;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author chenjie
 *
 */
public class NumberUtil {

	/**
	 * 与记算操作
	 * 
	 * @param value1
	 *          value1
	 * @param value2
	 *          value2
	 * @return boolean
	 */
	public static boolean andOperate(int value1, int value2) {
		if ((value1 & value2) == value2) {
			return true;
		}
		return false;
	}

	/**
	 * getRandomInt
	 * 
	 * @param min
	 *          min
	 * @param max
	 *          max
	 * @return int
	 */
	public static int getRandomInt(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}

	/**
	 * parseInt
	 * 
	 * @param in
	 *          in
	 * @return int
	 */
	public static int parseInt(String in) {
		int re = 0;
		if (!StringUtils.isEmpty(in)) {
			try {
				re = Integer.parseInt(in);
			} catch (Exception e) {
				re = 0;
			}
		}
		return re;
	}

	/**
	 * parseLong
	 * 
	 * @param in
	 *          in
	 * @return long
	 */
	public static long parseLong(String in) {
		long re = 0;
		if (!StringUtils.isEmpty(in)) {
			try {
				re = Long.parseLong(in);
			} catch (Exception e) {
				re = 0;
			}
		}
		return re;
	}

	/**
	 * parseDouble
	 * 
	 * @param in
	 *          in
	 * @return double
	 */
	public static double parseDouble(String in) {
		double re = 0;
		if (!StringUtils.isEmpty(in)) {
			try {
				re = Double.parseDouble(in);
			} catch (Exception e) {
				re = 0;
			}
		}
		return re;
	}

	/**
	 * parseInt
	 * 
	 * @param in
	 *          in
	 * @return int[]
	 */
	public static int[] parseInt(String[] in) {
		int[] arr = new int[in.length];
		int i = 0;
		for (String s : in) {
			arr[i] = parseInt(s);
			i++;
		}
		return arr;
	}
}
