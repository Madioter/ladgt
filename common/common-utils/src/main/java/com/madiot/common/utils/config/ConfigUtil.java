package com.madiot.common.utils.config;

import com.madiot.common.utils.string.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author chenjie
 */
public class ConfigUtil {

	private static Map<String, Properties> cache = new HashMap<String, Properties>();

	/**
	 * 加载properties文件， 默认读取缓存
	 *
	 * @param filePath
	 *          文件路径,相对于classpath路径
	 * @return {@code Properties}, 如果filePath为空返回null
	 */
	public static Properties loadProperties(String filePath) {
		return loadProperties(filePath, true);
	}

	/**
	 * 加载properties文件
	 *
	 * @param filePath
	 *          文件路径,相对于classpath路径
	 * @param isCache
	 *          {@code true} 读取缓存, 否则实时加载
	 * @return {@code Properties}, 如果filePath为空返回null
	 */
	public static Properties loadProperties(String filePath, boolean isCache) {
		if (isCache) {
			Properties props = cache.get(filePath);
			if (null != props) {
				return props;
			}
		}

		if (!StringUtils.isBlank(filePath)) {
			Properties props = new Properties();
			InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
			try {
				if (inputStream == null) {
					return null;
				}
				props.load(inputStream);
				cache.put(filePath, props);
				return props;
			} catch (IOException e) {
			}
		}

		return null;
	}

	/**
	 * 获取Config.properties配置信息
	 *
	 * @param key
	 *          键
	 * @return 值
	 */
	public static String getString(String key) {
		if (key == null) {
			return null;
		}
		Properties props = PropertiesUtil.loadProperties("config.properties");
		if (props == null) {
			return null;
		}
		return props.getProperty(key);
	}

	/**
	 * 获取Config.properties配置信息
	 *
	 * @param key
	 *          键
	 * @return 值
	 */
	public static String getString(String key, String defaultValue) {
		String result = getString(key);
		if (result != null) {
			return result;
		} else {
			return defaultValue;
		}
	}

	/**
	 * 获取Config.properties配置信息
	 *
	 * @param key
	 * @return
	 */
	public static Integer getInt(String key, Integer defaultValue) {
		String result = getString(key);
		if (result != null) {
			return Integer.valueOf(result);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 获取Config.properties配置信息
	 *
	 * @param key
	 * @return
	 */
	public static String[] getStrArray(String key) {
		String result = getString(key);
		if (!StringUtils.isBlank(result)) {
			return result.split(",");
		} else {
			return null;
		}
	}
}
