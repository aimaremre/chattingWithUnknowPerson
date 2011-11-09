package com.aimar.test.netty.webchat;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class UnPickHandler extends SimpleChannelUpstreamHandler {

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel self = ctx.getChannel();
        Session session = SessionHolder.getSession(self.getId());
        if (session == null) {
            self.write("session expired!");
            return;
        }
        String msg = (String) e.getMessage();
        User user = (User) session.getAttribute("self");
        if (msg.startsWith("unpick")) {
            if (user.getIsChatting().get()) {
                User friend = user.getFriend();

                user.setFriend(null);
                user.setIsChatting(new AtomicBoolean(false));
                user.setPickStatus(PickStatus.NOPICK);

                friend.setFriend(null);
                friend.setIsChatting(new AtomicBoolean(false));
                friend.setPickStatus(PickStatus.NOPICK);

                friend.getChannel().write("你的朋友离开了，请重新找人聊天!");
            } else if (user.getPickStatus() == PickStatus.PICKING) {
                ChatHandler.waitUsers.remove(user);
            } else if (user.getPickStatus() == PickStatus.PICKED) {
                user.setPickStatus(PickStatus.NOPICK);
            }
            self.write("退出成功！你可以重新找人！");
            return;
        }
        ctx.sendUpstream(e);
    }
}
