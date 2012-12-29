package com.javacreed.examples.gson.part5_3;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

public class SoccerPlayer {

  private String name;

  @Since(1.2)
  private int shirtNumber;

  @Until(0.9)
  private String country;

  private String teamName;

  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  public int getShirtNumber() {
    return shirtNumber;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setShirtNumber(final int shirtNumber) {
    this.shirtNumber = shirtNumber;
  }

  public void setTeamName(final String teamName) {
    this.teamName = teamName;
  }

}
