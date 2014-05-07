package com.javacreed.examples.gson.part5_3;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) throws Exception {

    final GsonBuilder builder = new GsonBuilder();
    builder.setVersion(1.0);

    final Gson gson = builder.create();

    final SoccerPlayer account = new SoccerPlayer();
    account.setName("Albert Attard");
    account.setShirtNumber(10); // Since version 1.2
    account.setTeamName("Zejtun Corinthians");
    account.setCountry("Malta"); // Until version 0.9

    final String json = gson.toJson(account);
    System.out.printf("Serialised (version 1.0)%n  %s%n", json);

    try (final Reader data = new InputStreamReader(Main.class.getResourceAsStream("/part5_3/player.json"), "UTF-8")) {
      // Parse JSON to Java
      final SoccerPlayer otherPlayer = gson.fromJson(data, SoccerPlayer.class);
      System.out.println("Deserialised (version 1.0)");
      System.out.printf("  Name:          %s%n", otherPlayer.getName());
      System.out.printf("  Shirt Number:  %s (since version 1.2)%n", otherPlayer.getShirtNumber());
      System.out.printf("  Team:          %s%n", otherPlayer.getTeamName());
      System.out.printf("  Country:       %s (until version 0.9)%n", otherPlayer.getCountry());
    }
  }
}
