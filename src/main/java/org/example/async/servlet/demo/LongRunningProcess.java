package org.example.async.servlet.demo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2020/7/29
 */
public class LongRunningProcess {

    public int doSomething() {
        try {
            int millis = ThreadLocalRandom.current().nextInt(2000);
            String currentThread = Thread.currentThread().getName();
            System.out.println(currentThread + " sleep for " + millis + " milliseconds.");
            Thread.sleep(millis);
            return millis;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
