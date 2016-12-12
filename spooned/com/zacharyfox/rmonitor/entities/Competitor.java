

package com.zacharyfox.rmonitor.entities;

import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.PassingInfo;
import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;

public class Competitor {
  public class Lap {
    public int lapNumber;

    public Duration lapTime;

    public int position;

    public Duration totalTime;
  }

  private String addData = "";

  private Duration bestLap = new Duration();

  private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(Competitor.this);

  private int classId = 0;

  private String firstName = "";

  private final ArrayList<Competitor.Lap> laps = new ArrayList<Competitor.Lap>();

  private int lapsComplete = 0;

  private Duration lastLap = new Duration();

  private String lastName = "";

  private String nationality = "";

  private String number = "";

  private int position = 0;

  private String regNumber = "";

  private Duration totalTime = new Duration();

  private int transNumber = 0;

  private static HashMap<String, Competitor> instances = new HashMap<String, Competitor>();

  private Competitor() {}

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

  public String getAddData() {
    return addData;
  }

  public Duration getAvgLap() {
    float totalTime = 0;
    int count = 0;
    if ((laps.size()) > 0) {
      for (Competitor.Lap lap : laps) {
        if (((lap.lapTime) != null) && ((lap.lapNumber) > 0)) {
          totalTime = totalTime + (lap.lapTime.toFloat());
          count++;
        }
      }
      if (count > 0) {
        return new Duration((totalTime / count));
      }
    }
    return new Duration();
  }

  public Duration getBestLap() {
    return bestLap;
  }

  public int getClassId() {
    return classId;
  }

  public String getFirstName() {
    return firstName;
  }

  public ArrayList<Competitor.Lap> getLaps() {
    return laps;
  }

  public int getLapsComplete() {
    return lapsComplete;
  }

  public Duration getLastLap() {
    return lastLap;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNationality() {
    return nationality;
  }

  public String getNumber() {
    return number;
  }

  public int getPosition() {
    return position;
  }

  public int getPositionInClass() {
    int positionInClass = 1;
    for (Competitor competitor : Competitor.instances.values()) {
      if (((competitor.classId) == (classId)) && ((competitor.position) < (position))) {
        positionInClass += 1;
      }
    }
    return positionInClass;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public Duration getTotalTime() {
    return totalTime;
  }

  public int getTransNumber() {
    return transNumber;
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
    string = ("First Name: " + (firstName)) + "\n";
    string += ("Last Name: " + (lastName)) + "\n";
    string += ("Registration Number: " + (regNumber)) + "\n";
    string += ("Number: " + (number)) + "\n";
    string += ("Transponder Number: " + (transNumber)) + "\n";
    string += ("Class ID: " + (classId)) + "\n";
    string += ("Position: " + (position)) + "\n";
    string += ("Laps: " + (lapsComplete)) + "\n";
    string += ("Total Time: " + (totalTime)) + "\n";
    string += ("Best Time: " + (bestLap)) + "\n";
    string += "Laps: \n";
    for (Competitor.Lap lap : laps) {
      string +=
          (((((((lap.lapNumber) + " ") + (lap.position)) + " ") + (lap.lapTime)) + " ")
                  + (lap.totalTime))
              + "\n";
    }
    return string;
  }

  private void addLap(@NotNull LapInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Boolean found = false;
    for (Competitor.Lap lap : Competitor.this.laps) {
      if ((lap.lapNumber) == (message.getLapNumber())) {
        found = true;
        lap.lapTime = message.getLapTime();
        lap.position = message.getPosition();
      }
    }
    if (found == false) {
      Competitor.Lap lap = new Competitor.Lap();
      lap.lapNumber = message.getLapNumber();
      lap.position = message.getPosition();
      lap.lapTime = message.getLapTime();
      Competitor.this.laps.add(lap);
    }
  }

  private void addLap(@NotNull PassingInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Boolean found = false;
    for (Competitor.Lap lap : Competitor.this.laps) {
      if (lap.totalTime.equals(message.getTotalTime())) {
        found = true;
        lap.lapTime = message.getLapTime();
      }
    }
    if (found == false) {
      Competitor.Lap lap = new Competitor.Lap();
      lap.lapTime = message.getLapTime();
      lap.totalTime = message.getTotalTime();
      Competitor.this.laps.add(lap);
    }
  }

  private void addLap(@NotNull RaceInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Boolean found = false;
    for (Competitor.Lap lap : Competitor.this.laps) {
      if (lap.totalTime.equals(message.getTotalTime())) {
        found = true;
        lap.totalTime = message.getTotalTime();
        lap.lapNumber = message.getLaps();
        lap.position = message.getPosition();
      }
    }
    if (found == false) {
      Competitor.Lap lap = new Competitor.Lap();
      lap.totalTime = message.getTotalTime();
      lap.lapNumber = message.getLaps();
      lap.position = message.getPosition();
      Competitor.this.laps.add(lap);
    }
  }

  private void messageUpdate(@NotNull CompInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor.this.setRegNumber(message.getRegNumber());
    Competitor.this.setNumber(message.getNumber());
    Competitor.this.setTransNumber(message.getTransNumber());
    Competitor.this.setFirstName(message.getFirstName());
    Competitor.this.setLastName(message.getLastName());
    Competitor.this.setClassId(message.getClassId());
    if (!("".equals(message.getNationality()))) {
      Competitor.this.setNationality(message.getNationality());
    }
    if (!("".equals(message.getAddInfo()))) {
      Competitor.this.setAddData(message.getAddInfo());
    }
  }

  private void messageUpdate(@NotNull LapInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor.this.addLap(message);
    if ((message.getLapNumber()) == (lapsComplete)) {
      setLastLap(message.getLapTime());
    }
    setBestLap(message.getLapTime());
  }

  private void messageUpdate(@NotNull PassingInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor.this.addLap(message);
    Competitor.this.setLastLap(message.getLapTime());
    Competitor.this.setBestLap(message.getLapTime());
    Competitor.this.setTotalTime(message.getTotalTime());
  }

  private void messageUpdate(@NotNull QualInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor.this.setRegNumber(message.getRegNumber());
    Competitor.this.setBestLap(message.getBestLapTime());
  }

  private void messageUpdate(@NotNull RaceInfo message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor.this.addLap(message);
    Competitor.this.setRegNumber(message.getRegNumber());
    Competitor.this.setPosition(message.getPosition());
    Competitor.this.setLapsComplete(message.getLaps());
    Competitor.this.setTotalTime(message.getTotalTime());
  }

  private void setAddData(@NotNull String addData) {
    if (addData == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldAddData = Competitor.this.addData;
    Competitor.this.addData = addData;
    changeSupport.firePropertyChange("addData", oldAddData, Competitor.this.addData);
  }

  private void setBestLap(@NotNull Duration bestLap) {
    if (bestLap == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (((Competitor.this.bestLap.toInt()) == 0)
        || ((bestLap.toFloat()) < (Competitor.this.bestLap.toFloat()))) {
      Duration oldBestLap = Competitor.this.bestLap;
      Competitor.this.bestLap = bestLap;
      changeSupport.firePropertyChange("bestLap", oldBestLap, Competitor.this.bestLap);
    }
  }

  private void setClassId(int classId) {
    int oldClassId = Competitor.this.classId;
    Competitor.this.classId = classId;
    changeSupport.firePropertyChange("classId", oldClassId, Competitor.this.classId);
  }

  private void setFirstName(@NotNull String firstName) {
    if (firstName == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldName = Competitor.this.firstName;
    Competitor.this.firstName = firstName;
    changeSupport.firePropertyChange("firstName", oldName, firstName);
  }

  private void setLapsComplete(int lapsComplete) {
    int oldLapsComplete = Competitor.this.lapsComplete;
    Competitor.this.lapsComplete = lapsComplete;
    changeSupport.firePropertyChange("lapsComplete", oldLapsComplete, Competitor.this.lapsComplete);
  }

  private void setLastLap(@NotNull Duration lastLap) {
    if (lastLap == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldLastLap = Competitor.this.lastLap;
    Competitor.this.lastLap = lastLap;
    changeSupport.firePropertyChange("lastLap", oldLastLap, Competitor.this.lastLap);
  }

  private void setLastName(@NotNull String lastName) {
    if (lastName == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldLastName = Competitor.this.lastName;
    Competitor.this.lastName = lastName;
    changeSupport.firePropertyChange("lastName", oldLastName, Competitor.this.lastName);
  }

  private void setNationality(@NotNull String nationality) {
    if (nationality == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldNationality = Competitor.this.nationality;
    Competitor.this.nationality = nationality;
    changeSupport.firePropertyChange("nationality", oldNationality, Competitor.this.nationality);
  }

  private void setNumber(@NotNull String number) {
    if (number == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldNumber = Competitor.this.number;
    Competitor.this.number = number;
    changeSupport.firePropertyChange("number", oldNumber, Competitor.this.number);
  }

  private void setPosition(int position) {
    int oldPosition = Competitor.this.position;
    Competitor.this.position = position;
    changeSupport.firePropertyChange("position", oldPosition, Competitor.this.position);
  }

  private void setRegNumber(@NotNull String regNumber) {
    if (regNumber == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    String oldRegNumber = Competitor.this.regNumber;
    Competitor.this.regNumber = regNumber;
    changeSupport.firePropertyChange("regNumber", oldRegNumber, Competitor.this.regNumber);
  }

  private void setTotalTime(@NotNull Duration totalTime) {
    if (totalTime == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Duration oldTotalTime = Competitor.this.totalTime;
    Competitor.this.totalTime = totalTime;
    changeSupport.firePropertyChange("totalTime", oldTotalTime, Competitor.this.totalTime);
  }

  private void setTransNumber(int transNumber) {
    int oldTransNumber = Competitor.this.transNumber;
    Competitor.this.transNumber = transNumber;
    changeSupport.firePropertyChange("transNumber", oldTransNumber, Competitor.this.transNumber);
  }

  public static Competitor getByPosition(int position) {
    for (Competitor competitor : Competitor.instances.values()) {
      if ((competitor.position) == position) {
        return competitor;
      }
    }
    return null;
  }

  public static Duration getFastestLap() {
    Duration fastestLap = new Duration();
    Duration competitorBestLap;
    for (Competitor competitor : Competitor.instances.values()) {
      competitorBestLap = competitor.getBestLap();
      if ((competitorBestLap.toInt()) == 0) continue;

      if ((fastestLap.isEmpty()) || (competitorBestLap.lt(fastestLap))) {
        fastestLap = competitorBestLap;
      }
    }
    return fastestLap;
  }

  public static Competitor getInstance(@NotNull String regNumber) {
    if (regNumber == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    if (Competitor.instances.containsKey(regNumber)) {
      return Competitor.instances.get(regNumber);
    }
    return null;
  }

  public static HashMap<String, Competitor> getInstances() {
    return Competitor.instances;
  }

  public static void reset() {
    Competitor.instances = new HashMap<String, Competitor>();
  }

  public static void updateOrCreate(@NotNull RMonitorMessage message) {
    if (message == null)
      throw new IllegalArgumentException("[Spoon inserted check] null passed as parameter");
    Competitor instance = Competitor.getInstance(message.getRegNumber());
    instance = (instance == null) ? new Competitor() : instance;
    if ((message.getClass()) == (RaceInfo.class)) {
      instance.messageUpdate(((RaceInfo) (message)));
    } else if ((message.getClass()) == (CompInfo.class)) {
      instance.messageUpdate(((CompInfo) (message)));
    } else if ((message.getClass()) == (LapInfo.class)) {
      instance.messageUpdate(((LapInfo) (message)));
    } else if ((message.getClass()) == (QualInfo.class)) {
      instance.messageUpdate(((QualInfo) (message)));
    } else if ((message.getClass()) == (PassingInfo.class)) {
      instance.messageUpdate(((PassingInfo) (message)));
    }

    Competitor.instances.put(instance.getRegNumber(), instance);
  }
}
