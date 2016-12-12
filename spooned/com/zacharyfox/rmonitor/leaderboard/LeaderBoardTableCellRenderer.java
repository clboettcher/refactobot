

package com.zacharyfox.rmonitor.leaderboard;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.utils.Duration;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.jetbrains.annotations.NotNull;

public class LeaderBoardTableCellRenderer extends DefaultTableCellRenderer {
  private static final long serialVersionUID = 8538560288679667468L;

  public Component getTableCellRendererComponent(
      @NotNull JTable table,
      @NotNull Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column) {
    if (value == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (table == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Component c =
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    Duration fastestLap = Competitor.getFastestLap();
    // TODO: This breaks if you reorder columns!!! Need to figure out how to do this with rowSorter and getModel
    Duration competitorBestLap =
        Competitor.getInstance(((String) (table.getValueAt(row, 2)))).getBestLap();
    if ((column == 8) && (value.equals(fastestLap))) {
      c.setBackground(new Color(150, 0, 150));
      c.setForeground(new Color(255, 255, 255));
    } else if ((column == 7) && (value.equals(fastestLap))) {
      c.setBackground(new Color(150, 0, 150));
      c.setForeground(new Color(255, 255, 255));
    } else if ((column == 7) && (value.equals(competitorBestLap))) {
      c.setBackground(new Color(0, 150, 0));
      c.setForeground(new Color(255, 255, 255));
    } else {
      c.setBackground(table.getBackground());
      c.setForeground(table.getForeground());
    }

    return c;
  }
}
