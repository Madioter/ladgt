package com.madiot.common.spring;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yi.Wang2 on 2016/9/26.
 */
public class DateConvert implements Converter<String, Date> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DateConvert.class);

	@Override
	public Date convert(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			logger.error("日期转换出错：" + stringDate + "不符合转换格式：yyyy-MM-dd HH:mm:ss");
		}
		return null;
	}

}