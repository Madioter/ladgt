package com.madiot.common.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 一些json与对象转换的工具集合类
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static final ObjectMapper objectMapper;

	static {
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	private JsonUtils() {
	}

	public static ObjectMapper getInstance() {
		return objectMapper;
	}

	/**
	 * 使用Jackson 数据绑定 将对象转换为 json字符串
	 * <p/>
	 * 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
	 *
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error("转换为json字符串失败" + e.toString());
		} catch (JsonMappingException e) {
			logger.error("转换为json字符串失败" + e.toString());
		} catch (IOException e) {
			logger.error("转换为json字符串失败" + e.toString());
		}
		return null;
	}

	/**
	 * json字符串转化为 JavaBean
	 * <p/>
	 * 还可以直接JsonUtils.getInstance().readValue(String content,Class valueType)用这种方式
	 *
	 * @param <T>
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T toJavaBean(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonParseException e) {
			logger.error("json字符串转化为 javabean失败" + e.toString());
		} catch (JsonMappingException e) {
			logger.error("json字符串转化为 javabean失败" + e.toString());
		} catch (IOException e) {
			logger.error("json字符串转化为 javabean失败" + e.toString());
		}
		return null;
	}

	/**
	 * json字符串转化为list
	 * <p/>
	 * 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new
	 * TypeReference<List<T>>(){})方式
	 *
	 * @param <T>
	 * @param content
	 * @param typeReference
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> toJavaBeanList(String content, TypeReference<List<T>> typeReference) throws IOException {
		try {
			return objectMapper.readValue(content, typeReference);
		} catch (JsonParseException e) {
			logger.error("json字符串转化为 list失败,原因:{}", e.toString());
			throw new RuntimeException("json字符串转化为 list失败");
		} catch (JsonMappingException e) {
			logger.error("json字符串转化为 list失败,原因:{}", e.toString());
			throw new JsonMappingException("json字符串转化为 list失败");
		} catch (IOException e) {
			logger.error("json字符串转化为 list失败,原因:{}", e.toString());
			throw new IOException("json字符串转化为 list失败");
		}
	}

}
