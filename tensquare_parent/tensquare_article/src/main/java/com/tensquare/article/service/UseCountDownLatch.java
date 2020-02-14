package com.tensquare.article.service;

import java.util.concurrent.CountDownLatch;

/**
 * @author haixin
 * @time 2020/2/12
 */
public class UseCountDownLatch {
    static CountDownLatch latch = new CountDownLatch(5);

    /*初始化线程*/
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()+"ready init work");
            latch.countDown();
            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_"+Thread.currentThread().getId()+"do its work");
            }
        }
    }

    /*等待初始化完成的线程*/
    private static class BusiThread implements Runnable{

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 3; i++) {
                System.out.println("BusiThread"+Thread.currentThread().getId()+" do business");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getId()+"main first init");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getId()+"main second init");
            }
        }).start();

        new Thread(new BusiThread()).start();//开启业务线程
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        System.out.println("主线程结束");
    }
}
