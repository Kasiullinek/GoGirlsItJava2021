package Simulation;

import java.util.*;

import java.util.Random;

public class WorldMap extends AbstractWorldMap {
    private static final int ANIMALS_NUM = 10, PLANTS_NUM = 100;
    private HashMap<Vector2D, List<Animal>> animalsPosition = new HashMap<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private HashMap<Vector2D, Plant> plants = new HashMap<>();
    private Random random;

    public WorldMap(int width, int height) {
        super(width, height);
        random = new Random();
        for(int i = 0; i < ANIMALS_NUM; i++) {
            Animal animal = new Animal(getRandomPosition());
            animals.add(animal);
            placeAnimalOnMap(animal);
        }for(int i = 0; i < PLANTS_NUM; i++) {
            placePlantsOnMap();
        }
    }
    private void placeAnimalOnMap(Animal animal)
    {
        List<Animal> animalsAtPosition = animalsPosition.get(animal.getPosition());
        if(animalsAtPosition == null) {
            animalsAtPosition = new LinkedList<>();
            animalsPosition.put(animal.getPosition(), animalsAtPosition);
        }
        animalsAtPosition.add(animal);
    }
    private void placePlantsOnMap()
    {
        Vector2D position = getRandomPosition();
        while(IsOccupiedByPlant(position)) position = getRandomPosition();
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
        animalsPosition.clear();
        for (Animal animal : animals) {
            animal.move(MapDirection.values()[this.random.nextInt(MapDirection.values().length)]);
            placeAnimalOnMap(animal);
        }
    }

    public void eat()
    {
        for(Animal animal: animals) {
            if(IsOccupiedByPlant(animal.getPosition())) {
                System.out.println("Animal ate plant on position " + animal.getPosition());
                plants.remove(animal.getPosition());
                placePlantsOnMap();
            }
        }
    }
}
