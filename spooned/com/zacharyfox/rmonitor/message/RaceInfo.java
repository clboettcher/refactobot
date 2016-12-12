

package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;
import org.jetbrains.annotations.NotNull;

public class RaceInfo extends RMonitorMessage {
  private int laps;

  private int position;

  private String regNumber;

  private Duration totalTime;

  public RaceInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    position = Integer.parseInt(tokens[1]);
    regNumber = tokens[2];
    laps = (tokens[3].equals("")) ? 0 : Integer.parseInt(tokens[3]);
    totalTime = new Duration(tokens[4]);
  }

  public int getLaps() {
    return laps;
  }

  public int getPosition() {
    return position;
  }

  @Override
  public String getRegNumber() {
    return regNumber;
  }

  public Duration getTotalTime() {
    return totalTime;
  }
}
