/**
 * @Title: IServer.java
 * @Package com.madiot.poke.server.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.server.api;

/**
 * @ClassName: IServer
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPokeMessageServer {

    void sendMessage(ICallbackService callbackService);
}
