package com.madiot.common.utils.string;

/**
 * @author chenjie
 */
public class StringUtils {
	/**
	 * 连接数组
	 *
	 * @param arr
	 * @param type
	 * @return
	 */
	public static String joinArr(Object[] arr, String type) {
		if (arr == null)
			return "";
		StringBuilder tmp = new StringBuilder();
		int i = 0;
		for (Object o : arr) {
			String s = String.valueOf(o);
			if (i > 0) {
				tmp.append(type);
			}
			tmp.append(s);
			i++;
		}
		return tmp.toString();
	}

	/**
	 * 是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "null".equals(str))
			return true;
		if (str.length() == 0)
			return true;
		return false;
	}

	/**
	 * 是否不为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null) {
			if (str.length() == 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 截取字符串
	 *
	 * @param source
	 * @param start
	 * @param end
	 * @return
	 */
	public static String findStr(String source, String start, String end) {
		if (source == null)
			return null;
		int i = 0;
		if (!isEmpty(start)) {
			i = source.indexOf(start);
			if (i < 0)
				return null;
			source = source.substring(i + start.length());
		}
		if (!isEmpty(end)) {
			i = source.indexOf(end);
			if (i < 0)
				return null;
			source = source.substring(0, i);
		}
		return source;
	}

	/**
	 * 用0补全数值
	 *
	 * @param index
	 * @param length
	 * @return
	 */
	public static String getIndexString(int index, int length) {
		String sDatesBefor = String.valueOf(index);
		while (sDatesBefor.length() < length) {
			sDatesBefor = "0" + sDatesBefor;
		}
		return sDatesBefor;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static String lowerFirst(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String upperFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}