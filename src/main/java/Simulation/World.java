package Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static final Random random = new Random();
    private static final int WIDTH = 20, HEIGHT = 30;

    public static void main(String[] args) {
        System.out.println("Start");
        Simulation.simulateDay();
        System.out.println("Stop");
    }

    private static void moveAnimal(Animal animal) {
        animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)], WIDTH, HEIGHT);
    }
}

