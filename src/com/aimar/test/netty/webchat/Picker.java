package com.aimar.test.netty.webchat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * pick线程，负责自动匹配好友
 * 
 * @author xiaodonglang
 */
public class Picker implements Runnable {

    @Override
    public void run() {

        while (true) {
            List<User> waitUsers = ChatHandler.waitUsers;
            for (int i = 0; i < waitUsers.size(); i++) {
                User before = waitUsers.get(i);
                if (before.getIsChatting().get()) {
                    continue;
                }
                for (int j = i + 1; j < waitUsers.size(); j++) {
                    if (before != waitUsers.get(j) & !before.getIsChatting().get()) {
                        match(before, waitUsers.get(j));
                        break;
                    }
                }
            }
            List<User> copy = Collections.synchronizedList(new ArrayList<User>());
            for (User user : waitUsers) {
                if (!user.getIsChatting().get()) {
                    copy.add(user);
                    user.getChannel().write(">>>>>>>>>找人中>>>>>>>>>>");
                }
            }
            ChatHandler.waitUsers = copy;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

    }

    /**
     * match处理
     * 
     * @param you
     * @param me
     */
    private void match(User you, User me) {
        me.setPickStatus(PickStatus.PICKED);
        you.setPickStatus(PickStatus.PICKED);
        me.setIsChatting(new AtomicBoolean(true));
        you.setIsChatting(new AtomicBoolean(true));
        me.setFriend(you);
        you.setFriend(me);
        me.getChannel().write("匹配成功，开始聊天");
        you.getChannel().write("匹配成功，开始聊天");

        syncSession(you);
        syncSession(me);
    }

    private void syncSession(User user) {
        Session session = SessionHolder.getSession(user.getChannel().getId());
        session.setAttribute("self", user);
        SessionHolder.setSession(user.getChannel().getId(), session);
    }

}
