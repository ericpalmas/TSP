import java.util.ArrayList;

public class Controller {
    public static void main(String[] args) {
        TspReader tspReader = new TspReader(args[0]);
        ArrayList<City> cities = tspReader.getCities();

        DistanceMatrix distanceMatrix = new DistanceMatrix(cities.size());
        distanceMatrix.calculateDistance(cities);

        NeirestNeighbor neirestNeighbor = new NeirestNeighbor(cities,distanceMatrix);
        Tour minPath  = neirestNeighbor.calculatePath();
        System.out.println("nearestNeighbor: \n");
        System.out.println("Errore relativo: " + ((minPath.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");

        Tour newTour = twoOpt(minPath,distanceMatrix);
        System.out.println("\nTwoOpt: \n");
        System.out.println("Errore relativo: " + ((newTour.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");
    }


    // Do all 2-opt combinations
    private static Tour twoOpt(Tour minPath,DistanceMatrix distanceMatrix) {
        Tour newTour = new Tour();
        int size = minPath.getTourSize();
        for (int i = 0; i < size; i++) {
            newTour.setCity(i, minPath.getCity(i));
        }
        // repeat until no improvement is made
        int improve = 0, iteration = 0;
        while (improve < 800) {
            double best_distance = minPath.getTourDistance(distanceMatrix);
            for (int i = 1; i < size - 1; i++) {
                for (int k = i + 1; k < size; k++) {
                    twoOptSwap(minPath, newTour, i, k);
                    iteration++;
                    double new_distance = newTour.getTourDistance(distanceMatrix);
                    if (new_distance < best_distance) {
                        improve = 0;
                        for (int j = 0; j < size; j++) {
                            minPath.setCity(j, newTour.getCity(j));
                        }
                        best_distance = new_distance;
                    }
                }
            }
            improve++;
        }
        return newTour;
    }


    public static void twoOptSwap(Tour tour, Tour newTour, int i, int k) {
        int size = tour.getTourSize();
        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int c = 0; c <= i - 1; ++c) {
            newTour.setCity(c, tour.getCity(c));
        }
        // 2. take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for (int c = i; c <= k; ++c) {
            newTour.setCity(c, tour.getCity(k - dec));
            dec++;
        }
        // 3. take route[k+1] to end and add them in order to new_route
        for (int c = k + 1; c < size; ++c) {
            newTour.setCity(c, tour.getCity(c));
        }
    }
}
