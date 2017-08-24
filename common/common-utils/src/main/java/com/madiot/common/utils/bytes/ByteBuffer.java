/**
 * @Title: ByteBuffer.java
 * @Package common
 * @Description: byte数组操作类
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madiot.common.utils.bytes;

import java.util.Arrays;

/**
 * @author Yi.Wang2
 * @ClassName: ByteBuffer
 * @Description: byte数组操作类
 * @date 2017/2/9
 */
public class ByteBuffer {

    /**
     * 默认的初始化长度为32字节
     */
    private static final int DEFAULT_SIZE = 128;

    /**
     * 当前读取位
     */
    private int readIndex = 0;

    /**
     * 当前读取位
     */
    private int writeIndex = 0;

    /**
     * 数据
     */
    private byte[] bytes;

    /**
     * 增加读取标签
     */
    private int tagIndex;

    /**
     * 无参构造函数
     */
    public ByteBuffer() {
        bytes = new byte[DEFAULT_SIZE];
    }

    /**
     * 指定长度的构造函数
     *
     * @param size 数据长度
     */
    public ByteBuffer(int size) {
        bytes = new byte[size];
    }

    /**
     * 使用数组构建对象，用于读取数组数据
     *
     * @param bytes 数组
     */
    public ByteBuffer(byte[] bytes) {
        this.bytes = bytes;
        writeIndex = bytes.length;
    }

    /**
     * 读取数组数据，支持负值，往回读
     *
     * @param length 读取的字节数
     * @return byte数组
     */
    public byte[] read(int length) {
        byte[] value;
        if (length > 0) {
            value = Arrays.copyOfRange(bytes, readIndex, readIndex + length);
        } else {
            value = Arrays.copyOfRange(bytes, readIndex + length, readIndex);
        }
        this.readIndex = readIndex + length;
        return value;
    }

    /**
     * 读取一个byte位
     *
     * @return byte数据
     */
    public byte read() {
        byte[] value = Arrays.copyOfRange(bytes, readIndex, readIndex + 1);
        this.readIndex = readIndex + 1;
        return value[0];
    }

    /**
     * 读取一个byte位的int数值
     *
     * @return int数值
     */
    public int readInt() {
        byte[] value = Arrays.copyOfRange(bytes, readIndex, readIndex + 1);
        this.readIndex = readIndex + 1;
        return value[0];
    }

    /**
     * 设置标记
     */
    public void mark() {
        this.tagIndex = readIndex;
    }

    /**
     * 还原之前设置的标记
     */
    public void restore() {
        this.readIndex = this.tagIndex;
    }

    /**
     * 读取一个双字节
     *
     * @return 双字节对应的数值
     */
    public int readWord() {
        byte[] value = Arrays.copyOfRange(bytes, readIndex, readIndex + 2);
        this.readIndex = readIndex + 2;
        return ByteUtils.bytesToInt(value);
    }

    /**
     * 读取一个四字节
     *
     * @return 四字节对应的数值
     */
    public int readDWord() {
        byte[] value = Arrays.copyOfRange(bytes, readIndex, readIndex + 4);
        this.readIndex = readIndex + 4;
        return ByteUtils.bytesToInt(value);
    }

    /**
     * 写一个byte数据
     *
     * @param b byte
     */
    public void write(byte b) {
        if (writeIndex + 1 > bytes.length) {
            resizeBytes(1);
        }
        bytes[writeIndex] = b;
        writeIndex = writeIndex + 1;
    }

    /**
     * 期望扩大的数值，数组长度不足时，扩大数组长度
     *
     * @param expect 期望的长度，防止一次扩容不足会反复进行多次扩容
     */
    private void resizeBytes(int expect) {
        // 每次扩容长度为 length / 2
        int length = bytes.length;
        // 如果期望的长度大于当前应该扩容的长度，默认扩容期望长度 + 32，否则按默认的长度扩容
        if (length / 2 < expect) {
            length = length + expect + 32;
        } else {
            length = length + length / 2;
        }
        bytes = Arrays.copyOf(bytes, length);
    }

    /**
     * 写byte数组
     *
     * @param b byte数据
     */
    public void write(byte[] b) {
        if (writeIndex + b.length > bytes.length) {
            resizeBytes(b.length);
        }
        for (int i = 0; i < b.length; i++) {
            this.bytes[writeIndex + i] = b[i];
        }
        // 设置写到的位置
        writeIndex = writeIndex + b.length;
    }

    /**
     * 将另一个ByteBuffer对象中的有效数据写入到当前的ByteBuffer对象中
     *
     * @param buffer 另一个ByteBuffer对象
     */
    public void write(ByteBuffer buffer) {
        write(buffer.getBytes());
    }

    /**
     * 写入一个byte数据
     *
     * @param intValue byte对应的int值
     */
    public void writeInt(int intValue) {
        if (writeIndex + 1 > bytes.length) {
            resizeBytes(1);
        }
        bytes[writeIndex] = (byte) (intValue & 0xFF);
        writeIndex = writeIndex + 1;
    }

    /**
     * 写入一个双字节数据
     *
     * @param value 双字节对应的int数值
     */
    public void writeWord(int value) {
        if (writeIndex + 2 > bytes.length) {
            resizeBytes(2);
        }
        bytes[writeIndex] = (byte) (value & 0xFFFF);
        writeIndex = writeIndex + 2;
    }

    /**
     * 写入一个四字节数据
     *
     * @param value 字节数据对应的数值
     */
    public void writeDWord(int value) {
        if (writeIndex + 4 > bytes.length) {
            resizeBytes(4);
        }
        bytes[writeIndex] = (byte) (value & 0xFFFFFFFF);
        writeIndex = writeIndex + 4;
    }

    /**
     * 获取当前ByteBuffer中写入的有效数据
     *
     * @return byte数组
     */
    public byte[] getBytes() {
        return Arrays.copyOfRange(bytes, 0, writeIndex);
    }

    /**
     * 判断当前的数据缓存区是否可以继续读取下一个字节
     *
     * @return true 后面有未读完的内容，false 已经读取到末尾
     */
    public boolean canReadMore() {
        return readIndex < writeIndex;
    }

    /**
     * 退格
     *
     * @param count 退格指定个数的byte
     */
    public void backspace(int count) {
        this.writeIndex = writeIndex - count;
    }

    /**
     * 读取全部的剩余的数据
     *
     * @return 读取未读数据
     */
    public byte[] readLast() {
        return Arrays.copyOfRange(bytes, readIndex, writeIndex);
    }

    /**
     * 从第start位读到第当前位偏移offset位（配合mark使用）
     *
     * @param start  从mark起，第start位，如果未进行过mark，则从0计算
     * @param offset 当前读取位readIndex的偏移位
     */
    public byte[] reread(int start, int offset) {
        return Arrays.copyOfRange(bytes, tagIndex + start, readIndex + offset);
    }
}
