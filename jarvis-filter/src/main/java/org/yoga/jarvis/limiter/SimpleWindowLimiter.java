/*
 *  Copyright 2022 yoga
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.yoga.jarvis.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoga.jarvis.util.Assert;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @Description: SimpleWindow Limiter
 * @Author: yoga
 * @Date: 2022/6/2 13:24
 */
public class SimpleWindowLimiter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleWindowLimiter.class);

    /**
     * threshold
     */
    private final int threshold;

    /**
     * time windows（ms）
     */
    private final long timeWindows;

    /**
     * record queue
     */
    private final Queue<Long> queue;

    public SimpleWindowLimiter(int threshold, long timeWindows, Queue<Long> queue) {
        this.threshold = threshold;
        this.timeWindows = timeWindows;
        this.queue = queue;
    }

    public synchronized boolean tryAcquire(long timeout, TimeUnit unit) {
        Assert.isTrue(timeout > 0, "timeout val is illegal");
        if (queue.size() >= threshold) {
            return false;
        }
        return queue.offer(System.currentTimeMillis() + unit.toMillis(timeout));
    }
}
