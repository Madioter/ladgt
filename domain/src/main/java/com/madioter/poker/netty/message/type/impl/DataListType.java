/**
 * @Title: DataListType.java
 * @Package common
 * @Description: 数据列表项
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.common.exception.ParserException;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.common.validate.IValid;
import com.madioter.poker.netty.message.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yi.Wang2
 * @ClassName: DataListType
 * @Description: 数据列表项类型
 * @date 2017/2/9
 */
public class DataListType<E extends IDataListItem> implements IEnCodeAble, IDecodeAble {

    /**
     * 对象的构造方法，用于快速构造对象
     *
     * @param clz 数据类型
     * @param <T> 本工程中的所有列表项需要实现IDataListItem，这里才可以正常使用
     * @return 列表类型对象
     */
    public static <T extends IDataListItem> DataListType<T> newInstance(Class<T> clz) {
        return new DataListType<T>(clz, null);
    }

    /**
     * 对象的构造方法，用于快速构造对象
     *
     * @param clz        数据类型
     * @param <T>        本工程中的所有列表项需要实现IDataListItem，这里才可以正常使用
     * @param countValid 长度有效值验证
     * @return 列表类型对象
     */
    public static <T extends IDataListItem> DataListType<T> newInstance(Class<T> clz, IValid countValid) {
        return new DataListType<T>(clz, countValid);
    }

    /**
     * 对象的构造方法，用于快速构造对象
     *
     * @param numType 总数对应的数据长度
     * @param clz     数据类型
     * @param <T>     本工程中的所有列表项需要实现IDataListItem，这里才可以正常使用
     * @return 列表类型对象
     */
    public static <T extends IDataListItem> DataListType<T> newInstance(int numType, Class<T> clz) {
        return new DataListType<T>(clz, numType, null);
    }

    /**
     * 对象的构造方法，用于快速构造对象
     *
     * @param numType 总数对应的数据长度
     * @param clz     数据类型
     * @param <T>     本工程中的所有列表项需要实现IDataListItem，这里才可以正常使用
     * @param valid   有效值验证
     * @return 列表类型对象
     */
    public static <T extends IDataListItem> DataListType<T> newInstance(int numType, Class<T> clz, IValid valid) {
        return new DataListType<T>(clz, numType, valid);
    }

    /**
     * 数据总长度
     */
    private IIntAble count;

    /**
     * 数据项列表
     */
    private List<E> dataList;

    /**
     * 总数的数据长度，默认为一字节
     */
    private int numType = MessageConstants.BYTE_SIZE;

    /**
     * 类表项的数据类型，用于解析byte数组的时候自动构建对象
     */
    private Class<E> clz;

    /**
     * 构造方法
     *
     * @param clz 类表项的数据类型
     */
    private DataListType(Class<E> clz, IValid valid) {
        this(clz, MessageConstants.BYTE_SIZE, valid);
    }

    /**
     * 构造方法
     *
     * @param numType 总数字段占用字节数
     * @param clz     类表项的数据类型
     */
    private DataListType(Class<E> clz, int numType, IValid valid) {
        this.numType = numType;
        this.clz = clz;
        if (numType == 1) {
            this.count = new ByteType((byte) 0x00);
        } else if (numType == 2) {
            this.count = new WordType(new byte[]{0x00, 0x00});
        } else {
            this.count = new DWordType(new byte[]{0x00, 0x00, 0x00, 0x00});
        }
        if (valid != null) {
            ((ByteArrayType) this.count).setValid(valid);
        }
        this.dataList = new TypeList<E>(this.clz);
    }

    /**
     * 新增对象
     *
     * @param e 对象
     */
    public void addData(E e) {
        if (dataList == null) {
            dataList = new ArrayList<E>();
        }
        this.dataList.add(e);
        this.count.setInt(dataList.size());
    }

    /**
     * 删除对象
     *
     * @param e 对象
     * @return 是否删除成功
     */
    public boolean removeData(E e) {
        return dataList.remove(e);
    }

    @Override
    public void encode(ByteBuffer command) {
        // 按指定长度设置
        byte[] countByte = ByteUtils.intToBytes(count.getInt(), numType);
        command.write(countByte);
        if (count.getInt() == 0) {
            // 返回总数为0
            return;
        }
        if (count.getInt() != dataList.size()) {
            throw new ParserException(String.format("数据长度和总数不匹配，size：%s, 数据长度：%s", count, dataList.size()));
        }
        for (int i = 0; i < count.getInt(); i++) {
            dataList.get(i).encode(command);
        }
    }

    /**
     * list中的解码方法
     *
     * @param code 数据
     */
    @Override
    public void decode(ByteBuffer code) {
        ((ByteArrayType) this.count).decode(code);
        // 无效值处理：
        // 1、超过数据定义范围的，直接抛出异常，例如N个可充电储能装置，有效值范围：1-250，如果出现了0或251，应该抛出异常
        // 2、无效和异常数据处理，OxFE表示异常，0xFF表示无效，如果值等于异常或无效时，返回false（不抛出异常），后面的具体数据项不进行解析
        if (!((ByteArrayType) this.count).isValid()) {
            return;
        }
        this.dataList = new ArrayList<E>(this.count.getInt());
        if (count.getInt() != 0) {
            int size = this.count.getInt();
            for (int i = 0; i < size; i++) {
                try {
                    // 解码，按列表数据类型初始化列表数据对象，这里需要所有实现列表接口的子类都具有无参构造方法
                    E e = clz.newInstance();
                    e.decode(code);
                    dataList.add(e);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new ParserException(String.format("数据对象初始化失败，clz: %s, error:%s", clz.getName(), e.getMessage()), e);
                }
            }
        }
    }

    /**
     * 获取列表项
     *
     * @return 列表数据
     */
    public List<E> getDataList() {
        return dataList;
    }

    /**
     * 获取列表总数
     *
     * @return 总数
     */
    public IIntAble getCount() {
        return count;
    }

    public Class<E> getClz() {
        return clz;
    }
}
