/**
 * @Title: EffectiveRange.java
 * @Package com.igdata.gbparser.common.validate
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/4/26
 * @version
 */
package com.madioter.poker.netty.message.common.validate;

import com.madioter.poker.netty.message.type.ByteArrayType;
import com.madioter.poker.netty.message.type.impl.ByteType;
import com.madioter.poker.netty.message.type.impl.DWordType;
import com.madioter.poker.netty.message.type.impl.WordType;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Yi.Wang2
 * @ClassName: NumberValid
 * @Description: 有效值范围
 * @date 2017/4/26
 */
public class NumberValid implements IValid {

    /**
     * 本地缓存
     */
    private static List<SoftReference<NumberValid>> references = new CopyOnWriteArrayList<>();

    /**
     * 最大值
     */
    private final Integer maxValue;

    /**
     * 最小值
     */
    private final Integer minValue;

    /**
     * 异常值
     */
    private final Integer errorValue;

    /**
     * 无效值
     */
    private final Integer invalidValue;

    private NumberValid(Integer minValue, Integer maxValue) {
        this(minValue, maxValue, null, null);
    }

    private NumberValid(Integer minValue, Integer maxValue, Integer errorValue) {
        this(minValue, maxValue, errorValue, null);
    }

    private NumberValid(Integer minValue, Integer maxValue, Integer errorValue, Integer invalidValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.errorValue = errorValue;
        this.invalidValue = invalidValue;
    }

    public boolean isValid(ByteArrayType byteArrayType) throws InvalidException {
        Long value;
        if (byteArrayType instanceof ByteType) {
            value = ((ByteType) byteArrayType).getInt().longValue();
        } else if (byteArrayType instanceof WordType) {
            value = ((WordType) byteArrayType).getInt().longValue();
        } else if (byteArrayType instanceof DWordType) {
            value = ((DWordType) byteArrayType).getLong();
        } else {
            return false;
        }
        if ((errorValue != null && value.equals(errorValue)) ||
                (invalidValue != null && value.equals(invalidValue))) {
            return false;
        } else if (value < minValue || value > maxValue) {
            throw new InvalidException("数据值超出有效值范围");
        }
        return true;
    }

    public boolean equals(Integer minValue, Integer maxValue, Integer errorValue, Integer invalidValue) {
        return minValue.equals(this.minValue)
                && maxValue.equals(this.maxValue)
                && ((errorValue == null && this.errorValue == null) || errorValue.equals(this.errorValue))
                && ((errorValue == null && this.errorValue == null) || invalidValue.equals(this.invalidValue));
    }

    public static IValid newInstance(Integer minValue, Integer maxValue, Integer errorValue, Integer invalidValue) {
        Iterator<SoftReference<NumberValid>> iterator = references.iterator();
        while (iterator.hasNext()) {
            SoftReference<NumberValid> item = iterator.next();
            NumberValid valid = item.get();
            if (valid == null) {
                iterator.remove();
            } else if (valid.equals(minValue, maxValue, errorValue, invalidValue)) {
                return valid;
            }
        }
        NumberValid valid = new NumberValid(minValue, maxValue, errorValue, invalidValue);
        references.add(new SoftReference<NumberValid>(valid));
        return valid;
    }

    public static IValid newInstance(Integer minValue, Integer maxValue) {
        return newInstance(minValue, maxValue, null, null);
    }

    public static IValid newInstance(Integer minValue, Integer maxValue, Integer errorValue) {
        return newInstance(minValue, maxValue, errorValue, null);
    }
}
