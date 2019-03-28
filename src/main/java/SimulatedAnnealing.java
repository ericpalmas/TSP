import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    Tour path;
    DistanceMatrix distanceMatrix;
    ArrayList<City> cities;
    long startTime;
    public static double bestValue = 0;

    public SimulatedAnnealing(Tour path, DistanceMatrix distanceMatrix) {
        this.path = path;
        this.distanceMatrix = distanceMatrix;
    }

    public SimulatedAnnealing(Tour newTour, DistanceMatrix distanceMatrix, ArrayList<City> cities, long startTime) {
        this.path = newTour;
        this.distanceMatrix = distanceMatrix;
        this.cities = cities;
        this.startTime = startTime;
    }

    public Tour computeSolution() {
        double temperature = 100, alpha = 0.95;
        Tour current = new Tour(path);
        Tour candidate, next;
        Tour best = new Tour(current);
        Random random = new Random();

        long end;
        int[] array;
        do {
            for (int i = 0; i < 100; i++) {
                bestValue = current.getTourDistance(distanceMatrix);
                DoubleBridge doubleBridge = new DoubleBridge(current, distanceMatrix);
                array = doubleBridge.executeDoubleBridge(path.getPath(), distanceMatrix.getDistanceMatrix(), path.getTourSize() - 1,path.getTourDistance(distanceMatrix));
                next = calculator(array);
                TwoOpt twoOpt = new TwoOpt(next, distanceMatrix);
                candidate = new Tour(twoOpt.optimizePath());

                if (candidate.getTourDistance(distanceMatrix) < current.getTourDistance(distanceMatrix)) {
                    current = new Tour(candidate);
                    if (current.getTourDistance(distanceMatrix) < best.getTourDistance(distanceMatrix)) {
                        best = new Tour(current);
                        bestValue = best.getTourDistance(distanceMatrix);
                        System.out.println("SA: " +best.getTourDistance(distanceMatrix));
                    }
                } else if (random.nextDouble() < Math.exp(-(candidate.getTourDistance(distanceMatrix) - current.getTourDistance(distanceMatrix)) / temperature)) {
                    current = new Tour(candidate);
                }
                end = System.nanoTime();
                if (end - startTime > 1.62e+11)
                    return best;
            }
            temperature *= alpha;

        } while (temperature > 0);
        return best;
    }

    private Tour calculator(int[] array) {
        Tour tour = new Tour();
        for (int i = 0; i < array.length; i++) {
            tour.addCity(new City(cities.get(array[i]).getLatitude(), cities.get(array[i]).getLongitude(), array[i]));
        }
        return tour;
    }

}
