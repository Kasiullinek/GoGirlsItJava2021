package Simulation;

import java.util.Random;

public class Animal implements Comparable<Animal>{
    private Vector2D position;
    private int energy;
    private int age = 1;
    private final Genome genome;
    private int animalID;
    private int numberOfChildren = 0;
    private static int counter = 0;

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
        this.age = 1;
        this.animalID = counter++;
        this.genome = new Genome();
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void increaseNumberOfChildren() {
        numberOfChildren++;
    }

    public Genome getGenome() {
        return  genome;
    }

    public int getAge() { return age; }
    
    public int getEnergy() {
        return energy;
    }

    public int getAnimalID() {return  animalID;}

    public Animal setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public Animal aging() {
        age++;
        return this;
    }

    public void moveBasedOnGenome() {
        move(genome.getRandomMove());
    }

    public Animal(Animal mother, Animal father) {
        Vector2D direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)].getUnitVector();
        this.position = pbc(mother.getPosition().add(direction));
        this.energy = (mother.getEnergy() + father.getEnergy()) / 4;
        this.animalID = counter++;
        this.genome = new Genome(mother.getGenome(), father.getGenome());
        mother.increaseNumberOfChildren();
        father.increaseNumberOfChildren();
        mother.setEnergy(mother.getEnergy() * 3 / 4);
        father.setEnergy(father.getEnergy() * 3 / 4);
    }
    public Vector2D getPosition() {
        return position;
    }

    public void move(MapDirection direction) {
        position = pbc(position.add(direction.getUnitVector()));
        System.out.println("Animal " + animalID + " moves: " + direction + "; new position: " + position + "; energy: " + energy + "; age: " + age);
    }

    private Vector2D pbc(Vector2D position) {
        int width = Simulation.getWorldMap().getWidth();
        int height = Simulation.getWorldMap().getHeight();
        if (position.x() < 0) return position.add(new Vector2D(width, 0));
        if (position.x() >= width) return position.subtract(new Vector2D(width, 0));
        if (position.y() < 0) return position.add(new Vector2D(0, height));
        if (position.y() >= height) return position.subtract(new Vector2D(0, height));

        return position;
    }

    public int compareTo(Animal animal) {
        return getEnergy() == animal.getEnergy() ? getAnimalID() - animal.getAnimalID() : getEnergy() - animal.getEnergy();
    }
}
