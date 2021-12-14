package Simulation;

    public record SimulationStatistics(
        int dayNumber,
        double meanLifeLength,
        double meanChildrenNumber,
        double meanEnergy,
        int noOfAnimals,
        int noOfPlants
    ){
        @Override
        public String toString() {
            return "Number of animals: " + noOfAnimals + "\n"
                    + "Number of plants: " + noOfPlants + "\n"
                    + "Mean age: " + formatNumber(meanLifeLength) + "\n"
                    + "Mean number of children: " + formatNumber(meanChildrenNumber) + "\n"
                    + "Mean energy: " + formatNumber(meanEnergy) + "\n"
                    + "Day number: " + dayNumber + "\n";
        }

        private String formatNumber(double number) {
            return String.format("%.2f", number);
        }
    }
