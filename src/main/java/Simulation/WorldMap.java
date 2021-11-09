package Simulation;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldMap extends AbstractWorldMap {
    private static final int ANIMALS_NUM = 10, PLANTS_NUM = 100;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private Random random;

    public WorldMap(int width, int height) {
        super(width, height);
        random = new Random();
        for(int i = 0; i < ANIMALS_NUM; i++)
        {
            animals.add(new Animal(getRandomPosition()));
        }for(int i = 0; i < PLANTS_NUM; i++)
        {
            placePlantsOnMap();
        }
    }

    private void placePlantsOnMap()
    {
        Vector2D position = getRandomPosition();
        while(IsOccupiedByPlant(position)) position = getRandomPosition();
        plants.add(new Plant(position));
    }
    private boolean IsOccupiedByPlant(Vector2D position)
    {
        return getPlantAtPosition(position) != null;
    }

    private Plant getPlantAtPosition(Vector2D position) {
        for(Plant plant : plants)
        {
            if(plant.getPosition().equals(position)) return plant;
        }
        return null;
    }
    private Vector2D getRandomPosition()
    {
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }
    @Override
    public void run() {
        for (Animal animal : animals) {
            animal.move(MapDirection.values()[this.random.nextInt(MapDirection.values().length)]);
        }
    }

    public void eat()
    {
        for(Animal animal: animals)
        {
            if(IsOccupiedByPlant(animal.getPosition())) {
                Plant plant = getPlantAtPosition(animal.getPosition());
                if(plant != null) {
                    plants.remove(plant);
                    placePlantsOnMap();
                }
            }
        }
    }
}
