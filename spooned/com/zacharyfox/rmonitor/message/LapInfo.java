

package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;
import org.jetbrains.annotations.NotNull;

public class LapInfo extends RMonitorMessage {
  private int position;

  private String regNumber;

  private int lapNumber;

  private Duration lapTime;

  public LapInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    position = Integer.parseInt(tokens[1]);
    regNumber = tokens[2];
    lapNumber = (tokens[3].equals("")) ? 0 : Integer.parseInt(tokens[3]);
    lapTime = new Duration(tokens[4]);
  }

  public int getPosition() {
    return position;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public int getLapNumber() {
    return lapNumber;
  }

  public Duration getLapTime() {
    return lapTime;
  }
}
