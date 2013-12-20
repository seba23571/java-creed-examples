package com.javacreed.examples.deadlock.part2;


public class Example1 {

    
    
    public static void main(String[] args) {
        
        final Object lockX = new Object();
        final Object lockY = new Object();

        final Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockX) {
                    synchronized (lockY) {
                        
                    }
                }
            }
        }, "Thread-A");
        threadA.start();

        
        
        
    }
}
