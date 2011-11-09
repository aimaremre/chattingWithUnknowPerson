package com.aimar.test.netty.webchat;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * 服务器端session
 * 
 * @author xiaodonglang
 */
public class SessionHolder {

    private static Map<Integer, Session> sessionHolder = new ConcurrentHashMap<Integer, Session>();

    public static void setSession(Integer sessionId, Session session) {
        sessionHolder.put(sessionId, session);
    }

    public static Session getSession(Integer sessionId) {
        return sessionHolder.get(sessionId);
    }

    public static int getSessionCount() {
        return sessionHolder.size();
    }

}
