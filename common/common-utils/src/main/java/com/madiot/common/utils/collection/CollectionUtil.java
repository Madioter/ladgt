package com.madiot.common.utils.collection;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author hl-tanyong
 * @version $Id: CollectionUtil.java, v 0.1 2015年9月18日 下午4:30:24 hl-tanyong Exp
 *          $
 */
public class CollectionUtil {
	/**
	 * 判断<code>Collection</code>是否为<code>null</code>或空数组<code>[]</code>。
	 *
	 * @param collection
	 * @return 如果为空, 则返回<code>true</code>
	 * @see Collection
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null) || (collection.size() == 0);
	}

	/**
	 * 判断<code>Map</code>是否为<code>null</code>或空<code>{}</code>
	 *
	 * @param map
	 * @return 如果为空, 则返回<code>true</code>
	 * @see Map
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null) || (map.size() == 0);
	}

	public static <K extends Enum<K>, V> EnumMap<K, V> getEnumMap(Class<K> keyType) {
		return new EnumMap<K, V>(keyType);
	}
}
