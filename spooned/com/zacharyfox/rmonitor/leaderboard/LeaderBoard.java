

package com.zacharyfox.rmonitor.leaderboard;

import com.zacharyfox.rmonitor.leaderboard.frames.ConnectFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.MainFrame;
import java.awt.EventQueue;
import javax.swing.UIManager;
import org.jetbrains.annotations.NotNull;

public class LeaderBoard {
  /** Launch the application. */
  public static void main(@NotNull String[] args) {
    if (args == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    try {
      System.setProperty("apple.awt.fullscreenhidecursor", "true");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "RMonitorLeaderboard");
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              MainFrame window = new MainFrame();
              window.setVisible(true);
              ConnectFrame newFrame = ConnectFrame.getInstance(window);
              newFrame.setVisible(true);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }
}
