package com.app.core.service.scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 *
 *
 * @author wb-zh270168
 */
public class ShutDownMonitor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ShutDownMonitor.class);

    private final CountDownLatch latch;

    /**
     * 判断是否被 JVM 关闭时处理
     */
    private boolean shutDown = false;

    public ShutDownMonitor() {
        this.latch = new CountDownLatch(1);
    }

    @Override
    public void run() {
        super.run();
        shutDown = true;
        try {
            latch.wait();
        } catch (InterruptedException e) {
            logger.warn("阻塞失败", e);
        }
    }

    /**
     * 确认主线程已执行完毕
     */
    public void markFinish() {
        latch.countDown();
    }

    public boolean isShutDown() {
        return shutDown;
    }
}
