/**
 * @Title: IProcessService.java
 * @Package com.madiot.poke.dubbo.api.server
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.dubbo.api.server;

/**
 * @ClassName: IProcessService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public interface IProcessService {

    byte[] receiptMessage(byte[] message);
}
