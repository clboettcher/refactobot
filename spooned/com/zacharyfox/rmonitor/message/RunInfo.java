

package com.zacharyfox.rmonitor.message;

import org.jetbrains.annotations.NotNull;

public class RunInfo extends RMonitorMessage {
  private String raceName;

  private int unique;

  public RunInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    unique = Integer.parseInt(tokens[1]);
    raceName = tokens[2];
  }

  public String getRaceName() {
    return raceName;
  }

  public int getUniqueId() {
    return unique;
  }
}
