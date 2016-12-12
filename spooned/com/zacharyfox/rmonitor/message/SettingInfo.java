

package com.zacharyfox.rmonitor.message;

import org.jetbrains.annotations.NotNull;

public class SettingInfo extends RMonitorMessage {
  private String description;

  private String value;

  public SettingInfo(@NotNull String[] tokens) {
    if (tokens == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    description = tokens[1];
    value = tokens[2];
  }

  public String getDescription() {
    return description;
  }

  public String getValue() {
    return value;
  }
}
