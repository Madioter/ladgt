package com.madiot.common.utils.object;

import com.madiot.common.utils.bytes.ByteUtils;
import com.madiot.common.utils.date.DateUtils;
import com.madiot.common.utils.string.StringUtils;
import org.apache.commons.lang.ArrayUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yi.Wang2 on 2016/10/8.
 */
public class ObjectUtils {

    private static Class[] SIMPLE_TYPE = new Class[]{String.class, Long.class, Integer.class, Double.class, Date.class,
            Character.class, Float.class, int.class, long.class, double.class, float.class, Boolean.class, boolean.class, char.class};

    /**
     * 判断是否是简单数据类型
     *
     * @param object 数据
     * @return boolean 判断结果
     */
    public static boolean isSimpleType(Object object) {
        if (object == null) {
            return true;
        }
        for (Class clz : SIMPLE_TYPE) {
            if (clz.equals(object.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取数据的字符串表达式
     *
     * @param object 对象
     * @return 字符串值
     */
    public static String getSimpleTypeValue(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return DateUtils.formatDate((Date) object, DateUtils.FULL_CHINESE_PATTERN);
        } else {
            return String.valueOf(object);
        }
    }

    /**
     * 判断是否为Map数据类型
     *
     * @param object 对象
     * @return boolean 判断结果
     */
    public static boolean isMap(Object object) {
        if (object instanceof Map) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为数组或List
     *
     * @param object 对象
     * @return boolean 判断结果
     */
    public static boolean isArray(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof List) {
            return true;
        } else if (object.getClass().isArray()) {
            return true;
        }
        return false;
    }

    /**
     * 将数组对象转换为url参数
     *
     * @param key    前缀
     * @param object 列表对象
     * @return params的结果集
     */
    public static Map<String, String> getArrayValues(String key, Object object) {
        Map<String, String> params = new HashMap<String, String>();
        if (object == null) {
            return params;
        }
        if (object instanceof List) {
            for (int i = 0; i < ((List) object).size(); i++) {
                Object item = ((List) object).get(i);
                params.putAll(getObjectValues(key + "[" + i + "]", item));
            }
        } else if (object.getClass().equals(byte[].class)) {
            params.put(key, ByteUtils.bytesToHexString((byte[]) object));
        } else if (object.getClass().isArray()) {
            int index = 0;
            for (Object item : (Object[]) object) {
                params.putAll(getObjectValues(key + "[" + index + "]", item));
                index++;
            }
        }
        return params;
    }

    public static Map<String, String> getObjectValues(String key, Object item) {
        Map<String, String> params = new HashMap<String, String>();
        if (isSimpleType(item)) {
            String value = getSimpleTypeValue(item);
            if (value != null) {
                params.put(key, getSimpleTypeValue(item));
            }
        } else if (isArray(item)) {
            params.putAll(getArrayValues(key, item));
        } else if (isMap(item)) {
            params.putAll(getMapValues(key, item));
        } else {
            params.putAll(getEntityValues(key, item));
        }
        return params;
    }

    private static Map<String, String> getMapValues(String key, Object obj) {
        Map<String, String> params = new HashMap<String, String>();
        for (Object itemKey : ((Map) obj).keySet()) {
            String newKey = itemKey.toString();
            if (StringUtils.isNotEmpty(key)) {
                newKey = key + "." + newKey;
            }
            params.putAll(getObjectValues(newKey, ((Map) obj).get(itemKey)));
        }
        return params;
    }

    public static Map<String, String> getEntityValues(String key, Object obj) {
        Map<String, String> params = new HashMap<String, String>();
        if (obj == null) {
            return params;
        }
        try {
            List<JavaBeanProperty> javaBeanProperties = getPropertyOpts(obj);
            for (JavaBeanProperty property : javaBeanProperties) {
                Object result = property.getValue(obj);
                String propertyName = property.getName();
                if (!StringUtils.isBlank(key)) {
                    propertyName = key + "." + propertyName;
                }
                if (result != null && isSimpleType(result)) {
                    params.put(propertyName, getSimpleTypeValue(result));
                } else if (result != null && !isSimpleType(result)) {
                    params.putAll(getObjectValues(propertyName, result));
                }
            }
        } catch (Exception e) {

        }
        return params;
    }

    /**
     * 解析post请求参数
     *
     * @param requestMap request
     * @return parameterMap param map
     */
    public static Map<String, Object> getParamMap(Map<String, List<String>> requestMap) {
        List<String> removeKeys = new ArrayList<String>();
        Map<String, Object> param = new HashMap<String, Object>();
        for (Map.Entry<String, List<String>> en : requestMap.entrySet()) {
            String requestMapKey = en.getKey();
            Object requestMapValue = en.getValue();
            if (removeKeys.contains(requestMapKey)) {
                continue;
            }
            // 如果为对象类型的合并层级对象
            if (requestMapKey.contains(".")) {
                // 获取第一节key
                String keyFirst = requestMapKey.substring(0, requestMapKey.indexOf("."));
                Map<String, List<String>> innerParams = new HashMap<String, List<String>>();
                for (String key : requestMap.keySet()) {
                    if (key.startsWith(keyFirst)) {
                        innerParams.put(key.substring(keyFirst.length() + 1), requestMap.get(key));
                        removeKeys.add(key);
                    }
                }
                // 构建对象
                requestMapValue = getParamMap(innerParams);
                requestMapKey = keyFirst;
            }

            // 构建当前层级对象
            if (requestMapKey.matches("^.*\\[(\\d+)\\]$")) {
                // 构建列表
                Pattern pattern = Pattern.compile("^(.*)\\[(\\d+)\\]$");
                Matcher matcher = pattern.matcher(requestMapKey);
                int index = 0;
                while (matcher.find()) {
                    requestMapKey = matcher.group(1);
                    index = Integer.valueOf(matcher.group(2));
                }
                List<Object> paramValueList;
                if (param.containsKey(requestMapKey)) {
                    Object paramValue = param.get(requestMapKey);
                    if (paramValue instanceof List) {
                        paramValueList = (List) paramValue;
                    } else {
                        paramValueList = new ArrayList<Object>();
                        paramValueList.add(paramValue);
                    }
                } else {
                    paramValueList = new ArrayList<Object>();
                }
                if (paramValueList.size() < index + 1) {
                    for (int i = index - paramValueList.size(); i > 0; i--) {
                        paramValueList.add(null);
                    }
                } else if (paramValueList.get(index) == null) {
                    paramValueList.remove(index);
                }
                paramValueList.add(index, requestMapValue);
                param.put(requestMapKey, paramValueList);
            } else {
                // 构建单个值
                if (en.getValue() != null && !en.getValue().isEmpty()) {
                    if (en.getValue().size() == 1) {
                        String valueStr = en.getValue().get(0);
                        param.put(requestMapKey, valueStr);
                    } else {
                        param.put(requestMapKey, en.getValue());
                    }
                } else {
                    param.put(requestMapKey, null);
                }
            }
        }
        return param;
    }

    /**
     * Converts a map to a JavaBean.
     *
     * @param type type to convert
     * @param map  map to convert
     * @return JavaBean converted
     * @throws IntrospectionException    failed to get class fields
     * @throws IllegalAccessException    failed to instant JavaBean
     * @throws InstantiationException    failed to instant JavaBean
     * @throws InvocationTargetException failed to call setters
     */
    public static final Object toBean(Class<?> type, Map<String, ? extends Object> map)
            throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * Converts a JavaBean to a map
     *
     * @param bean JavaBean to convert
     * @return map converted
     * @throws IntrospectionException    failed to get class fields
     * @throws IllegalAccessException    failed to instant JavaBean
     * @throws InvocationTargetException failed to call setters
     */
    public static final Map<String, Object> toMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 获取java类的所有属性
     *
     * @param javaBean java类对象
     * @return 属性操作集合
     * @throws Exception 获取异常
     */
    public static List<JavaBeanProperty> getPropertyOpts(Object javaBean) throws Exception {
        List<JavaBeanProperty> javaBeanProperties = new ArrayList<JavaBeanProperty>();
        BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            if (!descriptor.getName().equals("class")) {
                javaBeanProperties.add(new JavaBeanProperty(descriptor));
            }
        }
        return javaBeanProperties;
    }

    /**
     * 获取java类的所有属性
     *
     * @param javaBean java类对象
     * @return 属性操作集合
     * @throws Exception 获取异常
     */
    public static Map<String, JavaBeanProperty> getPropertyOptMap(Object javaBean) throws Exception {
        Map<String, JavaBeanProperty> propertyMap = new HashMap<String, JavaBeanProperty>();
        BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            if (!descriptor.getName().equals("class")) {
                propertyMap.put(descriptor.getName(), new JavaBeanProperty(descriptor));
            }
        }
        return propertyMap;
    }

    /**
     * 获取java类型
     * @param value 数据
     * @param clz 类型
     * @param <T> 泛型
     * @return 反馈类型实例
     */
    public static <T> T getJavaValue(String value, Class<T> clz) {
        if (clz == Integer.class || clz == int.class) {
            return (T) Integer.valueOf(value);
        } else if (clz == Long.class || clz == long.class) {
            return (T) Long.valueOf(value);
        } else if (clz == Float.class || clz == float.class) {
            return (T) Float.valueOf(value);
        } else if (clz == Double.class || clz == double.class) {
            return (T) Double.valueOf(value);
        } else if (clz == Date.class) {
            return (T) DateUtils.parseDate(value, DateUtils.FULL_CHINESE_PATTERN);
        } else if (clz == BigDecimal.class) {
            return (T) BigDecimal.valueOf(Double.valueOf(value));
        } else if (clz == String.class) {
            return (T) value;
        } else if (clz == byte[].class || clz == Byte[].class) {
            return (T) ByteUtils.decodeHex(value);
        }
        return null;
    }

    /**
     * 解析post请求参数，目前多层级数据解析有问题，如有需求，重新编写。
     * 目前所有的返回结果中的data数据不解析
     *
     * @param requestMap request
     * @return parameterMap param map
     */
    public static Map<String, Object> toParamMap(Map<String, String[]> requestMap) {
        List<String> removeKeys = new ArrayList<String>();
        Map<String, Object> param = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> en : requestMap.entrySet()) {
            String requestMapKey = en.getKey();
            Object requestMapValue = en.getValue();
            if (removeKeys.contains(requestMapKey)) {
                continue;
            }
            // 如果为对象类型的合并层级对象
            if (requestMapKey.contains(".")) {
                // 获取第一节key
                String keyFirst = requestMapKey.substring(0, requestMapKey.indexOf("."));
                Map<String, String[]> innerParams = new HashMap<String, String[]>();
                for (String key : requestMap.keySet()) {
                    if (key.startsWith(keyFirst)) {
                        innerParams.put(key.substring(keyFirst.length() + 1), requestMap.get(key));
                        removeKeys.add(key);
                    }
                }
                // 构建对象
                requestMapValue = toParamMap(innerParams);
                requestMapKey = keyFirst;
            }

            // 构建当前层级对象
            if (requestMapKey.matches("^.*\\[(\\d+)\\]$")) {
                // 构建列表
                Pattern pattern = Pattern.compile("^(.*)\\[(\\d+)\\]$");
                Matcher matcher = pattern.matcher(requestMapKey);
                int index = 0;
                while (matcher.find()) {
                    requestMapKey = matcher.group(1);
                    index = Integer.valueOf(matcher.group(2));
                }
                List<Object> paramValueList;
                if (param.containsKey(requestMapKey)) {
                    Object paramValue = param.get(requestMapKey);
                    if (paramValue instanceof List) {
                        paramValueList = (List) paramValue;
                    } else {
                        paramValueList = new ArrayList<Object>();
                        paramValueList.add(paramValue);
                    }
                } else {
                    paramValueList = new ArrayList<Object>();
                }
                if (paramValueList.size() < index + 1) {
                    for (int i = index - paramValueList.size(); i > 0; i--) {
                        paramValueList.add(null);
                    }
                } else if (paramValueList.get(index) == null) {
                    paramValueList.remove(index);
                }
                paramValueList.add(index, requestMapValue);
                param.put(requestMapKey, paramValueList);
            } else if (requestMapValue instanceof HashMap) {
                // 当下层已经构建为对象时，直接赋值
                param.put(requestMapKey, requestMapValue);
            } else {
                // 构建单个值
                if (en.getValue() != null && !ArrayUtils.isEmpty(en.getValue())) {
                    if (en.getValue().length == 1) {
                        String valueStr = en.getValue()[0];
                        param.put(requestMapKey, valueStr);
                    } else {
                        param.put(requestMapKey, en.getValue());
                    }
                } else {
                    param.put(requestMapKey, null);
                }
            }
        }
        return param;
    }
}
