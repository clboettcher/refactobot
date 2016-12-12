

package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;
import org.jetbrains.annotations.NotNull;

public class PassingInfo extends RMonitorMessage {
  private final Duration lapTime;

  private final String regNumber;

  private final Duration totalTime;

  public PassingInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    regNumber = tokens[1];
    lapTime = new Duration(tokens[2]);
    totalTime = new Duration(tokens[3]);
  }

  public Duration getLapTime() {
    return lapTime;
  }

  @Override
  public String getRegNumber() {
    return regNumber;
  }

  public Duration getTotalTime() {
    return totalTime;
  }
}
