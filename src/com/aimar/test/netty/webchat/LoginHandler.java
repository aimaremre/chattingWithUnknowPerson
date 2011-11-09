package com.aimar.test.netty.webchat;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class LoginHandler extends SimpleChannelUpstreamHandler {

    /**
     * 连接时初始化session对象
     */
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Channel self = ctx.getChannel();
        Session session = SessionHolder.getSession(self.getId());
        if (session == null) {
            session = new Session();
            SessionHolder.setSession(self.getId(), session);
        }
        User user = new User();
        user.setChannel(self);
        session.setAttribute("self", user);
        System.out.println("session init!");
        ctx.sendUpstream(e);
    }

    /**
     * 登录时初始化用户信息
     */
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        // Channel self = ctx.getChannel();
        // Session session = SessionHolder.getSession(self.getId());
        // if (session == null) {
        // self.write("session expired!");
        // return;
        // }
        // User user = new User();
        // user.setChannel(self);
        // session.setAttribute("self", user);
        // ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        // String msg = (String) e.getMessage();
        // if (msg.startsWith("login")) {
        // User user = new User();
        // user.setChannel(self);
        // session.setAttribute("self", user);
        // } else {
        // User user = (User) session.getAttribute("self");
        // if (user == null) {
        // self.write("请先登录!");
        // return;
        // }
        // }

        ctx.sendUpstream(e);
    }
}
