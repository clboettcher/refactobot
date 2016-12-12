

package com.zacharyfox.rmonitor.leaderboard.frames;

import com.zacharyfox.rmonitor.utils.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;

public class PlayerFrame extends JFrame implements ActionListener {
  private JFileChooser chooser;

  private final MainFrame mainFrame;

  private final JTextField playerFile;

  private final JButton selectFileButton;

  private final JButton startStop;

  private static PlayerFrame instance;

  private static Player player;

  private static final long serialVersionUID = -9179041103033981780L;

  private PlayerFrame(@NotNull MainFrame mainFrame) {
    if (mainFrame == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    this.mainFrame = mainFrame;
    getContentPane().setLayout(new net.miginfocom.swing.MigLayout("", "[grow][][]", "[][]"));
    setBounds(100, 100, 400, 150);
    playerFile = new JTextField();
    getContentPane().add(playerFile, "cell 0 0,growx");
    playerFile.setColumns(10);
    selectFileButton = new JButton("Open");
    selectFileButton.addActionListener(PlayerFrame.this);
    getContentPane().add(selectFileButton, "cell 1 0");
    startStop = new JButton("Start");
    startStop.setEnabled(false);
    startStop.addActionListener(PlayerFrame.this);
    getContentPane().add(startStop, "cell 2 0");
  }

  @Override
  public void actionPerformed(@NotNull ActionEvent evt) {
    if (evt == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (evt.getActionCommand().equals("Open")) {
      chooser = new JFileChooser();
      chooser.setSelectedFile(new File("leaderboard-recording.txt"));
      if ((chooser.showOpenDialog(PlayerFrame.this)) == (JFileChooser.APPROVE_OPTION)) {
        playerFile.setText(chooser.getSelectedFile().toString());
        startStop.setEnabled(true);
      }
    } else if (evt.getActionCommand().equals("Start")) {
      startStop.setText("Stop");
      playerFile.setEnabled(false);
      selectFileButton.setEnabled(false);
      PlayerFrame.player = new Player(playerFile.getText());
      PlayerFrame.player.execute();
    } else if (evt.getActionCommand().equals("Stop")) {
      PlayerFrame.player.close();
      startStop.setText("Start");
      playerFile.setEnabled(true);
      selectFileButton.setEnabled(true);
    }
  }

  public static PlayerFrame getInstance(@NotNull MainFrame mainFrame) {
    if (mainFrame == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if ((PlayerFrame.instance) == null) {
      PlayerFrame.instance = new PlayerFrame(mainFrame);
    }
    return PlayerFrame.instance;
  }
}
