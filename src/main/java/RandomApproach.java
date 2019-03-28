import java.util.ArrayList;

public class RandomApproach {
    DistanceMatrix distanceMatrix;
    Tour path;
    ArrayList<City> cities;
    long startTime;

    public RandomApproach(DistanceMatrix distanceMatrix, Tour path, ArrayList<City> cities, long startTime) {
        this.distanceMatrix = distanceMatrix;
        this.path = path;
        this.cities = cities;
        this.startTime = startTime;
    }

    public Tour computeSolution() {
        RandomGenerator randomGenerator;
        TwoOpt candidate;
        Tour next,finalT,best = path;

        long end;
        do{
            randomGenerator = new RandomGenerator(cities);
            next = randomGenerator.generateTour();
            candidate = new TwoOpt(next,distanceMatrix);
            finalT = candidate.optimizePath();
            if(finalT.getTourDistance(distanceMatrix)<best.getTourDistance(distanceMatrix)){
                best = new Tour(finalT);
            }
            end = System.nanoTime();
        }while((end-startTime)<1.8e+11);
        return best;
    }
}
