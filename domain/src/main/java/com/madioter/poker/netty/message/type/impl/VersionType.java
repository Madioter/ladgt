package com.madioter.poker.netty.message.type.impl;

import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.common.utils.number.NumberUtil;

/**
 * Created by DELL on 2017/5/10.
 */
public class VersionType extends DWordType {

    private enum VersionTypeEnum {
        TEST, OFFICIAL;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        byte[] bytes = getBytes();
        if (bytes[0] == VersionTypeEnum.TEST.ordinal()) {
            builder.append("(测试版)");
        }
        for (int i = 1; i < getSize(); i++) {
            if (i < getSize() - 1) {
                builder.append(bytes[i] + ".");
            } else {
                builder.append(bytes[i]);
            }
        }
        return builder.toString();
    }

    public void decode(String version) {
        byte[] bytes = new byte[getSize()];
        if (version.startsWith("(测试版)")) {
            bytes[0] = (byte) VersionTypeEnum.TEST.ordinal();
            version.replace("(测试版)", "");
        }
        String[] points = version.split(",");
        for (int i = 0; i < points.length; i++) {
            if (i < getSize()) {
                bytes[i + 1] = (byte) NumberUtil.parseInt(points[i]);
            }
        }
        this.setInt(ByteUtils.bytesToInt(bytes));
    }
}
