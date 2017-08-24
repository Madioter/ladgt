package com.madiot.common.utils.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * byte工具类 Created by Yi.Wang2 on 2016/8/19.
 */
public class ByteUtils {

    /**
     * 16进制数据按位比
     */
    private static long[] POSITION = new long[]{0xFFl, 0xFF00l, 0xFF0000l, 0xFF000000l, 0xFF00000000l, 0xFF0000000000l, 0xFF000000000000l, 0xFF00000000000000l};

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     *          原byte数据
     * @return 转换为十六进制字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 解析十六进制字符串为byte数组
     *
     * @param nm
     *          字符串
     * @return 比特数组
     */
    public static byte[] decodeHex(String nm) {
        int len = nm.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i++) {
            char c = nm.charAt(i);
            byte b = Byte.decode("0x" + c);
            c = nm.charAt(++i);
            result[i / 2] = (byte) (b << 4 | Byte.decode("0x" + c));
        }
        return result;
    }

    /**
     * 从inputStream中读取比特数组
     *
     * @param in
     *          inputStream
     * @return 比特数组
     * @throws IOException
     *           IO异常
     */
    public static byte[] readBytes(InputStream in) throws IOException {
        byte[] temp = new byte[in.available()];
        int size = 0;
        if ((size = in.read(temp)) != -1) {
            byte[] readBytes = new byte[size];
            System.arraycopy(temp, 0, readBytes, 0, size);
        }
        return temp;
    }

    /**
     * 合并两个比特数组
     *
     * @param original
     *          比特数组1
     * @param readBytes
     *          比特数组2
     * @return 合并后的比特数组
     */
    public static byte[] mergeArray(byte[] original, byte[] readBytes) {
        if (original == null && readBytes == null) {
            return new byte[0];
        } else if (original == null) {
            return readBytes;
        } else if (readBytes == null) {
            return original;
        }
        byte[] result = new byte[original.length + readBytes.length];
        for (int i = 0; i < original.length; i++) {
            result[i] = original[i];
        }
        for (int i = 0; i < readBytes.length; i++) {
            result[i + original.length] = readBytes[i];
        }
        return result;
    }

    /**
     * byte数组转换为int类型，长度不能超过4位 高位在前，地位在后
     *
     * @param bytes
     *          byte数组
     * @return int 16位整型
     */
    public static int bytesToInt(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        int size = bytes.length;
        if (size > 4) {
            throw new RuntimeException("超过4位byte数据无法被转换为int类型，请考虑转换为int数组");
        }
        int value = 0;
        for (int i = 0; i < size; i++) {
            value = (int) ((bytes[size - i - 1] << i * 8) & POSITION[i] | value);
        }
        return value;
    }

    /**
     * 数值转换为比特数祖
     *
     * @param data
     *          int数据
     * @return 比特数组
     */
    public static byte[] intToBytes(int data) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (data >> (24 - i * 8));
        }
        return b;
    }

    /**
     * int数据转换为比特数组
     *
     * @param data
     *          int数据
     * @param length
     *          数据长度（每几位编码为一个比特数）
     * @return 比特数组
     */
    public static byte[] intToBytes(int data, int length) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (data >> ((length - 1 - i) * 8));
        }
        return b;
    }

    /**
     * long数据转换为比特数组
     *
     * @param data
     *          long数据
     * @param length
     *          数据长度（每几位编码为一个比特数）
     * @return 比特数组
     */
    public static byte[] longToBytes(long data, int length) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (data >> ((length - 1 - i) * 8));
        }
        return b;
    }

    /**
     * byte数组转换为int类型数组，按指定的长度截取
     *
     * @param bytes
     *          byte数组
     * @param size
     *          长度（几位byte数据编码为一个数值）
     * @return int数组
     */
    public static int[] bytesToInts(byte[] bytes, int size) {
        if (bytes == null) {
            return null;
        }
        if (size > 4) {
            throw new RuntimeException("超过4位byte数据无法被转换为int数组，请考虑转换为long数组");
        }
        int[] result = new int[(int) Math.ceil(bytes.length / size)];
        for (int i = 0; i < result.length; i++) {
            byte[] temp;
            if (i < result.length - 1) {
                temp = Arrays.copyOfRange(bytes, i * size, (i + 1) * size);
            } else {
                temp = Arrays.copyOfRange(bytes, i * size, bytes.length);
            }
            result[i] = bytesToInt(temp);
        }
        return result;
    }

    /**
     * byte数组转换为long类型数组，长度不能超过4位 高位在前，地位在后
     *
     * @param bytes
     *          byte数组
     * @return long 32位长整型
     */
    public static long bytesToLong(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        int size = bytes.length;
        if (size > 8) {
            throw new RuntimeException("超过8位byte数据无法被转换为long类型，请考虑转换为long数组");
        }
        long value = 0;
        for (int i = 0; i < size; i++) {
            value = (((long) bytes[size - i - 1] << i * 8) & POSITION[i] | value);
        }
        return value;
    }

    /**
     * byte数组转换为int类型数组，按指定的长度截取
     *
     * @param bytes
     *          byte数组
     * @param size
     *          长度
     * @return int数组
     */
    public static long[] bytesToLongs(byte[] bytes, int size) {
        if (bytes == null) {
            return null;
        }
        if (size > 8) {
            throw new RuntimeException("超过8位byte数据无法被转换为long数组，请修改数组切分长度");
        }
        long[] result = new long[(int) Math.ceil(bytes.length / size)];
        for (int i = 0; i < result.length; i++) {
            byte[] temp;
            if (i < result.length - 1) {
                temp = Arrays.copyOfRange(bytes, i * size, (i + 1) * size);
            } else {
                temp = Arrays.copyOfRange(bytes, i * size, bytes.length);
            }
            result[i] = bytesToLong(temp);
        }
        return result;
    }

    /**
     * 给byte数组赋值，一次性赋多个值
     *
     * @param original
     *          原始的数据
     * @param from
     *          起始位置
     * @param data
     *          需要赋值的数据
     * @return 赋值后的byte数组
     */
    public static byte[] setBytes(byte[] original, int from, byte[] data) {
        if (data == null) {
            return original;
        }
        if (original == null) {
            return new byte[0];
        }
        if (original.length < from + data.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("original length：%s is not enough space to fill data length：%s，" + "from: %s is start index", original.length, data.length, from));
        }
        for (int i = 0; i < data.length; i++) {
            original[i + from] = data[i];
        }
        return original;
    }

    /**
     * 将byte数组格式化成指定的长度
     * @param bytes 比特数组
     * @param size 固定长度
     * @return 固定长度的byte数组
     */
    public static byte[] strictSize(byte[] bytes, int size) {
        if (bytes.length == size) {
            return bytes;
        } else {
            byte[] result = new byte[size];
            int index = 0;
            while (index < size) {
                if (index < bytes.length) {
                    result[index] = bytes[index];
                } else {
                    result[index] = 0;
                }
                index++;
            }
            return result;
        }
    }
}
