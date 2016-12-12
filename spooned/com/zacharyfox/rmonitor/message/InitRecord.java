

package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;
import org.jetbrains.annotations.NotNull;

public class InitRecord extends RMonitorMessage {
  private Duration timeOfDay;

  public InitRecord(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    timeOfDay = new Duration(tokens[1]);
  }

  public Duration getTimeOfDay() {
    return timeOfDay;
  }
}
