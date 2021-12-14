package Simulation;

import java.util.*;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldMap extends AbstractWorldMap {
    private static final String STATIC_FILE = "stats.json";

    private int animalsEnergy = 0;
    private int plantEnergy = 0;
    private int noOfPlants = 0;
    private final int INITIAL_ENERGY = SimulationParams.getField("animalEnergy");
    private int dayNumber = 1;

    private List<Animal> animals = new ArrayList<>();
    private HashMap<Vector2D, List<Animal>> animalsPosition = new HashMap<>();
    private HashMap<Vector2D, Plant> plants = new HashMap<>();
    private Random random = new Random();
    private SimulationStatistics statistics = null;

    public WorldMap() {
        super(SimulationParams.getField("width"), SimulationParams.getField("height"));
    }

    public void setSimulation()
    {
        this.animalsEnergy = SimulationParams.getField("animalEnergy");
        this.plantEnergy = SimulationParams.getField("plantEnergy");
        this.noOfPlants = Math.min(SimulationParams.getField("noOfPlants"), getHeight() * getWidth());
        animals.clear();
        plants.clear();
        for(int i = 0; i < SimulationParams.getField("noOfAnimals"); i++) {
            addNewAnimal(new Animal(getRandomPosition(), animalsEnergy));
        }for(int i = 0; i < noOfPlants; i++) {
        if(plants.size() >= getWidth() * getHeight()) break;
        placePlantsOnMap();
        }
    }

    @Override
    public void reproduce() {
        List<Animal> children = new LinkedList<>();
        animalsPosition.forEach((position, animals) -> {
            List<Animal> parents = animals.stream()
                    .filter(a -> a.getEnergy() > INITIAL_ENERGY / 2)
                    .sorted(Collections.reverseOrder())
                    .limit(2)
                    .collect(Collectors.toList());
            if(parents.size() == 2) {
                Animal child = new Animal(parents.get(0), parents.get(1));
                System.out.println("Animal " + child.getAnimalID() + " was born on position " + position);
               children.add(child);
            }
        });
        children.forEach(this::addNewAnimal);
    }

    private void addNewAnimal(Animal animal) {
        animals.add(animal);
        placeAnimalOnMap(animal);
    }

    @Override
    public void atTheEndOfDay() {
        animals = animals.stream()
                .map(Animal::aging)
                .map(animal -> animal.setEnergy(animal.getEnergy() - INITIAL_ENERGY / 2))
                .filter(animal -> animal.getEnergy() > 0)
                .collect(Collectors.toList());
        dayNumber++;
        createStatistics();
    }
    private void placeAnimalOnMap(Animal animal)
    {
        animalsPosition.computeIfAbsent(animal.getPosition(), pos -> new LinkedList<>()).add(animal);
    }
    private void placePlantsOnMap() {
        Vector2D position = getRandomPosition();
        while(IsOccupiedByPlant(position)) {
            position = getRandomPosition();
        }
        plants.put(position, new Plant(position));
    }
    private boolean IsOccupiedByPlant(Vector2D position)
    {
        return getPlantAtPosition(position) != null;
    }

    private Plant getPlantAtPosition(Vector2D position) {
        return  plants.get(position);
    }
    private Vector2D getRandomPosition()
    {
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }
    @Override
    public void run() {
        System.out.println("Today is day number "+ dayNumber );
        animalsPosition.clear();
        animals.forEach(animal -> {
            animal.moveBasedOnGenome();
            placeAnimalOnMap(animal);
        });
    }

    public void eat()
    {
        animalsPosition.forEach((position, animals) -> {
            if(IsOccupiedByPlant(position)) {
                animals.stream().max(Animal::compareTo).ifPresent(this::eatPlant);
            }
        });
        if(plants.size() < getWidth() * getHeight() - (noOfPlants / 10 + 1)) {
            IntStream.range(1, new Random().nextInt(noOfPlants / 10) + 1).forEach(i -> placePlantsOnMap());
        }
    }

    private void eatPlant(Animal animal) {
        System.out.println("Animal ate plant on position " + animal.getPosition());
        animal.setEnergy(animal.getEnergy() + plantEnergy);
        plants.remove(animal.getPosition());
    }

    private void createStatistics() {
        statistics = new SimulationStatistics(
                dayNumber,
                animals.stream().mapToInt(Animal::getAge).average().orElse(0),
                animals.stream().mapToInt(Animal::getNumberOfChildren).average().orElse(0),
                animals.stream().mapToInt(Animal::getEnergy).average().orElse(0),
                animals.size(),
                plants.size()
        );
       // System.out.println(statistics);
        JsonParser.dumpStatisticsToJsonFile(STATIC_FILE, statistics);
    }

    @Override
    public Map<Vector2D, Plant> getPlantsLocations() {
        return plants;
    }

    @Override
    public Map<Vector2D, List<Animal>> getAnimalsLocations() {
        return animalsPosition;
    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }
}
