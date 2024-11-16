package com.ningf.ningfojcodesandbox.unsafe;

/**
 * @description: 程序运行阻塞（时间占用过多）
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class SleepError {
    public static void main(String[] args) throws InterruptedException {
        long ONE_HOUR = 60*60*1000;
        Thread.sleep(ONE_HOUR);
        System.out.println("睡醒了");
    }
}
