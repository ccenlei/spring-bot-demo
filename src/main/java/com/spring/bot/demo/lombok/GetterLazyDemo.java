package com.spring.bot.demo.lombok;

import lombok.Getter;

/**
 * like this
 * 
 * private final AtomicReference<java.lang.Object> cached = new AtomicReference<>();
 *   public double[] getCached() {
 *      java.lang.Object value = this.cached.get();
 *      if (value == null) {
 *          synchronized (this.cached) {
 *              value = this.cached.get();
 *              if (value == null) {
 *                  final double[] actualValue = expensive();
 *                  value = actualValue == null ? this.cached : actualValue;
 *                  this.cached.set(value);
 *              }
 *          }
 *      }
 *      return (double[]) (value == this.cached ? null : value);
 *  }
  
 *  private double[] expensive() {
 *      double[] result = new double[1000000];
 *      for (int i = 0; i < result.length; i++) {
 *          result[i] = Math.asin(i);
 *      }
 *      return result;
 *  }
 */
public class GetterLazyDemo {

    @Getter(lazy = true)
    private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[100];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }
}
