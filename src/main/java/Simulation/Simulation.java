package Simulation;

public class Simulation {

    private static final WorldMap worldMap = new WorldMap();

    public static WorldMap getWorldMap() { return worldMap; }

    public static void simulateDay() {
        worldMap.run();
        worldMap.eat();
        worldMap.reproduce();
        worldMap.atTheEndOfDay();
    }

    public static void setSimulation(){
        worldMap.setSimulation();
    }
}
