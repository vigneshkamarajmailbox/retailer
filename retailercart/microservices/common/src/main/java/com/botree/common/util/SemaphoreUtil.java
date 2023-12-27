package com.botree.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Class contains the semaphore related activities.
 * @author vinodkumar.a
 */

public final class SemaphoreUtil {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SemaphoreUtil.class);
    /** semaphoreMap. */
    private static Map<String, Semaphore> semaphoreMap = new HashMap<>();
    /** i. */
    private static int i = 0;

    /**
     * Default constructor.
     */
    private SemaphoreUtil() {

    }

    /**
     * Method to acquire the semaphore lock.
     * @param key key
     * @throws InterruptedException exception
     */
    public static void acquire(final String key) throws InterruptedException {
        var semaphore = semaphoreMap.get(key);
        if (semaphore != null) {
            semaphore.acquire();
            i++;
            LOG.info("acquire {}  {}", key, i);
        } else {
            LOG.info("semaphore map empty for key :: {}", key);
        }
    }

    /**
     * Method to release the semaphore lock.
     * @param key key
     */
    public static void release(final String key) {
        var semaphore = semaphoreMap.get(key);
        if (semaphore != null) {
            LOG.info("release {} {} ", key, i);
            semaphore.release();
        }
    }

    /**
     * Method to add the key.
     * @param key key
     */
    public static void add(final String key) {
        semaphoreMap.put(key, new Semaphore(1));
    }

}
