package com.madiot.poke.dubbo.api.server;

/**
 * Created by julian on 2017/8/30.
 */
public interface ILoginProcessService {

    byte[] receiptMessage(byte[] message, String serverIp);
}
