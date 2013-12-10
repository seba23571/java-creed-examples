package com.javacreed.examples.gson.part1;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        
        Map<Integer, Contact> contacts = new HashMap<>();
        contacts.put(1, new Contact(1, "Paul White"));
        contacts.put(2, new Contact(2, "John Smith"));
        
        String json = gson.toJson(contacts);
        System.out.println(json);
    }
}
