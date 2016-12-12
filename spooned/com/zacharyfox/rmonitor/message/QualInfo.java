

package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;
import org.jetbrains.annotations.NotNull;

public class QualInfo extends RMonitorMessage {
  private int bestLap;

  private Duration bestLapTime;

  private int position;

  private String regNumber;

  public QualInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    position = Integer.parseInt(tokens[1]);
    regNumber = tokens[2];
    bestLap = (tokens[3].equals("")) ? 0 : Integer.parseInt(tokens[3]);
    bestLapTime = new Duration(tokens[4]);
  }

  public int getBestLap() {
    return bestLap;
  }

  public Duration getBestLapTime() {
    return bestLapTime;
  }

  public int getPosition() {
    return position;
  }

  @Override
  public String getRegNumber() {
    return regNumber;
  }
}
