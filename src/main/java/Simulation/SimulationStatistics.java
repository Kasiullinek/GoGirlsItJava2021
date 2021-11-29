package Simulation;

public class SimulationStatistics {
    private final int dayNumber;
    private final double meanLifeLength;
    private final double meanChildrenNumber;
    private final double meanEnergy;
    private int noOfAnimals;
    private int noOfPlants;

    public SimulationStatistics(
        int dayNumber,
        double meanLifeLength,
        double meanChildrenNumber,
        double meanEnergy,
        int noOfAnimals,
        int noOfPlants
    ){
        this.dayNumber = dayNumber;
        this.meanLifeLength = meanLifeLength;
        this.meanChildrenNumber = meanChildrenNumber;
        this.meanEnergy = meanEnergy;
        this.noOfAnimals = noOfAnimals;
        this.noOfPlants = noOfPlants;
    }
}
