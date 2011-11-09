package com.aimar.test.netty.webchat;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.channel.Channel;

public class User {

    private Channel       channel;

    private AtomicBoolean isChatting = new AtomicBoolean(false);

    private User          friend;

    private PickStatus    pickStatus = PickStatus.NOPICK;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void speak(User friend, String msg) {
        Channel channel = friend.getChannel();
        channel.write(msg);
    }

    public void setIsChatting(AtomicBoolean isChatting) {
        this.isChatting = isChatting;
    }

    public AtomicBoolean getIsChatting() {
        return isChatting;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getFriend() {
        return friend;
    }

    public void setPickStatus(PickStatus pickStatus) {
        this.pickStatus = pickStatus;
    }

    public PickStatus getPickStatus() {
        return pickStatus;
    }

}
