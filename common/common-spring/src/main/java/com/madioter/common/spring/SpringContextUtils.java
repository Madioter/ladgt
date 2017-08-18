package com.madioter.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring容器工具类，用于获取Spring的bean Created by Yi.Wang2 on 2016/8/25.
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	/**
	 * Spring 容器
	 */
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 获取applicationContext对象
	 *
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据bean的id来查找对象
	 *
	 * @param id
	 *          bean的ID
	 * @return Bean实例
	 */
	public static Object getBeanById(String id) {
		return applicationContext.getBean(id);
	}

	/**
	 * 根据bean的class来查找对象
	 *
	 * @param c
	 *          bean 类型
	 * @param <T>
	 *          类型泛型
	 * @return Bean实例
	 */
	public static <T> T getBeanByClass(Class<T> c) {
		return applicationContext.getBean(c);
	}

	/**
	 * 根据bean的class来查找所有的对象(包括子类)
	 *
	 * @param c
	 *          bean 类型
	 * @return Bean实例集合
	 */
	public static Map getBeansByClass(Class c) {
		return applicationContext.getBeansOfType(c);
	}

}
