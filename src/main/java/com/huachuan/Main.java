package com.huachuan;

import java.util.TreeMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    volatile static int cur = 1;
    Lock lock = new ReentrantLock();
    public static void main(String[] args) {
       Thread t1 = new Thread(new Runnable() {
           @Override
           public void run() {
               int temp = 1;
               synchronized (Main.class) {
                    while (true){
                       while (cur != temp) {
                           try {
                               Main.class.wait();
                           } catch (InterruptedException e) {
                               throw new RuntimeException(e);
                           }
                       }
                        System.out.println(cur);
                        ++cur;
                        temp += 3;
                        Main.class.notifyAll();
                   }
               }

           }
       });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int temp = 2;
                synchronized (Main.class) {
                    while (true){
                        while (cur != temp) {
                            try {
                                Main.class.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(cur);
                        ++cur;
                        temp += 3;
                        Main.class.notifyAll();
                    }
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int temp = 3;
                synchronized (Main.class) {
                    while (true){
                        while (cur != temp) {
                            try {
                                Main.class.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(cur);
                        ++cur;
                        temp += 3;
                        Main.class.notifyAll();
                    }
                }

            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
