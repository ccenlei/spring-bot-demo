package com.spring.bot.demo.lombok;

import lombok.Synchronized;

/**
 * like this:
 * 
 * private static final Object $LOCK = new Object[0];
 * 
 * public static void hello() {
 * synchronized ($LOCK) {
 * System.out.println("world");
 * }
 * }
 */
public class SynchronizedDemo {

    @Synchronized
    public static void hello() {
        System.out.println("synchronized demo.");
    }
}