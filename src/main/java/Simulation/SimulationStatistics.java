package Simulation;

    public record SimulationStatistics(
        int dayNumber,
        double meanLifeLength,
        double meanChildrenNumber,
        double meanEnergy,
        int noOfAnimals,
        int noOfPlants
    ){}
