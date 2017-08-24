/**
 * @Title: IPlayerStatusChange.java
 * @Package com.madiot.poke.dubbo.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.dubbo.api.server;

/**
 * @ClassName: IPlayerStatusChange
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public interface IPlayerStatusChange {

    void offLine(Integer id, String remoteIp);

}
