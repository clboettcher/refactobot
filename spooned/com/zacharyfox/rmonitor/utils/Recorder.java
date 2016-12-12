

package com.zacharyfox.rmonitor.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.jetbrains.annotations.NotNull;

public class Recorder {
  private BufferedWriter bufferedWriter;

  private FileWriter fileWriter;

  private long startTime = 0;

  public Recorder(@NotNull String fileName) {
    if (fileName == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    try {
      fileWriter = new FileWriter(fileName);
      bufferedWriter = new BufferedWriter(fileWriter);
    } catch (IOException e) {
    }
  }

  public void close() {
    try {
      bufferedWriter.close();
      fileWriter.close();
    } catch (IOException e) {
    }
  }

  public void push(@NotNull String message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if ((startTime) == 0) {
      startTime = new Date().getTime();
    }
    long timeStamp = (new Date().getTime()) - (startTime);
    try {
      bufferedWriter.write((((timeStamp + " ") + message) + "\n"));
    } catch (IOException e) {
    }
  }
}
