

package com.zacharyfox.rmonitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;

/** @author Zachary Fox <zachreligious@gmail.com> */
public class Connection extends Socket {
  private BufferedReader clientReader;

  public Connection(@NotNull String ip, int port) throws Exception {
    super(ip, port);
    if (ip == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    clientReader = new BufferedReader(new InputStreamReader(Connection.this.getInputStream()));
  }

  @Override
  public void close() throws IOException {
    clientReader.close();
    super.close();
  }

  public String readLine() throws IOException {
    String line = clientReader.readLine();
    return line;
  }
}
