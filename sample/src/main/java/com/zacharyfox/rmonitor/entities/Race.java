

package com.zacharyfox.rmonitor.entities;

import com.zacharyfox.rmonitor.message.Heartbeat;

public class Race {
  private final java.beans.PropertyChangeSupport changeSupport =
      new java.beans.PropertyChangeSupport(Race.this);

  private int competitorsVersion = 0;

  private com.zacharyfox.rmonitor.utils.Duration elapsedTime =
      new com.zacharyfox.rmonitor.utils.Duration();

  private java.lang.String flagStatus = "";

  private int id = 0;

  private int lapsComplete = 0;

  private int lapsToGo = 0;

  private java.lang.String name = "";

  private com.zacharyfox.rmonitor.utils.Duration scheduledTime =
      new com.zacharyfox.rmonitor.utils.Duration();

  private com.zacharyfox.rmonitor.utils.Duration timeOfDay =
      new com.zacharyfox.rmonitor.utils.Duration();

  private com.zacharyfox.rmonitor.utils.Duration timeToGo =
      new com.zacharyfox.rmonitor.utils.Duration();

  private java.lang.Float trackLength = ((float) (0.0));

  private java.lang.String trackName = "";

  public void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
    changeSupport.addPropertyChangeListener(l);
  }

  public void addPropertyChangeListener(
      java.lang.String property, java.beans.PropertyChangeListener l) {
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

  public com.zacharyfox.rmonitor.utils.Duration getScheduledTime() {
    return scheduledTime;
  }

  public java.lang.Float getTrackLength() {
    return trackLength;
  }

  public java.lang.String getTrackName() {
    return trackName;
  }

  public void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
    changeSupport.removePropertyChangeListener(l);
  }

  public void removePropertyChangeListener(
      java.lang.String property, java.beans.PropertyChangeListener l) {
    changeSupport.removePropertyChangeListener(property, l);
  }

  @java.lang.Override
  public java.lang.String toString() {
    java.lang.String string;
    string = ("Race Name: " + (name)) + "\n";
    string += ("Time to go: " + (timeToGo.toString())) + "\n";
    string += ("Elapsed Time: " + (elapsedTime)) + "\n";
    string += ("Race Duration: " + (scheduledTime)) + "\n";
    com.zacharyfox.rmonitor.entities.Competitor leader =
        com.zacharyfox.rmonitor.entities.Competitor.getByPosition(1);
    if (leader != null) {
      string += ("Leader: " + (leader.getRegNumber())) + "\n";
      string += ("Leader Laps: " + (leader.getLapsComplete())) + "\n";
      string += ("Leader Total Time: " + (leader.getTotalTime())) + "\n";
    }
    return string;
  }

  public void update(com.zacharyfox.rmonitor.message.RMonitorMessage message) {
    if (message != null) {
      if ((message.getClass()) == (Heartbeat.class)) {
        Race.this.messageUpdate(((Heartbeat) (message)));
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.RunInfo.class)) {
        Race.this.messageUpdate(((com.zacharyfox.rmonitor.message.RunInfo) (message)));
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.SettingInfo.class)) {
        Race.this.messageUpdate(((com.zacharyfox.rmonitor.message.SettingInfo) (message)));
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.RaceInfo.class)) {
        Race.this.messageUpdate(((com.zacharyfox.rmonitor.message.RaceInfo) (message)));
        com.zacharyfox.rmonitor.entities.Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.CompInfo.class)) {
        com.zacharyfox.rmonitor.entities.Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.LapInfo.class)) {
        com.zacharyfox.rmonitor.entities.Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.QualInfo.class)) {
        com.zacharyfox.rmonitor.entities.Competitor.updateOrCreate(message);
        setCompetitorsVersion();
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.ClassInfo.class)) {
        com.zacharyfox.rmonitor.entities.RaceClass.update(
            ((com.zacharyfox.rmonitor.message.ClassInfo) (message)));
      } else if ((message.getClass()) == (com.zacharyfox.rmonitor.message.PassingInfo.class)) {
        com.zacharyfox.rmonitor.entities.Competitor.updateOrCreate(message);
      } else {
        java.lang.System.out.println(message);
      }
    }
  }

  private void messageUpdate(Heartbeat message) {
    setElapsedTime(message.getRaceTime());
    setLapsToGo(message.getLapsToGo());
    setTimeToGo(message.getTimeToGo());
    setScheduledTime(
        new com.zacharyfox.rmonitor.utils.Duration(
            ((elapsedTime.toFloat()) + (timeToGo.toFloat()))));
    setTimeOfDay(message.getTimeOfDay());
    setFlagStatus(message.getFlagStatus());
  }

  private void messageUpdate(com.zacharyfox.rmonitor.message.RaceInfo message) {
    if ((message.getPosition()) == 1) {
      setLapsComplete(message.getLaps());
    }
  }

  private void messageUpdate(com.zacharyfox.rmonitor.message.RunInfo message) {
    if (((id) != (message.getUniqueId())) && ((name) != (message.getRaceName()))) {
      competitorsVersion = 0;
      elapsedTime = new com.zacharyfox.rmonitor.utils.Duration();
      flagStatus = "";
      id = 0;
      lapsComplete = 0;
      lapsToGo = 0;
      name = "";
      scheduledTime = new com.zacharyfox.rmonitor.utils.Duration();
      timeOfDay = new com.zacharyfox.rmonitor.utils.Duration();
      timeToGo = new com.zacharyfox.rmonitor.utils.Duration();
      trackLength = ((float) (0.0));
      trackName = "";
      com.zacharyfox.rmonitor.entities.Competitor.reset();
    }
    setName(message.getRaceName());
    setId(message.getUniqueId());
  }

  private void messageUpdate(com.zacharyfox.rmonitor.message.SettingInfo message) {
    if (message.getDescription().equals("TRACKNAME")) {
      setTrackName(message.getValue());
    }
    if (message.getDescription().equals("TRACKLENGTH")) {
      setTrackLength(java.lang.Float.parseFloat(message.getValue()));
    }
  }

  private void setCompetitorsVersion() {
    Race.this.competitorsVersion = (Race.this.competitorsVersion) + 1;
    changeSupport.firePropertyChange(
        "competitorsVersion", ((Race.this.competitorsVersion) - 1), Race.this.competitorsVersion);
  }

  private void setElapsedTime(com.zacharyfox.rmonitor.utils.Duration elapsedTime) {
    com.zacharyfox.rmonitor.utils.Duration oldElapsedTime = Race.this.elapsedTime;
    Race.this.elapsedTime = elapsedTime;
    changeSupport.firePropertyChange("elapsedTime", oldElapsedTime, Race.this.elapsedTime);
  }

  private void setFlagStatus(java.lang.String flagStatus) {
    java.lang.String oldFlagStatus = Race.this.flagStatus;
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

  private void setName(java.lang.String name) {
    java.lang.String oldName = Race.this.name;
    Race.this.name = name;
    changeSupport.firePropertyChange("name", oldName, Race.this.name);
  }

  private void setScheduledTime(com.zacharyfox.rmonitor.utils.Duration scheduledTime) {
    com.zacharyfox.rmonitor.utils.Duration oldScheduledTime = Race.this.scheduledTime;
    Race.this.scheduledTime = scheduledTime;
    changeSupport.firePropertyChange("scheduledTime", oldScheduledTime, Race.this.scheduledTime);
  }

  private void setTimeOfDay(com.zacharyfox.rmonitor.utils.Duration timeOfDay) {
    com.zacharyfox.rmonitor.utils.Duration oldTimeOfDay = Race.this.timeOfDay;
    Race.this.timeOfDay = timeOfDay;
    changeSupport.firePropertyChange("timeOfDay", oldTimeOfDay, Race.this.timeOfDay);
  }

  private void setTimeToGo(com.zacharyfox.rmonitor.utils.Duration timeToGo) {
    com.zacharyfox.rmonitor.utils.Duration oldTimeToGo = Race.this.timeToGo;
    Race.this.timeToGo = timeToGo;
    changeSupport.firePropertyChange("timeToGo", oldTimeToGo, Race.this.timeToGo);
  }

  private void setTrackLength(java.lang.Float trackLength) {
    java.lang.Float oldTrackLength = Race.this.trackLength;
    Race.this.trackLength = trackLength;
    changeSupport.firePropertyChange("trackLength", oldTrackLength, Race.this.trackLength);
  }

  private void setTrackName(java.lang.String trackName) {
    java.lang.String oldTrackName = Race.this.trackName;
    Race.this.trackName = trackName;
    changeSupport.firePropertyChange("trackName", oldTrackName, Race.this.trackName);
  }
}
