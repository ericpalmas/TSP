import java.util.ArrayList;
import java.util.Random;

public class NearestNeighbor {
    private final ArrayList<City> cities;
    private DistanceMatrix distanceMatrix;
    private Random random;

    public NearestNeighbor(final ArrayList<City> cities, DistanceMatrix distanceMatrix, Random random){
        this.cities = cities;
        this.distanceMatrix = distanceMatrix;
        this.random = random;
    }

    public int[] calculate(){
        int[] minPath = new int[cities.size() + 1];
        int pos = random.nextInt(cities.size() - 1);
        int min, count, i, minPos = pos;
        minPath[0] = cities.get(pos).getId();
        boolean[] visitedCities = new boolean[cities.size()];
        visitedCities[pos] = true;
        for (count = 1; count <= cities.size(); count++) {
            min = Integer.MAX_VALUE;
            i = minPos;
            for(int j = 0; j < cities.size(); ++j){
                if(distanceMatrix.getDistanceMatrix()[i][j] != 0 && distanceMatrix.getDistanceMatrix()[i][j] < min && !visitedCities[j]){
                    min = distanceMatrix.getDistanceMatrix()[i][j];
                    minPos = j;
                }
            }
            if (min == Integer.MAX_VALUE)
                break;
            visitedCities[minPos] = true;
            minPath[count] = cities.get(minPos).getId();
        }
        minPath[cities.size()] = minPath[0];
        return minPath;
    }
}



