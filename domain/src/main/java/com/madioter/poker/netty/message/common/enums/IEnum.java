/**
 * @Title: IEnum.java
 * @Package common.data
 * @Description: 枚举类型接口
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.common.enums;

/**
 * @ClassName: IEnum
 * @Description: 枚举类型接口，用于统一所有的枚举类，并方便获取键和值
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public interface IEnum {

    /**
     * 获取编码值
     * @return code
     */
    public Integer getCode();

    /**
     * 获取中文名称
     * @return name
     */
    public String getName();
}
