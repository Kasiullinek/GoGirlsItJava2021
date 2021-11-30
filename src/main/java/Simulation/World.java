package Simulation;

import java.util.Random;

public class World {

    private static final Random random = new Random();
    private static final int DAYS = 15;

    public static void main(String[] args) {
        System.out.println("Start");
        for(int i = 0; i < DAYS; i++){
            Simulation.simulateDay();
        }
        System.out.println("Stop");
    }
}

