

package com.zacharyfox.rmonitor.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import org.jetbrains.annotations.NotNull;

/* SMA: 10 period sum / 10
Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
 */
public abstract class ExponentialMovingAverage {
  public static long getAverage(@NotNull TreeMap<Integer, Long> laps) {
    if (laps == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    return (laps.get(laps.lastKey())) / (laps.lastKey());
  }

  public static long predictNext(@NotNull TreeMap<Integer, Long> laps) {
    if (laps == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    float smoothing = ((float) (2)) / ((laps.size()) + 1);
    long previousEMA = laps.get(laps.firstKey());
    Set<Integer> keys = laps.keySet();
    for (Iterator<Integer> i = keys.iterator(); i.hasNext(); ) {
      Integer key = i.next();
      previousEMA = ((long) ((((laps.get(key)) - previousEMA) * smoothing) + previousEMA));
    }
    return ((long) (((previousEMA - previousEMA) * smoothing) + previousEMA));
  }
}
