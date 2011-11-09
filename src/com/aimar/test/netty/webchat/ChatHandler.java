package com.aimar.test.netty.webchat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ChatHandler extends SimpleChannelHandler {

    public static List<User> waitUsers = Collections.synchronizedList(new ArrayList<User>());

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String msg = (String) e.getMessage();
        System.out.println(msg);
        Channel self = ctx.getChannel();
        Session session = SessionHolder.getSession(self.getId());
        User user = (User) session.getAttribute("self");

        User friend = user.getFriend();

        if (friend == null) {
            user.getChannel().write("请先找人!");
            return;
        }
        // 给朋友发送信息
        self.write("自己:" + msg);
        send(friend.getChannel(), msg);
    }

    private void send(Channel channel, String msg) {
        channel.write("朋友:" + msg);
    }

}
