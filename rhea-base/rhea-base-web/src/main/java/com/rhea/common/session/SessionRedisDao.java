package com.rhea.common.session;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class SessionRedisDao extends EnterpriseCacheSessionDAO {
    private static final int EXPIRE_TIME = 1800;
    @Value("${shiro.session.prefix}")
    private String prefix;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        log.debug("create session:{}", session.getId());
        redisTemplate.opsForValue().set(buildKey(sessionId), session);
        return sessionId;
    }

    @Override protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        log.debug("read session:{}", session.getId());
        if (null == session) {
            session = (Session) redisTemplate.opsForValue().get(buildKey(sessionId));
        }
        return session;
    }

    @Override protected void doUpdate(Session session) {
        super.doUpdate(session);
        log.debug("update session:{}", session.getId());
        String sessionKey = buildKey(session.getId());
        if (redisTemplate.hasKey(sessionKey)) {
            redisTemplate.opsForValue().set(sessionKey, session);
        }
        redisTemplate.expire(sessionKey, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    @Override protected void doDelete(Session session) {
        super.doDelete(session);
        log.debug("update session:{}", session.getId());
        redisTemplate.delete(buildKey(session.getId()));
    }

    private String buildKey(Serializable sessionId) {
        return prefix + sessionId.toString();
    }
}