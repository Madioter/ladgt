/**
 * @Title: IMessageSendService.java
 * @Package com.madiot.poke.dubbo.api.connect
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.dubbo.api.connect;

import com.madioter.poker.common.exception.ConnectionException;

/**
 * @ClassName: IMessageSendService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public interface IMessageSendService {

    void sendMessage(byte[] data, String serverIp, Integer playerId) throws ConnectionException;
}
