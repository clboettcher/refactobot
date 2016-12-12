

package com.zacharyfox.rmonitor.message;

import org.jetbrains.annotations.NotNull;

public class ClassInfo extends RMonitorMessage {
  private String description;

  private int unique;

  public ClassInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    unique = Integer.parseInt(tokens[1]);
    description = tokens[2];
  }

  public String getDescription() {
    return description;
  }

  public int getUniqueId() {
    return unique;
  }
}
