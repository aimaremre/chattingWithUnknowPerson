package com.aimar.test.netty.webchat;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * session实体
 * 
 * @author xiaodonglang
 */
public class Session {

    private Map<Object, Object> session = new ConcurrentHashMap<Object, Object>();

    public void setAttribute(Object key, Object value) {
        session.put(key, value);
    }

    public Object getAttribute(Object key) {
        return session.get(key);
    }

    public void removeAttribute(Object key) {
        session.remove(key);
    }

}
