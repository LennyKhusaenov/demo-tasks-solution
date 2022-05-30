package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserStats {

    private static class HitsPerUserHolder {
        private static final Map<String, AtomicLong> hitsPerUser = new ConcurrentHashMap<>();
    }

    public void onUserCall(String userId) {
        HitsPerUserHolder.hitsPerUser.computeIfAbsent(userId, user -> new AtomicLong())
                .incrementAndGet();
    }
}
