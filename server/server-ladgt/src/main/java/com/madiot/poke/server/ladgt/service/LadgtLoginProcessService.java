package com.madiot.poke.server.ladgt.service;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.notices.upstream.Login;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.dubbo.api.server.ILoginProcessService;
import com.madiot.poker.common.domain.IPlayer;
import com.madiot.poke.dubbo.api.server.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by julian on 2017/8/30.
 */
@Service
public class LadgtLoginProcessService implements ILoginProcessService {

    @Autowired
    private IPokeRedisService redisService;

    @Autowired
    private IUserService userService;

    public byte[] receiptMessage(byte[] message, String serverIp) {
        NoticeMessage noticeMessage = new NoticeMessage(message);
        Login login = (Login) noticeMessage.getData();
        String username = login.getUsername().getString();
        String password = login.getPassword().getString();
        IPlayer player = userService.login(username, password);
        if (player != null) {
            ConnectInfoDO connectInfoDO = redisService.getConnectInfo(player.getId());
            if (connectInfoDO != null) {
                connectInfoDO.setServerIp(serverIp);
            } else {
                connectInfoDO = new ConnectInfoDO(player, serverIp);
            }
            redisService.saveConnectInfo(connectInfoDO);
            noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.SUCCESS);
        } else {
            noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.REFUSE);
        }
        noticeMessage.getNoticeHead().setTimestamp(new Date());
        ByteBuffer buffer = new ByteBuffer();
        noticeMessage.encode(buffer);
        return buffer.getBytes();
    }
}
