/**
 * @Title: DateTime.java
 * @Package common
 * @Description: 国标的时间数据类型
 * @author Yi.Wang2
 * @date 2017/2/8
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.type.ByteArrayType;
import com.madioter.poker.netty.message.type.IGetValueAble;
import com.madioter.poker.netty.message.type.ISetValueAble;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Yi.Wang2
 * @ClassName: DateTime
 * @Description: 国标的时间数据类型
 * @date 2017/2/8
 */
public class DateTime extends ByteArrayType implements IGetValueAble<Date>, ISetValueAble<Date> {

    /**
     * 时间
     */
    private Calendar calendar;

    /**
     * 设置时间
     *
     * @param date 时间定义
     */
    public DateTime(Date date) {
        this();
        calendar.setTime(date);
    }

    /**
     * 获取当前时间
     */
    public DateTime() {
        super(MessageConstants.LONG_SIZE);
        calendar = Calendar.getInstance();
    }

    @Override
    public byte[] getBytes() {
        return ByteUtils.longToBytes(calendar.getTimeInMillis(), MessageConstants.LONG_SIZE);
    }

    @Override
    public void setData(byte[] data) {
        calendar.setTimeInMillis(ByteUtils.bytesToLong(data));
    }

    @Override
    public String toString() {
        return getYear() + "-" + getMonth() + "-" + getDay() + " " + getHour() + ":" + getMinute() + ":" + getSecond() + "." + getMillisecond();
    }

    @Override
    public Date getValue() {
        return calendar.getTime();
    }

    @Override
    public void setValue(Date value) {
        calendar.setTime(value);
    }

    /**
     * 获取年份
     *
     * @return 年
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 设置年份
     *
     * @param year 年
     */
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    /**
     * 获取月份（按自然月传递数据，注意java中的月份原始数据为0-11，国标定义为1-12）
     *
     * @return 月
     */
    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 设置月份（按自然月传递数据，注意java中的月份原始数据为0-11，国标定义为1-12）
     *
     * @param month 月
     */
    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month - 1);
    }

    /**
     * 获取当月的第几天
     *
     * @return 当月的第几天
     */
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置当月的第几天
     *
     * @param day 当月的第几天
     */
    public void setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    /**
     * 获取当天的小时（24小时制）
     *
     * @return 小时
     */
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 设置当天的小时（24小时制）
     *
     * @param hour 小时
     */
    public void setHour(int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * 获取小时中的分钟
     *
     * @return 分钟
     */
    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 设置小时中的分钟
     *
     * @param minute 分钟
     */
    public void setMinute(int minute) {
        calendar.set(Calendar.MINUTE, minute);
    }

    /**
     * 获取分钟中的秒数
     *
     * @return 秒数
     */
    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 设置分钟中的秒数
     *
     * @param second 秒
     */
    public void setSecond(int second) {
        calendar.set(Calendar.SECOND, second);
    }

    public int getMillisecond() {
        return calendar.get(Calendar.MILLISECOND);
    }

    public void setMillisecond(int millisecond) {
        calendar.set(Calendar.MILLISECOND, millisecond);
    }

    public void setDate(Date date) {
        this.calendar.setTime(date);
    }
}
