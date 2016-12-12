

package com.zacharyfox.rmonitor.entities;

import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.message.RunInfo;
import com.zacharyfox.rmonitor.message.SettingInfo;
import com.zacharyfox.rmonitor.utils.Duration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.jetbrains.annotations.NotNull;

public class Race {
  private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(Race.this);

  private int competitorsVersion = 0;

  private Duration elapsedTime = new Duration();

  private String flagStatus = "";

  private int id = 0;

  private int lapsComplete = 0;

  private int lapsToGo = 0;

  private String name = "";

  private Duration scheduledTime = new Duration();

  private Duration timeOfDay = new Duration();

  private Duration timeToGo = new Duration();

  private Float trackLength = ((float) (0.0));

  private String trackName = "";

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

  public int getLapsComplete() {
    return lapsComplete;
  }

  public int getLapsToGo() {
    return lapsToGo;
  }

  public int getScheduledLaps() {
    return (lapsComplete) + (lapsToGo);
  }

  public Duration getScheduledTime() {
    return scheduledTime;
  }

  public Float getTrackLength() {
    return trackLength;
  }

  public String getTrackName() {
    return trackName;
  }

  public void removePropertyChangeListener(@NotNull PropertyChangeListener l) {
    if (l == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    changeSupport.removePropertyChangeListener(l);
  }

  public void removePropertyChangeListener(
      @NotNull String property, @NotNull PropertyChangeListener l) {
    if (l == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (property == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    changeSupport.removePropertyChangeListener(property, l);
  }

  @Override
  public String toString() {
    String string;
    string = ("Race Name: " + (name)) + "\n";
    string += ("Time to go: " + (timeToGo.toString())) + "\n";
    string += ("Elapsed Time: " + (elapsedTime)) + "\n";
    string += ("Race Duration: " + (scheduledTime)) + "\n";
    Competitor leader = Competitor.getByPosition(1);
    if (leader != null) {
      string += ("Leader: " + (leader.getRegNumber())) + "\n";
      string += ("Leader Laps: " + (leader.getLapsComplete())) + "\n";
      string += ("Leader Total Time: " + (leader.getTotalTime())) + "\n";
    }
    return string;
  }

  public void update(@NotNull RMonitorMessage message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (message != null) {
      if ((message.getClass()) == (Heartbeat.class)) {
        Race.this.messageUpdate(((Heartbeat) (message)));
      } else if ((message.getClass()) == (RunInfo.class)) {
        Race.this.messageUpdate(((RunInfo) (message)));
      } else if ((message.getClass()) == (SettingInfo.class)) {
        Race.this.messageUpdate(((SettingInfo) (message)));
      } else if ((message.getClass()) == (RaceInfo.class)) {
        Race.this.messageUpdate(((RaceInfo) (message)));
        Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.CompInfo.class)) {
        Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.LapInfo.class)) {
        Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.QualInfo.class)) {
        Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.ClassInfo.class)) {
        com.zacharyfox.rmonitor.entities.RaceClass.update(
            ((com.zacharyfox.rmonitor.message.ClassInfo) (message)));
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.PassingInfo.class)) {
        Competitor.updateOrCreate(message);
      } else {
        java.lang.System.out.println(message);
      }
    }
  }

  private void messageUpdate(@NotNull Heartbeat message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    setElapsedTime(message.getRaceTime());
    setLapsToGo(message.getLapsToGo());
    setTimeToGo(message.getTimeToGo());
    setScheduledTime(new Duration(((elapsedTime.toFloat()) + (timeToGo.toFloat()))));
    setTimeOfDay(message.getTimeOfDay());
    setFlagStatus(message.getFlagStatus());
  }

  private void messageUpdate(@NotNull RaceInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if ((message.getPosition()) == 1) {
      setLapsComplete(message.getLaps());
    }
  }

  private void messageUpdate(@NotNull RunInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (((id) != (message.getUniqueId())) && ((name) != (message.getRaceName()))) {
      competitorsVersion = 0;
      elapsedTime = new Duration();
      flagStatus = "";
      id = 0;
      lapsComplete = 0;
      lapsToGo = 0;
      name = "";
      scheduledTime = new Duration();
      timeOfDay = new Duration();
      timeToGo = new Duration();
      trackLength = ((float) (0.0));
      trackName = "";
      Competitor.reset();
    }
    setName(message.getRaceName());
    setId(message.getUniqueId());
  }

  private void messageUpdate(@NotNull SettingInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (message.getDescription().equals("TRACKNAME")) {
      setTrackName(message.getValue());
    }
    if (message.getDescription().equals("TRACKLENGTH")) {
      setTrackLength(Float.parseFloat(message.getValue()));
    }
  }

  private void setCompetitorsVersion() {
    Race.this.competitorsVersion = (Race.this.competitorsVersion) + 1;
    changeSupport.firePropertyChange(
        "competitorsVersion", ((Race.this.competitorsVersion) - 1), Race.this.competitorsVersion);
  }

  private void setElapsedTime(@NotNull Duration elapsedTime) {
    if (elapsedTime == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldElapsedTime = Race.this.elapsedTime;
    Race.this.elapsedTime = elapsedTime;
    changeSupport.firePropertyChange("elapsedTime", oldElapsedTime, Race.this.elapsedTime);
  }

  private void setFlagStatus(@NotNull String flagStatus) {
    if (flagStatus == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldFlagStatus = Race.this.flagStatus;
    Race.this.flagStatus = flagStatus;
    changeSupport.firePropertyChange("flagStatus", oldFlagStatus, Race.this.flagStatus);
  }

  private void setId(int id) {
    int oldId = Race.this.id;
    Race.this.id = id;
    changeSupport.firePropertyChange("id", oldId, Race.this.id);
  }

  private void setLapsComplete(int lapsComplete) {
    int oldLapsComplete = Race.this.lapsComplete;
    Race.this.lapsComplete = lapsComplete;
    changeSupport.firePropertyChange("lapsComplete", oldLapsComplete, Race.this.lapsComplete);
  }

  private void setLapsToGo(int lapsToGo) {
    int oldLapsToGo = Race.this.lapsToGo;
    Race.this.lapsToGo = lapsToGo;
    changeSupport.firePropertyChange("lapsToGo", oldLapsToGo, Race.this.lapsToGo);
  }

  private void setName(@NotNull String name) {
    if (name == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldName = Race.this.name;
    Race.this.name = name;
    changeSupport.firePropertyChange("name", oldName, Race.this.name);
  }

  private void setScheduledTime(@NotNull Duration scheduledTime) {
    if (scheduledTime == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldScheduledTime = Race.this.scheduledTime;
    Race.this.scheduledTime = scheduledTime;
    changeSupport.firePropertyChange("scheduledTime", oldScheduledTime, Race.this.scheduledTime);
  }

  private void setTimeOfDay(@NotNull Duration timeOfDay) {
    if (timeOfDay == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldTimeOfDay = Race.this.timeOfDay;
    Race.this.timeOfDay = timeOfDay;
    changeSupport.firePropertyChange("timeOfDay", oldTimeOfDay, Race.this.timeOfDay);
  }

  private void setTimeToGo(@NotNull Duration timeToGo) {
    if (timeToGo == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldTimeToGo = Race.this.timeToGo;
    Race.this.timeToGo = timeToGo;
    changeSupport.firePropertyChange("timeToGo", oldTimeToGo, Race.this.timeToGo);
  }

  private void setTrackLength(@NotNull Float trackLength) {
    if (trackLength == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Float oldTrackLength = Race.this.trackLength;
    Race.this.trackLength = trackLength;
    changeSupport.firePropertyChange("trackLength", oldTrackLength, Race.this.trackLength);
  }

  private void setTrackName(@NotNull String trackName) {
    if (trackName == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldTrackName = Race.this.trackName;
    Race.this.trackName = trackName;
    changeSupport.firePropertyChange("trackName", oldTrackName, Race.this.trackName);
  }
}
