package com.madioter.poker.connect.process;

import com.madioter.poker.common.exception.ConnectionException;

/**
 * 流程接口 Created by Yi.Wang2 on 2016/8/24.
 */
public interface IProcess {

    /**
     * 中转接口
     *
     * @return 响应
     */
    public byte[] doProcess() throws ConnectionException;
}
