package Simulation;

public class Animal {
    private Vector2D position;
    private int energy;

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public Animal setEnergy(int energy) {
        this.energy = energy;
        return this;
    }
    public Vector2D getPosition() {
        return position;
    }

    public void move(MapDirection direction) {
        position = pbc(position.add(direction.getUnitVector()));
        System.out.println("Animal moves " + direction + ": new position: " + position);
    }

    private Vector2D pbc(Vector2D position) {
        int width = Simulation.getWorldMap().getWidth();
        int height = Simulation.getWorldMap().getHeight();
        if (position.getX() < 0) return position.add(new Vector2D(width, 0));
        if (position.getX() >= width) return position.subtract(new Vector2D(width, 0));
        if (position.getY() < 0) return position.add(new Vector2D(0, height));
        if (position.getY() >= height) return position.subtract(new Vector2D(0, height));

        return position;
    }
}
