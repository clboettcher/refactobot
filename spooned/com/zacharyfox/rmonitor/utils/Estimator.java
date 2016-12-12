

package com.zacharyfox.rmonitor.utils;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.entities.Race;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.jetbrains.annotations.NotNull;

public class Estimator {
  private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(Estimator.this);

  private int estimatedLapsAvg = 0;

  private int estimatedLapsBest = 0;

  private int estimatedLapsLast = 0;

  private Duration estimatedTimeAvg = new Duration(0);

  private Duration estimatedTimeBest = new Duration(0);

  private Duration estimatedTimeLast = new Duration(0);

  private int lapsComplete = 0;

  private int lapsToGo = 0;

  private int scheduledLaps = 0;

  private Duration scheduledTime = new Duration(0);

  public void addPropertyChangeListener(@NotNull PropertyChangeListener l) {
    if (l == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    changeSupport.addPropertyChangeListener(l);
  }

  public void addPropertyChangeListener(
      @NotNull String property, @NotNull PropertyChangeListener l) {
    if (l == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (property == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    changeSupport.addPropertyChangeListener(property, l);
  }

  public int getEstimatedLapsAvg() {
    return Estimator.this.estimatedLapsAvg;
  }

  public int getEstimatedLapsBest() {
    return Estimator.this.estimatedLapsBest;
  }

  public Duration getEstimatedTimeAvg() {
    return estimatedTimeAvg;
  }

  public Duration getEstimatedTimeBest() {
    return estimatedTimeBest;
  }

  public int getLapsComplete() {
    return lapsComplete;
  }

  public int getScheduledLaps() {
    return Estimator.this.scheduledLaps;
  }

  public Duration getScheduledTime() {
    return Estimator.this.scheduledTime;
  }

  public void update(@NotNull Race race) {
    if (race == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Estimator.this.setScheduledTime(race.getScheduledTime());
    Estimator.this.setScheduledLaps(race.getScheduledLaps());
    Estimator.this.setLapsComplete(race.getLapsComplete());
    Estimator.this.setLapsToGo(race.getLapsToGo());
    Estimator.this.setEstimatedLapsByBest();
    Estimator.this.setEstimatedLapsByAvg();
  }

  private void setEstimatedLapsByAvg() {
    int oldEstimatedLapsAvg = Estimator.this.estimatedLapsAvg;
    Duration oldEstimatedTimeAvg = Estimator.this.estimatedTimeAvg;
    Competitor competitor = Competitor.getByPosition(1);
    if (competitor == null) {
      Estimator.this.estimatedLapsAvg = lapsToGo;
      return;
    }
    int laps = competitor.getLapsComplete();
    if (laps == 0) {
      Estimator.this.estimatedLapsAvg = lapsToGo;
      return;
    }
    int time = competitor.getTotalTime().toInt();
    int avgLapTime = competitor.getAvgLap().toInt();
    do {
      time += avgLapTime;
      laps += 1;
    } while (time < (scheduledTime.toInt()));
    if (((lapsToGo) == 0) || (laps < ((lapsToGo) + (competitor.getLapsComplete())))) {
      Estimator.this.estimatedLapsAvg = laps;
    } else {
      Estimator.this.estimatedLapsAvg = scheduledLaps;
    }
    Estimator.this.estimatedTimeAvg = new Duration(time);
    changeSupport.firePropertyChange(
        "estimatedLapsAvg", oldEstimatedLapsAvg, Integer.toString(Estimator.this.estimatedLapsAvg));
    changeSupport.firePropertyChange("estimatedTimeAvg", oldEstimatedTimeAvg, estimatedTimeAvg);
  }

  private void setEstimatedLapsByBest() {
    int oldEstimatedLapsBest = Estimator.this.estimatedLapsBest;
    Duration oldEstimatedTimeBest = Estimator.this.estimatedTimeBest;
    Competitor competitor = Competitor.getByPosition(1);
    if (competitor == null) {
      Estimator.this.estimatedLapsBest = lapsToGo;
      return;
    }
    int laps = competitor.getLapsComplete();
    if (laps == 0) {
      Estimator.this.estimatedLapsBest = lapsToGo;
      return;
    }
    int time = competitor.getTotalTime().toInt();
    int bestLapTime = competitor.getBestLap().toInt();
    if (bestLapTime == 0) {
      Estimator.this.estimatedLapsBest = lapsToGo;
      return;
    }
    do {
      time += bestLapTime;
      laps += 1;
    } while (time < (scheduledTime.toInt()));
    if (((lapsToGo) == 0) || (laps < ((lapsToGo) + (competitor.getLapsComplete())))) {
      Estimator.this.estimatedLapsBest = laps;
    } else {
      Estimator.this.estimatedLapsBest = scheduledLaps;
    }
    Estimator.this.estimatedTimeBest = new Duration(time);
    changeSupport.firePropertyChange(
        "estimatedLapsBest",
        oldEstimatedLapsBest,
        Integer.toString(Estimator.this.estimatedLapsBest));
    changeSupport.firePropertyChange("estimatedTimeBest", oldEstimatedTimeBest, estimatedTimeBest);
  }

  private void setEstimatedLapsByLast() {
    int oldEstimatedLapsLast = Estimator.this.estimatedLapsLast;
    Duration oldEstimatedTimeLast = Estimator.this.estimatedTimeLast;
    Competitor competitor = Competitor.getByPosition(1);
    if (competitor == null) {
      Estimator.this.estimatedLapsLast = lapsToGo;
      return;
    }
    int laps = competitor.getLapsComplete();
    if (laps == 0) {
      Estimator.this.estimatedLapsLast = lapsToGo;
      return;
    }
    int time = competitor.getTotalTime().toInt();
    int avgLapTime = competitor.getLastLap().toInt();
    do {
      time += avgLapTime;
      laps += 1;
    } while (time < (scheduledTime.toInt()));
    if (((lapsToGo) == 0) || (laps < ((lapsToGo) + (competitor.getLapsComplete())))) {
      Estimator.this.estimatedLapsLast = laps;
    } else {
      Estimator.this.estimatedLapsLast = scheduledLaps;
    }
    Estimator.this.estimatedTimeLast = new Duration(time);
    changeSupport.firePropertyChange(
        "estimatedLapsLast",
        oldEstimatedLapsLast,
        Integer.toString(Estimator.this.estimatedLapsLast));
    changeSupport.firePropertyChange("estimatedTimeLast", oldEstimatedTimeLast, estimatedTimeLast);
  }

  private void setLapsComplete(int lapsComplete) {
    int oldLapsComplete = Estimator.this.lapsComplete;
    Estimator.this.lapsComplete = lapsComplete;
    changeSupport.firePropertyChange(
        "lapsComplete", oldLapsComplete, Integer.toString(lapsComplete));
  }

  private void setLapsToGo(int lapsToGo) {
    int oldLapsToGo = Estimator.this.lapsToGo;
    Estimator.this.lapsToGo = lapsToGo;
    changeSupport.firePropertyChange("lapsToGo", oldLapsToGo, Integer.toString(lapsToGo));
  }

  private void setScheduledLaps(int scheduledLaps) {
    int oldScheduledLaps = Estimator.this.scheduledLaps;
    Estimator.this.scheduledLaps = scheduledLaps;
    changeSupport.firePropertyChange(
        "scheduledLaps", oldScheduledLaps, Integer.toString(scheduledLaps));
  }

  private void setScheduledTime(@NotNull Duration scheduledTime) {
    if (scheduledTime == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldScheduledTime = Estimator.this.scheduledTime;
    Estimator.this.scheduledTime = scheduledTime;
    changeSupport.firePropertyChange(
        "scheduledTime", oldScheduledTime, Estimator.this.scheduledTime);
  }
}
