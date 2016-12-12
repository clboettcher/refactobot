

package com.zacharyfox.rmonitor.entities;

import com.zacharyfox.rmonitor.message.ClassInfo;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;

public class RaceClass {
  private static HashMap<Integer, String> instances = new HashMap<Integer, String>();

  private RaceClass() {}

  public static String getClassName(@NotNull Integer classId) {
    if (classId == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String className = RaceClass.instances.get(classId);
    return className == null ? "" : className;
  }

  public static void update(@NotNull ClassInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String className = RaceClass.instances.get(message.getUniqueId());
    if ((className == null) || (!(className.equals(message.getDescription())))) {
      RaceClass.instances.put(message.getUniqueId(), message.getDescription());
    }
  }
}
