/**
 * @Title: TestDomain.java
 * @Package com.madiot.test
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.test;

import com.madiot.common.utils.collection.CollectionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestDomain
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class TestDomain {

    private static Map<String, Class> dataName = new HashMap<>();

    public Map<String, Class> getData() {
        return dataName;
    }

    public void setData(Map<String, Class> data) {
        if (!CollectionUtil.isEmpty(data)) {
            for (Map.Entry<String, Class> entry : data.entrySet()) {
                dataName.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
