

package com.zacharyfox.rmonitor.leaderboard;

import com.zacharyfox.rmonitor.utils.Duration;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

public class LeaderBoardTable extends JTable {
  private static final long serialVersionUID = -6458659058033888484L;

  private LeaderBoardTableModel leaderBoardTableModel = new LeaderBoardTableModel();

  private TableRowSorter<LeaderBoardTableModel> sorter =
      new TableRowSorter<LeaderBoardTableModel>(leaderBoardTableModel);

  public LeaderBoardTable() {
    super();
    LeaderBoardTable.this.setModel(leaderBoardTableModel);
    LeaderBoardTable.this.setRowSorter(sorter);
    LeaderBoardTable.this.setDefaultRenderer(Duration.class, new LeaderBoardTableCellRenderer());
    LeaderBoardTable.this.initColumns();
    sorter.setSortsOnUpdates(true);
    sorter.toggleSortOrder(0);
  }

  private void initColumns() {
    Integer smallColumnSize = 40;
    Integer timeColumnSize = 100;
    Integer[] smallColumns = new Integer[] {0, 1, 2, 3, 5};
    Integer[] timeColumns = new Integer[] {6, 7, 8, 9};
    for (Integer column : smallColumns) {
      LeaderBoardTable.this.getColumnModel().getColumn(column).setPreferredWidth(smallColumnSize);
    }
    for (Integer column : timeColumns) {
      LeaderBoardTable.this.getColumnModel().getColumn(column).setPreferredWidth(timeColumnSize);
    }
  }
}
