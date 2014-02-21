/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.gson.part3;

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
