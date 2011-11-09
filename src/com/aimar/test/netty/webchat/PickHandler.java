package com.aimar.test.netty.webchat;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class PickHandler extends SimpleChannelUpstreamHandler {

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel self = ctx.getChannel();
        Session session = SessionHolder.getSession(self.getId());
        if (session == null) {
            self.write("session expired!");
            return;
        }
        // ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        String msg = (String) e.getMessage();
        User user = (User) session.getAttribute("self");
        if (msg.startsWith("pick")) {
            ChatHandler.waitUsers.add(user);
            user.setPickStatus(PickStatus.PICKING);
            self.write("开始找人!");
            return;
        } else if (user.getPickStatus() == PickStatus.NOPICK) {
            self.write("请先找人，再聊天!");
            return;
        } else if (user.getPickStatus() == PickStatus.PICKING) {
            self.write("系统正在匹配，请稍候!");
            return;
        }
        ctx.sendUpstream(e);
    }

}
